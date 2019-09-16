package com.xue.bbs.beanflat;

import java.sql.Connection;
import java.sql.PreparedStatement;

import com.xue.bbs.utils.DBUtils;

/**
 * 删除帖子：封装删除帖子业务逻辑、数据库代码
 * @author xuexue
 *
 */
public class DeleteFlat {
	
	public static void delete(int rootid) {
		//定义预编译处理类
		PreparedStatement pret = null;
		//定义数据库连接
		Connection con = null;
		try {
			//获取数据库连接
			con = DBUtils.getConnection();
			//编写sql删除语句
			String sql = "delete from article where rootid = ?";
			//预编译sql语句，此时还没执行
			pret = con.prepareStatement(sql);
			pret.setInt(1, rootid);
			pret.executeUpdate();
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			DBUtils.close(con);
			DBUtils.close(pret);
		}
	}

}
