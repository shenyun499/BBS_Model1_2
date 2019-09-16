package com.xue.bbs.beanflat;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.servlet.http.HttpSession;

import com.xue.bbs.utils.DBUtils;

/**
 * 登录页面验证
 * @author xuexue
 *
 */
public class Login {
	
	public static boolean loginCheck(String username, String password) {
		//定义预编译处理类
				PreparedStatement pret = null;
				//定义返回结果集
				ResultSet rs = null;
				//定义数据库连接
				Connection con = null;
				try {
					//获取数据库连接
					con = DBUtils.getConnection();
					//编写sql查询语句
					String sql = "select *from buser where username = ? and password = ?";
					//预编译sql语句，此时还没执行
					pret = con.prepareStatement(sql);
					//设置查询条件
					pret.setString(1, username);
					pret.setString(2, password);
					//执行sql语句，返回结果
					rs = pret.executeQuery();
					if (rs.next()) {
						return true;
					}
				} catch(Exception e) {
					e.printStackTrace();
				} finally {
					DBUtils.close(con);
					DBUtils.close(pret);
					DBUtils.close(rs);
				}
				return false;
	}

}
