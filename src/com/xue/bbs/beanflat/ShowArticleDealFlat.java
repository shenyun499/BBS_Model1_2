package com.xue.bbs.beanflat;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;

import com.xue.bbs.pojo.Article;
import com.xue.bbs.utils.ArUtils;
import com.xue.bbs.utils.DBUtils;

/**
 * 功能：详细展示帖子：封装业务、数据库代码
 * @author xuexue
 *
 */
public class ShowArticleDealFlat {
	
	public static void showArticleDealFlat(List<Article> list, int id) {
		//获取数据库连接
		Connection con = DBUtils.getConnection();
		//定义预编译处理类
		PreparedStatement pret = null;
		//定义返回结果集
		ResultSet rs = null;
		
		//编写sql语句,rootid查询到所有同一主题帖下的文章
		String sql = "select *from article where rootid = ?";
		try {
			//预编译sql语句，此时还没执行
			pret = con.prepareStatement(sql);
			//设置查询条件
			pret.setInt(1, id);
			//执行sql语句，返回结果
			rs = pret.executeQuery();
			while (rs.next()) {
				//每次查到一条记录，存放在article对象中
				Article article = new Article();
				ArUtils.initArticle(article, rs);
				list.add(article);
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
