package com.xue.bbs.bean;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.xue.bbs.utils.DBUtils;

/**
 * 删除帖子：封装删除帖子业务逻辑、数据库代码
 * 
 * @author xuexue
 *
 */
public class Delete {

	// 递归函数，删除关联子帖
	public static void treeDelete(Connection con, int id, boolean isleaf) {
		// 定义预编译处理类
		PreparedStatement pret = null;
		// 定义返回结果集
		ResultSet rs = null;
		try {
			// 编写sql删除语句
			String sql = "delete from article where id = ?";
			// 预编译sql语句，此时还没执行
			pret = con.prepareStatement(sql);
			pret.setInt(1, id);
			pret.executeUpdate();

			// 查看是否有子帖，有则递归删除，否则结束
			if (!isleaf) {
				sql = "select *from article where pid = ?";
				pret = con.prepareStatement(sql);
				pret.setInt(1, id);
				rs = pret.executeQuery();
				while (rs.next()) {
					// 定值取值：帖子是否是叶子
					boolean isleaff = rs.getInt("isleaf") == 0 ? true : false;
					// 取得帖子id
					int idd = rs.getInt("id");
					// 递归删除
					treeDelete(con, idd, isleaff);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBUtils.close(pret);
			DBUtils.close(rs);
		}
	}

	public static void updateIsLeaf(int pid, Connection con) {
		// 定义预编译处理类
		PreparedStatement pret = null;
		// 定义返回结果集
		ResultSet rs = null;
		try {
			// 查询是否有帖子有一样的pid，下还有其它
			String sql = "select count(*) from article where pid = ?";
			pret = con.prepareStatement(sql);
			pret.setInt(1, pid);
			rs = pret.executeQuery();
			while (rs.next()) {
				// 获取还有pid的总记录数
				int num = rs.getInt(1);
				// 如果等于0,说明已经没有了，将叶子置为0
				if (num == 0) {
					sql = "update article set isleaf = ? where id = ?";
					pret = con.prepareStatement(sql);
					pret.setInt(1, 0);
					pret.setInt(2, pid);
					pret.executeUpdate();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBUtils.close(con);
			DBUtils.close(pret);
			DBUtils.close(rs);
		}
	}

}
