<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="java.sql.*,com.xue.bbs.utils.*,com.xue.bbs.bean.*" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<% 
	/*
	*此页面为删除帖子（树型）
	*/
	//验证session
	String str = (String)session.getAttribute("user");
	if (str != null && str.equals("success")) {
		//从主页帖子页面获取需要删除的帖子id 帖子的pid 帖子的isleaf
		int id = Integer.parseInt(request.getParameter("id") == null ? "-1" : request.getParameter("id"));
		int pid = Integer.parseInt(request.getParameter("pid") == null ? "-1" : request.getParameter("pid"));
		boolean isleaf = Boolean.parseBoolean(request.getParameter("isleaf"));
		//获取数据库连接
		Connection con = DBUtils.getConnection();
		//判断帖子信息
		if (id != -1 && pid != -1) {
			//递归删除子帖
			Delete.treeDelete(con, id, isleaf);
			
			//判断父贴是否还有子帖，有则不动，否则将isleaf改为0
			Delete.updateIsLeaf(pid, con);
		}
		
	} else {
		response.sendRedirect("err.jsp");
	}
%>
<html>

	<head>
	</head>
	<body>
		<span>删除成功</span>
		<span id="time" style="background:red">3</span>秒后自动跳转,如果不跳转请点击
		<script language="JavaScript1.2" type="text/javascript">
			function delayURL(url) {
				var delay = document.getElementById("time").innerHTML;
				//alert(delay);
				if(delay > 0) {
					delay--;
					document.getElementById("time").innerHTML = delay;
				} else {
					window.top.location.href = url;
				}
				//每隔一秒钟调用一次
				setTimeout("delayURL('" + url + "')", 1000);

			}
		</script>

		<a href="article.jsp">主题列表</a>

		<script type="text/javascript">
			delayURL("article.jsp");
		</script>
	</body>

</html>