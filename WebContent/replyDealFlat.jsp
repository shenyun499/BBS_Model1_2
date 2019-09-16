<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="java.sql.*,com.xue.bbs.utils.*,com.xue.bbs.beanflat.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%
	/*
	*此页面为回复帖子（平板）
	*/
	//设置字符编码
	request.setCharacterEncoding("utf-8");
	//从回复帖子页面获取帖子信息
	int id = Integer.parseInt(request.getParameter("pid") == null ? "-1" : request.getParameter("pid"));
	int rootid = Integer.parseInt(request.getParameter("rootid") == null ? "-1" : request.getParameter("rootid"));
	String title = request.getParameter("title");
	String cont = request.getParameter("cont");
	
	//验证session
	String str = (String)session.getAttribute("user");
	if (str != null && str.equals("success") && id != -1 && rootid != -1) {
		ReplyDealFlat.replyDealFlat(id, rootid, title, cont);
	} else {
		response.sendRedirect("err.jsp");
	}

%>

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

<html>

<head>

	<body>
		<p>回复成功!</p><br>
		<a href="articleFlat.jsp">点击返回主论坛</a>
	</body>

</html>