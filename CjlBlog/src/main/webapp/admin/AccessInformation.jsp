<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>访问管理页面</title>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/jquery-easyui-1.3.3/themes/default/easyui.css">
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/jquery-easyui-1.3.3/themes/icon.css">
<script type="text/javascript" src="${pageContext.request.contextPath}/static/jquery-easyui-1.3.3/jquery.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/static/jquery-easyui-1.3.3/jquery.easyui.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/static/jquery-easyui-1.3.3/locale/easyui-lang-zh_CN.js"></script>
<script type="text/javascript">
	
	function deleteInformation(){
		if(confirm("确定删除所有访问者信息？")){
			$.post("${pageContext.request.contextPath}/admin/blog/deleteAccessInformation.do",{},function(result){
				if(result.success){
					$.messager.alert("系统提示","已删除！");
				}else{
					$.messager.alert("系统提示","删除失败！");
				}
				$("#dg").datagrid("reload");
			},"json");
		}
	}
	function getvnInformation(){
		if(confirm("确定发送访问量到邮箱？")){
			$.post("${pageContext.request.contextPath}/admin/blog/getVNItoEmail.do",{},function(result){
				if(result.success){
					$.messager.alert("系统提示","已发送！");
				}else{
					$.messager.alert("系统提示",result.error);
				}
			},"json");
		}
	}
</script>
</head>
<body style="margin: 1px">
<table id="dg" title="评论管理" class="easyui-datagrid" 
  fitColumns="true" pagination="true" rownumbers="true"
  url="${pageContext.request.contextPath}/admin/blog/getAccessinFormationList.do" fit="true" toolbar="#tb">
  <thead>
  	<tr>
  		<th field="id" width="50" align="center">访问ip</th>
  		<th field="count" width="50" align="center">访问数量</th>
  		<th field="time" width="100" align="center">访问时间</th>
  		<th field="address" width="200" align="center">访问地址</th>
  	</tr>
  </thead>
</table>
<div id="tb">
	<div>
		<a href="javascript:deleteInformation()" class="easyui-linkbutton" iconCls="icon-remove" plain="true">删除所有访问</a>
		<a href="javascript:getvnInformation()" class="easyui-linkbutton" iconCls="icon-redo" plain="true">发送到邮箱</a>
	</div>
</div>

</body>
</html>