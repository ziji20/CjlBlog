<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<script type="text/javascript" src="${pageContext.request.contextPath}/static/ueditor/third-party/SyntaxHighlighter/shCore.js"></script>
<style type="text/css">
	
</style>
<script type="text/javascript">
	function loadimage(){
		document.getElementById("randImage").src="${pageContext.request.contextPath}/image.jsp?"+Math.random();
	}
	function submitData(){
		var name=$("#author").val();
    	var content=$("#content").val();
    	var contactInformation=$("#contactInformation").val();
    	var imageCode=$("#imageCode").val();
    	if(name==null || name==""){
    		alert("请输入你的尊姓大名！");
    	}else  if(contactInformation==null || contactInformation==""){
    		alert("请输入你的联系方式！");
    	}else if(content==null || content==""){
    		alert("请输入你的留言！");
    	}else if(content.length<8){
    		alert("留言不少于8位！");
    	}else if(imageCode==null || imageCode==""){
    		alert("请填写验证码！");
    	}else{
    		$.post("${pageContext.request.contextPath}/lmessage/save.do",{'name':name,'contactInformation':contactInformation,'content':content,'imageCode':imageCode},function(result){
    			if(result.success){
    				window.location.reload();
    				alert("留言成功！");
    			}else{
    				alert(result.errorInfo);
    			}
    		},"json");
    	}
    }
 </script>
<link rel="stylesheet" href="${pageContext.request.contextPath}/static/bootstrap3/css/bootstrap.min.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/static/bootstrap3/css/bootstrap-theme.min.css">
<link href="${pageContext.request.contextPath}/static/css/blog.css" rel="stylesheet">
<link href="${pageContext.request.contextPath}/static/css/m.css" rel="stylesheet">
<div style="float:left;width:68%" class="mbody">
 	<div class="post_title">
 		<div style="float:left;">
			<img src="${pageContext.request.contextPath}/static/images/about_icon.png"/>
 		</div>
		<div style="margin-top:-1px;float:left;">${pageTitle}</div>
	</div>
	<div class="data_list" style="border:0;clear:both;" >
		<div>
			<ul>
				<li style="margin-bottom:8px;">
					姓名:&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="text" id="author" name="author" placeholder="**" maxlength="8" style="background:transparent;border:1px solid #ffffff"/>
				</li>
				<li style="margin-bottom:8px;">
					联系方式:<input type="text" id="contactInformation" name="contactInformation" placeholder="QQ或微信" style="background:transparent;border:1px solid #ffffff"/>
				</li>
				<li>
					内容:<textarea rows="3" id="content" name="content" placeholder="来说两句吧..." style="width: 100%;background:transparent;border:1px solid #ffffff"></textarea>
				</li>
			</ul>
		</div>
		<div class="verCode" style="margin-top:-10px;padding-left:0px;">
			验证码：<input type="text" value="${imageCode }" name="imageCode"  id="imageCode" size="10" onkeydown= "if(event.keyCode==13)form1.submit()" style="background:transparent;border:1px solid #ffffff"/>&nbsp; 
		</div>
		<img onclick="javascript:loadimage();"  title="换一张试试" name="randImage" id="randImage" src="${pageContext.request.contextPath}/image.jsp" width="60" height="20" border="1" align="middle" style="margin-top:10px;">
		<div class="publishButton" style="float:lefe;padding-top:20px">
			<button class="btn btn-primary" type="button" onclick="submitData()">留言</button>
		</div>
	</div>
</div>

