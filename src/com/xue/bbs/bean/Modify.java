package com.xue.bbs.bean;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.xue.bbs.utils.DBUtils;

/**
 * 功能：更新帖子，封装业务逻辑及数据库代码
 * @author xuexue
 *
 */
public class Modify {
	
	public static void modify(String title, String cont, int id) {
		//定义预编译处理类
		PreparedStatement pret = null;
		//定义返回结果集
		ResultSet rs = null;
		//定义数据库连接
		Connection con = null;
		try {
			//获取数据库连接
			con = DBUtils.getConnection();
			//编写sql修改语句
			String sql = "update article set title = ?,cont=? where id = ? ";
			//预编译sql语句，此时还没执行
			pret = con.prepareStatement(sql);
			pret.setString(1, title);
			pret.setString(2, cont);
			pret.setInt(3, id);
			pret.executeUpdate();
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			DBUtils.close(con);
			DBUtils.close(pret);
			DBUtils.close(rs);
		}
	}

}
