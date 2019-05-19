<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<div style="float:left;width:70%;" class="mbody">
 	<div class="post_title">
 		<div style="float:left;">
			<img src="${pageContext.request.contextPath}/static/images/about_icon.png"/>
 		</div>
		<div style="margin-top:-1px;float:left;">${pageTitle}</div>
	</div>
	<div style="padding: 30px" class="MyInfo">
		${blogger.profile }
	</div>
</div>