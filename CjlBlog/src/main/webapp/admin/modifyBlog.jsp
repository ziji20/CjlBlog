<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>修改博客页面</title>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/jquery-easyui-1.3.3/themes/default/easyui.css">
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/jquery-easyui-1.3.3/themes/icon.css">
<script type="text/javascript" src="${pageContext.request.contextPath}/static/jquery-easyui-1.3.3/jquery.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/static/jquery-easyui-1.3.3/jquery.easyui.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/static/jquery-easyui-1.3.3/locale/easyui-lang-zh_CN.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath }/static/js/My97DatePicker_date/WdatePicker.js"></script> 

<script type="text/javascript" charset="gbk" src="${pageContext.request.contextPath}/static/ueditor/ueditor.config.js"></script>
<script type="text/javascript" charset="gbk" src="${pageContext.request.contextPath}/static/ueditor/ueditor.all.min.js"> </script>
<script type="text/javascript" charset="gbk" src="${pageContext.request.contextPath}/static/ueditor/lang/zh-cn/zh-cn.js"></script>
<script type="text/javascript">
	
	function submitData(){
		var title=$("#title").val();
		var blogTypeId=$("#blogTypeId").combobox("getValue")
		var content=UE.getEditor('editor').getContent()
		var keyWord=$("#keyWord").val();
		var setDate=$("#setDate").val();
		var clickHit=$("#clickHit").val();
		var privateBlog=$('input:radio[name="privateBlog"]:checked').val();
		
		if(title==null || title==''){
			alert("请输入标题！");
		}else if(blogTypeId==null || blogTypeId==''){
			alert("请选择博客类别！");
		}else if(content==null || content==''){
			alert("请填写内容！"); 
		}else{
			$.post("${pageContext.request.contextPath}/admin/blog/save.do",{'id':'${param.id}','title':title,'blogType.id':blogTypeId,
				'contentNoTag':UE.getEditor('editor').getContentTxt(),'content':content,
				'summary':UE.getEditor('editor').getContentTxt().substr(0,155),'keyWord':keyWord,
				'clickHit':clickHit,'setDate':setDate,'privateBlog':privateBlog},function(result){
				if(result.success){
					alert("博客修改成功！");
					window.parent.tabsClose();
				}else{
					alert("博客修改失败！");
				}
			},"json");
		}
	}

</script>
</head>
<body style="margin: 10px">
<div id="p" class="easyui-panel" title="修改博客" style="padding: 10px">
	<table cellspacing="20px">
		<tr>
			<td width="80px">博客标题：</td>
			<td>
				<input type="text" id="title" name="title" style="width: 400px"/>
			</td>
		</tr>
		<tr>
			<td>所属类别：</td>
			<td>
				<select class="easyui-combobox" style="width: 154px" id="blogTypeId" name="blogType.id" editable="false" panelHeight="auto">
					<option value="">请选择博客类别...</option>
					<c:forEach var="blogType" items="${blogTypeCountList }">
						<option value="${blogType.id }">${blogType.typeName }</option>
					</c:forEach>
				</select>
			</td>
		</tr>
		<tr>
			<td valign="top">博客内容：</td>
			<td>
				<script id="editor" name="content" type="text/plain" style="width:100%;height:500px;"></script>
			</td>
		</tr>
		<tr>
			<td>摘要：</td>
			<td>
				<textarea rows="5" cols="100" id="summary" name="summary" style="resize:none" onkeyup="this.value = this.value.substring(0,300)" placeholder="请输入，不输入默认是标题"></textarea>&nbsp;
			</td>
		</tr>
		<tr>
			<td>关键字：</td>
			<td>
				<input type="text" id="keyWord" name="keyWord" style="width: 400px"/>&nbsp;(多个关键字中间用空格隔开)
			</td>
		</tr>
		<tr>
			<td>分布时间：</td>
			<td>
				<input type="text" id="setDate" name="setDate" onclick="WdatePicker({el:this,dateFmt:'yyyy-MM-dd HH:mm:ss'})" style="width: 200px"/>&nbsp;
			</td>
		</tr>
		<tr>
			<td>是否私有：</td>
			<td>
				<input type="radio" id="isprivateBlog" name="privateBlog" value="1" />&nbsp;是
				<input type="radio" id="noprivateBlog" name="privateBlog" value="0" checked="checked"/>&nbsp;否
			</td>
		</tr>
		<tr>
			<td>阅读次数：</td>
			<td>
				<input type="text" id="clickHit" name="clickHit" style="width: 80px"/>&nbsp;
			</td>
		</tr>
		<tr>
			<td></td>
			<td>
				<a href="javascript:submitData()" class="easyui-linkbutton" data-options="iconCls:'icon-submit'">修改博客</a>
			</td>
		</tr>
	</table>
</div>

<!-- 实例化编辑器 -->
<script type="text/javascript">
    var ue = UE.getEditor('editor');
    
    ue.addListener("ready",function(){
    	// 通过ajax请求数据
    	UE.ajax.request("${pageContext.request.contextPath}/admin/blog/findById.do",
    			{
    				method:"post",
    				async:false,
    				data:{"id":"${param.id}"},
    				onsuccess:function(result){
    					result=eval("("+result.responseText+")");
    					$("#title").val(result.title);
    					$("#summary").val(result.summary);
    					$("#keyWord").val(result.keyWord);
    					$("#clickHit").val(result.clickHit);
    					$("#setDate").val(result.setDate);
    					$("#blogTypeId").combobox("setValue",result.blogType.id);
    					if(result.privateBlog==1){
    						$("#noprivateBlog").removeAttr("checked")
    						$("#isprivateBlog").attr("checked","checked");
    					}
    					UE.getEditor('editor').setContent(result.content);
    				}
   			});
    });
</script>



</body>
</html>