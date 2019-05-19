<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" href="${pageContext.request.contextPath}/static/bootstrap3/css/bootstrap.min.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/static/bootstrap3/css/bootstrap-theme.min.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/blog.css">

<script src="${pageContext.request.contextPath}/static/bootstrap3/js/jquery-1.11.2.min.js"></script>
<script src="${pageContext.request.contextPath}/static/bootstrap3/js/bootstrap.min.js"></script>
<title>博客主页</title>
<style type="text/css">
	body{
		padding-top:10px;
		padding-bottom:40px;
		background-color: #F8F8FF;
		font-family: microsoft yahei;
	}
</style>
</head>

<body>
	<div class="container">
		<div class="row">
			<div class="col-md-4">
				<img alt="博客" src="${pageContext.request.contextPath}/static/images/logo.png">
			</div>
			<div class="col-md-8">
				<iframe style="float: right;" width="420" scrolling="no" height="60" frameborder="0" allowtransparency="true" src="http://i.tianqi.com/index.php?c=code&id=12&icon=1&num=5"></iframe>
			</div>
		</div>
		
		<div class="row" style="padding-top: 10px">
			<div class="col-md-12">
				<nav class="navbar navbar-default">
				  <div class="container-fluid">				    
				    <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
				      <ul class="nav navbar-nav">
				      	<li><a class="navbar-brand" href="#">博客首页</a></li>
				        <li><a class="navbar-brand" href="#">关于博主</a></li>
				      </ul>
				      <form class="navbar-form navbar-right" role="search">
				        <div class="form-group">
				          <input type="text" class="form-control" placeholder="请输入要查询的关键字">
				        </div>
				        <button type="submit" class="btn btn-default">搜索</button>
				      </form>
				    </div><!-- /.navbar-collapse -->
				  </div><!-- /.container-fluid -->
				</nav>
			</div>
		</div>
		
		<div class="row">	
		 
		  
		  <div class="col-md-9">
			  <div class="data_list">
				<div class="data_list_title">
					<img
						src="${pageContext.request.contextPath}/static/images/list_icon.png" />
					最新博客
				</div>
			
				<div class="datas">
					<ul>
						<c:forEach var="blog" items="${blogList }">
							<li style="margin-bottom: 30px"><span class="date"><a
									href="${pageContext.request.contextPath}/blog/articles/${blog.id }.html"><fmt:formatDate
											value="${blog.releaseDate }" type="date" pattern="yyyy年MM月dd日" /></a></span>
								<span class="title"><a
									href="${pageContext.request.contextPath}/blog/articles/${blog.id }.html">${blog.title }</a></span>
								<span class="summary">摘要: ${blog.summary }...</span> <span
								class="img"> <c:forEach var="image"
										items="${blog.imageList }">
										<a
											href="${pageContext.request.contextPath}/blog/articles/${blog.id }.html">${image}</a>
									  		&nbsp;&nbsp;
								  		</c:forEach>
							</span> <span class="info">发表于 <fmt:formatDate
										value="${blog.releaseDate }" type="date"
										pattern="yyyy-MM-dd HH:mm" /> 阅读(${blog.clickHit })
									评论(${blog.replyHit })
							</span></li>
							<hr
								style="height:5px;border:none;border-top:1px dashed gray;padding-bottom:  10px;" />
						</c:forEach>
					</ul>
				</div>
			</div>
		  </div>
		 
		   <div class="col-md-3">
		  	<div class="data_list">
				<div class="data_list_title">
					<img src="${pageContext.request.contextPath}/static/images/user_icon.png"/>
					博主
				</div>
				<div class="user_image">
					<img src="${pageContext.request.contextPath}/static/userImages/${blogger.imageName}"></img>
				</div>
				<div class="nickName">${blogger.nickName }</div>
				<div class="userSign">${blogger.sign }</div>
			</div>	
			
			<div class="data_list">
				<div class="data_list_title">
					<img src="${pageContext.request.contextPath}/static/images/byType_icon.png"/>
					按日志类别
				</div>
				<div class="datas">
					<ul>
						<c:forEach var="blogTypeCount" items="${blogTypeCountList }">
							<li><span><a href="${pageContext.request.contextPath}/index.html?typeId=${blogTypeCount.id}">${blogTypeCount.typeName }(${blogTypeCount.blogCount })</a></span></li>
						</c:forEach>
						
					</ul>
				</div>
			</div>
			
			<div class="data_list">
				<div class="data_list_title">
					<img src="${pageContext.request.contextPath}/static/images/byDate_icon.png"/>
					按日志日期
				</div>
				<div class="datas">
					<ul>
						<c:forEach var="blogCount" items="${blogCountList }">
							<li><span><a href="${pageContext.request.contextPath}/index.html?releaseDateStr=${blogCount.releaseDateStr}">${blogCount.releaseDateStr }(${blogCount.blogCount })</a></span></li>
						</c:forEach>
						
					</ul>
				</div>
			</div>
			
			<div class="data_list">
				<div class="data_list_title">
					<img src="${pageContext.request.contextPath}/static/images/link_icon.png"/>
					友情链接
				</div>
				<div class="datas">
					<ul>
						<c:forEach var="link" items="${linkList }">
							<li><span><a href="${link.linkUrl }" target="_blank">${link.linkName }</a></span></li>
						</c:forEach>
					</ul>
				</div>
			</div>
		  </div>
			
		</div>
		
		<div class="row">
			<div class="col-md-12" >
				<div class="footer" align="center" style="padding-top: 120px" >
					<font>Copyright © 2018紫极20博客 版权所有</font>
				</div>
			</div>			
		</div>
	</div>
</body>
</html>
