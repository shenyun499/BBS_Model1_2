package com.xue.bbs.beanflat;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import com.xue.bbs.utils.DBUtils;

/**
 * 功能：处理发布帖子的业务逻辑，数据库
 * @author 
 *
 */
public class PostDealFlat {
	
	public static void postDealFlat(String title, String cont) {
		//定义预编译处理类
		PreparedStatement pret = null;
		//定义返回结果集
		ResultSet rs = null;
		//定义数据库连接
		Connection con = null;
		try {
			//获取数据库连接
			con = DBUtils.getConnection();
			//将自动提交事务，改成手动提交
			con.setAutoCommit(false);
			//编写sql插入语句
			String sql = "INSERT INTO `bbs`.`article` (`pid`, `rootid`, `title`, `cont`, `pdate`, `isleaf`) VALUES (?,?, ?, ?, now(), ?)";
			//预编译sql语句，此时还没执行
			pret = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			//设置插入条件
			pret.setInt(1, 0);
			//因为不确定id，所以，先插入一个数，随后在update
			pret.setInt(2, 0);
			pret.setString(3, title);
			pret.setString(4, cont);
			pret.setInt(5, 0);
			//执行sql语句，插入，插入成功返回受影响的行数
			int num = pret.executeUpdate();
			//num为1，插入成功
			if (num == 1) {
				//根据插入成功，获取id
				int id;
				rs = pret.getGeneratedKeys();
				while (rs.next()) {
					id = rs.getInt(1);
					//获取id后，插入rootid
					sql = "update article set rootid = ? where id = ?";
					//预编译
					pret = con.prepareStatement(sql);
					//设置插入条件
					pret.setInt(1, id);
					pret.setInt(2, id);
					//执行插入
					pret.executeUpdate();
					con.commit();
					con.setAutoCommit(true);
				}
			} else {
				//添加失败
			}
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			DBUtils.close(con);
			DBUtils.close(pret);
			DBUtils.close(rs);
		}
	}

}
