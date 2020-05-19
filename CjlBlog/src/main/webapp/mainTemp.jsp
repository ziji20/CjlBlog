<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<link href="${pageContext.request.contextPath}/static/css/base.css" rel="stylesheet">
<link href="${pageContext.request.contextPath}/static/css/index.css" rel="stylesheet">
<link href="${pageContext.request.contextPath}/static/css/m.css" rel="stylesheet">

<script src="${pageContext.request.contextPath}/static/bootstrap3/js/jquery-1.11.2.min.js"></script>
<script src="${pageContext.request.contextPath}/static/bootstrap3/js/bootstrap.min.js"></script>
<title>${pageTitle }-Powered by ziji20</title>
<script type="text/javascript">
	function checkData(){
		var q=document.getElementById("q").value.trim();
		if(q==null || q==""){
			alert("请输入您要查询的关键字！");
			return false;
		}else{
			return true;
		}
	}
	 $(function () {
	        var ie6 = document.all;
	        var dv = $('#l_box'), st;
	        dv.attr('otop', dv.offset().top); //存储原来的距离顶部的距离
	        $(window).scroll(function () {
	            st = Math.max(document.body.scrollTop || document.documentElement.scrollTop);
	            if (st > parseInt(dv.attr('otop'))) {
	                if (ie6) {//IE6不支持fixed属性，所以只能靠设置position为absolute和top实现此效果
	                    dv.css({ position: 'absolute', top: st });
	                }
	                else if (dv.css('position') != 'fixed') dv.css({ 'position': 'fixed', top: 0,'right':'17%'});
	            } else if (dv.css('position') != 'static') dv.css({ 'position': 'static' });
	        });
	    });
</script>
</head>

<body>
	<!-- 博客首页头部 -->
	<jsp:include page="/foreground/common/head.jsp" />
	<div class="music" style="z-index:100;position: absolute;left: 60%;top: 72px;">
		<div id="skPlayer"></div>
			<!--拉取网易云音乐  -->
		   <!-- <script src="/static/js/skPlayer.min.js"></script>
		    <script>
		        var player = new skPlayer({
		            autoplay: false,
		            listshow: false,
		            mode: 'listloop',
		            music: {
		                type: 'cloud',
		                source: 2687686046
		            }
		        });
		    </script>  -->
		    <!-- 拉取QQ音乐 -->
		    <jsp:include page="/foreground/common/music.jsp" />
	</div>
	<!-- 主体 -->
	<article>
		<!-- 最新博客 -->
		<div style="margin:0 auto;">
			<jsp:include page="${mainPage }" />
		</div>
		 <!-- 博主 -->
		 <jsp:include page="/foreground/common/right.jsp" />
	</article>
	<!-- 底部 -->	
	<jsp:include page="/foreground/common/foot.jsp" />
</body>
</html>
