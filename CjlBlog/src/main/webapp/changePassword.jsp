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
<SCRIPT type="text/javascript">
	function checkForm() {
		var newPassowrd = $("#newPassowrd").val();
		var is_NewPassowrd = $("#is_NewPassowrd").val();
		var vcod = $("#vcod").val();
		if (is_NewPassowrd == null || is_NewPassowrd == "") {
			$("#err").html("密码不能为空！");
			return false;
		}
		if (newPassowrd != is_NewPassowrd) {
			$("#err").html("两次密码不一致！");
			return false;
		}
		if (vcod == null || vcod == "") {
			$("#err").html("验证码不能为空！");
			return false;
		}
		return true;
	}

	function ForgetPassword(){
		$.post("${pageContext.request.contextPath}/blogger/SendVcod.do",{},function(result){
			if(result.success){
				alert("已发送验证码到邮箱!,验证码将在5分钟之后失效!");
				
			}else{
				alert("发送失败!");
			}
		},"json");
	}
</SCRIPT>
</head>
<body>
	<div id="sky"></div>
	<div id="head"></div>
	<div id="middle">
		<form action="${pageContext.request.contextPath}/blogger/forgetPassword.do" method="post" onsubmit="return checkForm()">
			<ul style="text-align: center;">
				<li style="font-size: 48px">ZijiBlog</li>
				<li><input class="pwd" id="newPassowrd" name="newPassowrd" placeholder="请输入新密码" type="password" value="${blogger.newPassowrd }"></li>
				<li><input class="pwd" id="is_NewPassowrd" name="is_NewPassowrd" placeholder="确认新密码" type="password" value="${blogger.newPassowrd }"></li>
				<li><input class="name" id="vcod" name="vcod" placeholder="请输入验证码" type="text"><a herf="#" onclick=""></a></li>
				<li><button type="submit" id="login">立即修改</button></li>
			</ul>
		</form>
		<ul style="text-align: center;">
			<li><button class="ForgetPassword" onclick="ForgetPassword()">获取验证码</button></li>
				<li><span id="err" style="display: inline-block;">${errorInfo }</span></li>
		</ul>
	</div>
	<div style="text-align: center; padding-top: 30px" id="footer">Copyright ©
		2018 紫极20 版权所有 <a href="${pageContext.request.contextPath}/login.jsp" style="color:white;">登陆</a></div>
	<div id="cloud"></div>
</body>
</html>
