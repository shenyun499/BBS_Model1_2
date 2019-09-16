package com.xue.bbs.bean;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.xue.bbs.pojo.Article;
import com.xue.bbs.pojo.Page;
import com.xue.bbs.utils.ArUtils;
import com.xue.bbs.utils.DBUtils;

/**
*
*此功能为展示论坛（树形）
*/

public class ShowArticle {
	
	/**
	 * 递归展示帖子
	 * @param list
	 * @param con
	 * @param pid
	 * @param grade
	 */
	public static void showTreeArticle(List<Article> list, Connection con, int pid, int grade) {
		PreparedStatement pret = null;
		//定义返回结果集
		ResultSet rs = null;
		try {
			//编写sql语句,查询pid值的帖子
			String sql = "select *from article where pid = ?";
			//预编译，此时还没有执行sql语句
			pret = con.prepareStatement(sql);
			//设置查询条件值
			pret.setInt(1, pid);
			//执行sql语句，返回结果集
			rs = pret.executeQuery();
			while (rs.next()) {
				Article article = new Article();
				//给Article赋值
				ArUtils.initArticle(article, rs);
				//单独设置等级
				article.setGrade(grade);
				//添加到集合中
				list.add(article);
				//判断是否为叶子节点
				boolean isleaff = article.getIsleaf();
				if (!isleaff) {
					pid = rs.getInt("id");
					showTreeArticle(list, con, pid, grade + 1);
				}
			}
			
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			DBUtils.close(pret);
			DBUtils.close(rs);
		}
	}

}
