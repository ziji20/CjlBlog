package com.cjl.util;

/**
 * 分页工具类
 * @author Administrator
 *
 */
public class PageUtil {

	/**
	 * 生成分页代码
	 * @param targetUrl 目标地址
	 * @param totalNum 总记录数
	 * @param currentPage 当前页
	 * @param pageSize 每页大小
	 * @return
	 */
	public static String genPagination(String targetUrl,long totalNum,int currentPage,int pageSize,String param){
		long totalPage=totalNum%pageSize==0?totalNum/pageSize:totalNum/pageSize+1;
		if(totalPage==0){
			return "未查询到数据";
		}else{
			/*StringBuffer pageCode=new StringBuffer();
			pageCode.append("<a href='"+targetUrl+"?page=1&"+param+"'>首页</a>");
			if(currentPage>1){
				pageCode.append("<a href='"+targetUrl+"?page="+(currentPage-1)+"&"+param+"'>上一页</a>");			
			}else{
				pageCode.append("<li class='disabled'><a href='"+targetUrl+"?page="+(currentPage-1)+"&"+param+"'>上一页</a>");		
			}
			for(int i=currentPage-2;i<=currentPage+2;i++){
				if(i<1||i>totalPage){
					continue;
				}
				if(i==currentPage){
					pageCode.append("<li class='active'><a href='"+targetUrl+"?page="+i+"&"+param+"'>"+i+"</a>");	
				}else{
					pageCode.append("<a href='"+targetUrl+"?page="+i+"&"+param+"'>"+i+"</a>");	
				}
			}
			if(currentPage<totalPage){
				pageCode.append("<a href='"+targetUrl+"?page="+(currentPage+1)+"&"+param+"'>下一页</a>");		
			}else{
				pageCode.append("<li class='disabled'><a href='"+targetUrl+"?page="+(currentPage+1)+"&"+param+"'>下一页</a>");	
			}
			pageCode.append("<a href='"+targetUrl+"?page="+totalPage+"&"+param+"'>尾页</a>");*/
			StringBuffer pageCode=new StringBuffer();
			pageCode.append("<a href='"+targetUrl+"?page=1&"+param+"' id='homepage'>首页</a>");
			if(currentPage>1){
				pageCode.append("<a href='"+targetUrl+"?page="+(currentPage-1)+"&"+param+"' id='prev-page'>上一页</a>");			
			}else{ 
				pageCode.append("<a id='prev-page' class='allpage'>上一页</a>");		
			}
			for(int i=currentPage-2;i<=currentPage+2;i++){
				if(i<1||i>totalPage){
					continue;
				}
				if(i==currentPage){
					pageCode.append("<a href='"+targetUrl+"?page="+i+"&"+param+"' class='curPage'>"+i+"</a>");	
				}else{
					pageCode.append("<a href='"+targetUrl+"?page="+i+"&"+param+"'>"+i+"</a>");	
				}
			}
			if(currentPage<totalPage){
				pageCode.append("<a href='"+targetUrl+"?page="+(currentPage+1)+"&"+param+"' id='next-page'>下一页</a>");		
			}else{
				pageCode.append("<a class='allpage' id='next-page'>下一页</a>");	
			}
			pageCode.append("<a href='"+targetUrl+"?page="+totalPage+"&"+param+"' id='tailpage'>尾页</a>");
			return pageCode.toString();
		}
	}
	

	
	
}
