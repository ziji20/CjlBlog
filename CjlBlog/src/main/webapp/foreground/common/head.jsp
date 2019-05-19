<%@ page language="java" contentType="textml; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!-- 博客首页头部 -->
	<header>
	  <div class="logo">
	  	<div>
	  		<a href="${pageContext.request.contextPath}/index.html" id="go-home"><img alt="博客" src="${pageContext.request.contextPath}/static/images/logo.png"></a>
	  	</div>
	  	<div class="tianqi">
		 	<!--<iframe style="float: right;margin: auto;" width="420" scrolling="no" height="60" frameborder="0" allowtransparency="true" src="http://i.tianqi.com/index.php?c=code&id=12&icon=1&num=5"></iframe>
		 	-->
		 	
		</div>
	  </div>
	  <nav id="nav" style="clear:both;padding-top: 10px;">
	    <ul>
	      <li><a href="${pageContext.request.contextPath}/index.html" id="go-home">首页</a></li>
	      <li><a href="${pageContext.request.contextPath}/blogger/goLeavingMessage.html">留言</a></li>
	      <li><a href="${pageContext.request.contextPath}/blogger/aboutMe.html">关于博主</a></li>
	      <li><a href="${pageContext.request.contextPath}/blogger/myTeam.html">我的团队</a></li>
	    </ul>
	  </nav>   
    <script>
		window.onload = function ()
		{
		var obj=null;
		var As=document.getElementById('nav').getElementsByTagName('a');
		obj = As[0];
		for(i=1;i<As.length;i++){if(window.location.href.indexOf(As[i].href)>=0)
		obj=As[i];}
		obj.id='selected'
		}
	</script> 
	</header>
	<!-- 博客在手机上显示 -->
	<div class="mnav" id="mnav">
	    <ul>
	      <li><a href="${pageContext.request.contextPath}/index.html" id="selected">首页</a></li>
	      <li><a href="${pageContext.request.contextPath}/blogger/goLeavingMessage.html">留言</a></li>
	      <li><a href="${pageContext.request.contextPath}/blogger/aboutMe.html">关于博主</a></li>
	      <li><a href="${pageContext.request.contextPath}/blogger/myTeam.html">我的团队</a></li>
	    </ul>
	</div>
	<script>
		window.onload = function ()
		{
		var obj=null;
		var As=document.getElementById('mnav').getElementsByTagName('a');
		for(i=0;i<As.length;i++){
			obj=As[i];
			obj.id=' ';
		}
		obj = As[0];
		for(i=1;i<As.length;i++){if(window.location.href.indexOf(As[i].href)>=0)
		obj=As[i];}
		obj.id='selected'
		}
	</script> 