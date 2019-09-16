<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="java.sql.*,com.xue.bbs.utils.*,java.util.*,com.xue.bbs.pojo.*,java.text.*,com.xue.bbs.beanflat.*" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%
	/*
	*此功能为展示搜索结果
	*/
	
	//创建页对象
	Page pages = new Page();
	//定义List集合，存放Article
	List<Article> list = new ArrayList<Article>();
	//获取搜索关键字
	request.setCharacterEncoding("utf-8");
	String keywords = request.getParameter("keywords") == null ? "" : request.getParameter("keywords");
	
	//验证session
	String str = (String)session.getAttribute("user");
	if (str != null && str.equals("success")) {
		SearchResult.searchResult(pages, request, list, keywords);
	} else {
		response.sendRedirect("err.jsp");
	}
%>

<html>

	<head>
		<title>Java论坛搜索结果</title>
		<meta http-equiv="content-type" content="text/html; charset=utf8">
		<link rel="stylesheet" type="text/css" href="images/style.css" title="Integrated Styles">
		<script language="JavaScript" type="text/javascript" src="images/global.js"></script>
		<link rel="alternate" type="application/rss+xml" title="RSS" href="http://bbs.chinajavaworld.com/rss/rssmessages.jspa?forumID=20">
		<script language="JavaScript" type="text/javascript" src="images/common.js"></script>
	</head>

	<body style="margin:0 15px 0 15px;">
		<table border="0" cellpadding="0" cellspacing="0" width="100%">
			<tbody>
				<tr>
					<td width="40%"><img src="images/header-stretch.gif" alt="" border="0" height="57" width="100%">
					</td>
					<td width="1%"><img src="images/header-right.gif" alt="" height="57" border="0"></td>
				</tr>
			</tbody>
		</table>
		<br>
		<div id="jive-forumpage">
			<table border="0" cellpadding="0" cellspacing="0" width="100%">
				<tbody>
					<tr valign="top">
						<td width="98%">
							<p class="jive-breadcrumbs">
								<!-- 退出按钮 -->
								<a style="color:blue;float:right;" href="exit.jsp">退出</a>
								<!-- 切换模式 -->
								<a style="color:blue;float:right;margin-right:10px;" href="article.jsp">切换到树型模式</a>
								<!-- 切换模式 -->
								<a style="color:blue;float:right;margin-right:10px;" href="articleFlat.jsp">切换到平板模式</a>
							</p>
							<p class="jive-breadcrumbs">论坛: Java语言交流
							</p>
							<p class="jive-description"> <h2 style="color:red;text-align: center;">搜索结果 </h2> </p>
						</td>
					</tr>
				</tbody>
			</table>
			<br>
			<table border="0" cellpadding="3" cellspacing="0" width="100%">
				<tbody>
					<tr valign="top">
						<td><span class="nobreak">
       	     第<%=pages.getCurrentPage() %>页
       	     共<%=pages.getTotalPage() %>页
          <span class="jive-paginator"> [
          <% if (keywords.equals("") || list.size() == 0) { %>
	          <a href="javascript:void(0);">首页</a>
	          <a href="javascript:void(0);">上一页</a>
	          <a href="javascript:void(0);">下一页</a>
	          <a href="javascript:void(0);">尾页</a>
           <% } else { %>
	           <a href="searchResult.jsp?currentPage=<%=1 %>&keywords=<%=keywords %>">首页</a>
	           <a href="searchResult.jsp?currentPage=<%=pages.getCurrentPage() - 1 %>&keywords=<%=keywords %>">上一页</a>
	           <a href="searchResult.jsp?currentPage=<%=pages.getCurrentPage() + 1 %>&keywords=<%=keywords %>">下一页</a>
	           <a href="searchResult.jsp?currentPage=<%=pages.getTotalPage() %>&keywords=<%=keywords %>">尾页</a>
           <% } %>
           ] </span> </span>
						</td>
					</tr>
				</tbody>
			</table>
			<table border="0" cellpadding="0" cellspacing="0" width="100%">
				<tbody>
					<tr valign="top">
						<td width="99%">
							<div class="jive-thread-list">
								<div class="jive-table">
									<table summary="List of threads" cellpadding="0" cellspacing="0" width="100%">
										<thead>
											<tr>
												<th class="jive-author"></th>
												<th class="jive-author"> 操作 </th>
												<th class="jive-author"> 主题 </th>
												<th class="jive-author">
													<nobr> 作者 &nbsp; </nobr>
												</th>
												<th class="jive-view-count">
													<nobr> 浏览 &nbsp; </nobr>
												</th>
												<th class="jive-msg-count" nowrap="nowrap"> 回复 </th>
												<th class="jive-last" nowrap="nowrap"> 发布时间 </th>
											</tr>
										</thead>
										<!-- 开始 输入空串或者如果没有找到记录，则显示没有记录 -->
										<% if (keywords.equals("") || list.size() == 0) { %>
										<tbody>
											<tr class="">
												<td class="jive-first" nowrap="nowrap" colspan="7" width="100%">
													<font style="color:red;">没有搜索到相关信息</font>
												</td>
											</tr>
										</tbody>
										<% } else { %>
										
										<!-- 如果记录不为空，则显示信息 -->
										<% for (Article ar : list) { %>
										<tbody>
											<tr class="">
												<td class="jive-first" nowrap="nowrap" width="1%">
													<div class="jive-bullet"> <img src="images/read-16x16.gif" alt="已读" border="0" height="16" width="16">
														<!-- div-->
													</div>
												</td>

												<td nowrap="nowrap" width="1%">
													<!-- 更新 -->
													<a href="modifyFlat.jsp?id=<%=ar.getId() %>&title=<%=ar.getTitle()%>&cont=<%=ar.getCont()%>">更新</a>
													<!-- 删除 -->
													<a href="deleteFlat.jsp?rootid=<%=ar.getRootid() %>">删除</a>
												</td>

												<td class="jive-thread-name" width="95%">
													<a id="jive-thread-1" href="articleDetailFlat.jsp?id=<%=ar.getId()%>&title=<%=ar.getTitle() %>"><%=ar.getCont() %></a>
												</td>
												<td class="jive-author" nowrap="nowrap" width="1%"><span class=""> <a href="http://bbs.chinajavaworld.com/profile.jspa?userID=226030">bjsxt</a> </span></td>
												<td class="jive-view-count" width="1%"> 10000</td>
												<td class="jive-msg-count" width="1%"> 0</td>
												<td class="jive-last" nowrap="nowrap" width="1%"><%=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(ar.getPdate()) %><br>
													<div class="jive-last-post"><br> by:
														<a href="http://bbs.chinajavaworld.com/thread.jspa?messageID=780182#780182" title="jingjiangjun" style="">黄智学</a>
													</div>
												</td>
											</tr>
										</tbody>
										<% } }%>
										<!-- end -->
										
									</table>
								</div>
							</div>
							<div class="jive-legend"></div>
						</td>
					</tr>
				</tbody>
			</table>
			<br>
			<br>
		</div>
	</body>

</html>