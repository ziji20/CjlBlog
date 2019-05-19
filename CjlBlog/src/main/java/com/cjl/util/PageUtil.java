package com.cjl.util;

/**
 * ��ҳ������
 * @author Administrator
 *
 */
public class PageUtil {

	/**
	 * ���ɷ�ҳ����
	 * @param targetUrl Ŀ���ַ
	 * @param totalNum �ܼ�¼��
	 * @param currentPage ��ǰҳ
	 * @param pageSize ÿҳ��С
	 * @return
	 */
	public static String genPagination(String targetUrl,long totalNum,int currentPage,int pageSize,String param){
		long totalPage=totalNum%pageSize==0?totalNum/pageSize:totalNum/pageSize+1;
		if(totalPage==0){
			return "δ��ѯ������";
		}else{
			/*StringBuffer pageCode=new StringBuffer();
			pageCode.append("<a href='"+targetUrl+"?page=1&"+param+"'>��ҳ</a>");
			if(currentPage>1){
				pageCode.append("<a href='"+targetUrl+"?page="+(currentPage-1)+"&"+param+"'>��һҳ</a>");			
			}else{
				pageCode.append("<li class='disabled'><a href='"+targetUrl+"?page="+(currentPage-1)+"&"+param+"'>��һҳ</a>");		
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
				pageCode.append("<a href='"+targetUrl+"?page="+(currentPage+1)+"&"+param+"'>��һҳ</a>");		
			}else{
				pageCode.append("<li class='disabled'><a href='"+targetUrl+"?page="+(currentPage+1)+"&"+param+"'>��һҳ</a>");	
			}
			pageCode.append("<a href='"+targetUrl+"?page="+totalPage+"&"+param+"'>βҳ</a>");*/
			StringBuffer pageCode=new StringBuffer();
			pageCode.append("<a href='"+targetUrl+"?page=1&"+param+"' id='homepage'>��ҳ</a>");
			if(currentPage>1){
				pageCode.append("<a href='"+targetUrl+"?page="+(currentPage-1)+"&"+param+"' id='prev-page'>��һҳ</a>");			
			}else{ 
				pageCode.append("<a id='prev-page' class='allpage'>��һҳ</a>");		
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
				pageCode.append("<a href='"+targetUrl+"?page="+(currentPage+1)+"&"+param+"' id='next-page'>��һҳ</a>");		
			}else{
				pageCode.append("<a class='allpage' id='next-page'>��һҳ</a>");	
			}
			pageCode.append("<a href='"+targetUrl+"?page="+totalPage+"&"+param+"' id='tailpage'>βҳ</a>");
			return pageCode.toString();
		}
	}
	

	
	
}
