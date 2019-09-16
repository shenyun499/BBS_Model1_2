package com.xue.bbs.beanflat;

import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.xue.bbs.pojo.Article;
import com.xue.bbs.pojo.Page;
import com.xue.bbs.utils.ArUtils;
import com.xue.bbs.utils.DBUtils;

/**
 * 此功能为搜索结果
 * @author 
 *
 */
public class SearchResult {
	public static void searchResult(Page pages, HttpServletRequest request, List<Article> list, String keywords) throws UnsupportedEncodingException {
		if (!keywords.equals("")) {
			//设置字符编码
			request.setCharacterEncoding("utf-8");
			//获取数据库连接
			Connection con = DBUtils.getConnection();
			//定义预编译处理类
			PreparedStatement pret = null;
			//定义返回结果集
			ResultSet rs = null;
			//设置article总记录数
			int totalRecord = 0;
			
			//编写sql语句,查询父帖记录总数
			String sql = "select count(*) from article where title like ? or cont like ?";
			try {
				//预编译sql语句，此时还没执行
				pret = con.prepareStatement(sql);
				//设置查询条件
				pret.setString(1, "%" + keywords + "%");
				pret.setString(2, "%" + keywords + "%");
				//执行sql语句，返回结果
				rs = pret.executeQuery();
				while (rs.next()) {
					totalRecord = rs.getInt(1);
				}
				
				//分页技术实现
				//设置每页数为2
				pages.setPageSize(2);
				//根据总记录数和每页数计算总页数
				pages.setTotalPage(totalRecord, 2);
				//获取当前页数,并设置(如果刚进来，就是为null，设置为1，为第一页，否则，为后面页数)
				int currentPage = request.getParameter("currentPage") == null ? 1 : Integer.parseInt(request.getParameter("currentPage"));
				//当上一页到0时，必须把值设回1
				currentPage = currentPage < 1 ? 1 : currentPage;
				//当下一页到最大页数+1时，必须把值设回最大页数
				currentPage = currentPage > pages.getTotalPage() ? pages.getTotalPage() : currentPage;
				pages.setCurrentPage(currentPage);
				
				//sql查询语句，limit 第二个？表示当前页面起,因为2条记录一页，为了保证每次开始(currentPage - 1)*2  第三个？表示显示2条记录
				sql = "select *from article where title like ? or cont like ? order by pdate asc limit ?,? ";
				pret = con.prepareStatement(sql);
				//设置查询条件
				pret.setString(1, "%" + keywords + "%");
				pret.setString(2, "%" + keywords + "%");
				pret.setInt(3, (currentPage - 1)*2);
				pret.setInt(4, 2);
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

}
