<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta name="description" content="">
<meta name="author" content="">
<title>博客系统后台登录页面-Powered by ziji20</title>
<script	src="${pageContext.request.contextPath}/static/js/jquery-2.1.1.js"></script>
<link href="${pageContext.request.contextPath}/static/css/login.css" rel="stylesheet">
<script type="text/javascript">
	function checkForm() {
		var userName = $("#userName").val();
		var password = $("#password").val();
		if (userName == null || userName == "") {
			$("#err").html("用户名不能为空！");
			return false;
		}
		if (password == null || password == "") {
			$("#err").html("密码不能为空！");
			return false;
		}
		return true;
	}
</script>
</head>
<body>
	<div id="sky"></div>
	<div id="head"></div>
	<div id="middle">
		<form action="${pageContext.request.contextPath}/blogger/login.do"	method="post" onsubmit="return checkForm()">
			<ul style="text-align: center;">
				<li style="font-size: 48px">ZijiBlog</li>
				<li><input class="name" id="userName" name="userName" placeholder="请输入用户名" type="text" value="${blogger.userName }"></li>
				<li><input class="pwd" id="password" name="password" placeholder="请输入密码" type="password"  value="${blogger.password }"></li>
				<li><button id="login">立即登录</button></li>
				<li><span id="err" style="display: inline-block;">${errorInfo }</span></li>
			</ul>
		</form>
	</div>
	<div style="text-align: center; padding-top: 30px" id="footer">Copyright ©
		2018 紫极20 版权所有 <a href="${pageContext.request.contextPath}/changePassword.jsp" style="color:white;">忘记密码</a></div>
	<div id="cloud"></div>
</body>
</html>