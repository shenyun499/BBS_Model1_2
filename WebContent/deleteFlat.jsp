<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="java.sql.*,com.xue.bbs.utils.*,com.xue.bbs.beanflat.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<% 
	/*
	*此页面为删除帖子（平板）
	*/
	//验证session
	String str = (String)session.getAttribute("user");
	if (str != null && str.equals("success")) {
		//从主页帖子页面获取需要删除的主帖子rootid，要删除它的所有帖子
		int rootid = Integer.parseInt(request.getParameter("rootid") == null ? "-1" : request.getParameter("rootid"));
		
		//判断帖子信息
		if (rootid != -1) {
			DeleteFlat.delete(rootid);
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

		<a href="articleFlat.jsp">主题列表</a>

		<script type="text/javascript">
			delayURL("articleFlat.jsp");
		</script>
	</body>

</html>