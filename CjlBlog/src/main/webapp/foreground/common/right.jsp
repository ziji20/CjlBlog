<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<script type="text/javascript">
/* window.onscroll=function(){
    var scrollTop = document.documentElement.scrollTop||document.body.scrollTop;
    if(scrollTop>=document.body.offsetHeight-document.documentElement.clientHeight)
    {
        document.getElementById("l_box").style.position="fixed";
        document.getElementById("l_box").style.top ="19.2%";  
        document.getElementById("l_box").style.right="24%";
        document.getElementById("l_box").style.width=300+"px";  
    }
} */
</script>
<div class="l_box" id="l_box" style="float:right;"><!-- width:300px;position: fixed;top:19.2%; right: 10%; -->
   	<div class="about_me">
    	<h2>关于我</h2>
     	<ul>
        	<div class="avatar">
				<a href="${pageContext.request.contextPath}/blogger/aboutMe.html" style="background: url(${pageContext.request.contextPath}/static/userImages/${blogger.imageName})">
					<span>${blogger.nickName}</span>
				</a>
			</div>
        	<p style="text-align:center;"><b>${blogger.nickName}<br>(${blogger.sign})</b></p>
				
      	</ul>
	</div>
    <div class="search">
      <form action="${pageContext.request.contextPath}/blog/q.html" method="post" onsubmit="return checkData()" name="searchform" id="searchform">
        <input name="q" id="q" class="input_text" value="${q }"  placeholder="请输入要查询的关键字..." type="text">
        <input name="Submit" class="input_submit" value="搜索" type="submit">
      </form>
    </div>
    <div class="fenlei">
      <h2>文章分类</h2>
      <ul>
      	<c:forEach var="blogTypeCount" items="${blogTypeCountList }">
			<li><span><a href="${pageContext.request.contextPath}/index.html?typeId=${blogTypeCount.id}">${blogTypeCount.typeName }(${blogTypeCount.blogCount })</a></span></li>
		</c:forEach>
      </ul>
    </div>
    <div class="timefenlei">
      <h2>日期分类</h2>
      <ul>
      	<c:forEach var="blogCount" items="${blogCountList }">
			<li><span><a href="${pageContext.request.contextPath}/index.html?releaseDateStr=${blogCount.releaseDateStr}">${blogCount.releaseDateStr }(${blogCount.blogCount })</a></span></li>
		</c:forEach>
      </ul>
    </div>
    <div class="links">
      <h2>友情链接</h2>
      <ul>
		<c:forEach var="link" items="${linkList }">
			<a href="${link.linkUrl }" target="_blank">${link.linkName }</a>
		</c:forEach>
	   </ul>
	</div>
</div>