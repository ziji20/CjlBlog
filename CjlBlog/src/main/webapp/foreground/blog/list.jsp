<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<!-- 最新博客 -->
<div class="r_box" id="blog_content">
	<c:forEach var="blog" items="${blogList }">
		<li style="margin-bottom: 30px">
			<h3><a href="${pageContext.request.contextPath}/blog/articles/${blog.id }.html">${blog.title }</a></h3><br/>
			<a href="${pageContext.request.contextPath}/blog/articles/${blog.id }.html"><span class="summary">摘要: ${blog.summary }...</span></a>
			<span class="img"> 
			<div style="width:auto;">
			<c:forEach var="image" items="${blog.imageList }">
				<div class="bolg_img">
					<a href="${pageContext.request.contextPath}/blog/articles/${blog.id }.html">${image}</a>
				</div>
			</c:forEach>
			</div>
		</span>
		<br>
		<div style="width:100%;clear:both;">
			<p style="float:right;">发表于 <fmt:formatDate value="${blog.releaseDate }" type="date"	pattern="yyyy-MM-dd HH:mm" /> 阅读(${blog.clickHit })	评论(${blog.replyHit })</p>
		</div>
		</li>
		<hr	style="height:5px;border:none;border-top:1px dashed gray;padding-bottom:  10px;" />
	</c:forEach>
	<!--分页  -->
    <div class="pagelist">
		${pageCode }
	</div>
 </div>
