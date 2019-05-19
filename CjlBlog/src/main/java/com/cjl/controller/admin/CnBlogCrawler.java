package com.cjl.controller.admin;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.io.FileUtils;
import org.apache.http.HttpEntity;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;


import com.cjl.util.*;
import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Status;


/**
 * http://www.cnblogs.com/ ����
 */
public class CnBlogCrawler {
	
	private static Logger logger = Logger.getLogger(CnBlogCrawler.class);

	private static final String URL="http://www.cnblogs.com/"; // ������ַ
	
	private static CacheManager manager=null; // cache������
	private static Cache cache=null; // cache�������
	
	private static Connection con = null;
	
	/**
	 * ������ҳ ��ȡ����
	 */
	public static void parseHomePage(){
		while(true){
			manager=CacheManager.create(PropertiesUtil.getValue("cacheFilePath"));
			cache=manager.getCache("cnblog");
			logger.info("��ʼ��ȡ"+URL+"��ҳ");
			RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(100000) // ���ö�ȡ��ʱʱ��
					.setConnectTimeout(5000) // �������ӳ�ʱʱ��
					.build();
			CloseableHttpClient httpclient = HttpClients.createDefault(); // ����httpclientʵ��
			HttpGet httpget = new HttpGet(URL); // ����httpgetʵ��
			httpget.setConfig(requestConfig);
			CloseableHttpResponse response = null;
			try {
				response = httpclient.execute(httpget);
			} catch (ClientProtocolException e1) {
				logger.error(URL+"-ClientProtocolException", e1);
			} catch (IOException e1) {
				logger.error(URL+"-IOException", e1);
			}
			if (response != null) {
				HttpEntity entity = response.getEntity(); // ��ȡ����ʵ��
				// �жϷ���״̬�Ƿ�Ϊ200
				if (response.getStatusLine().getStatusCode() == 200) {
					String webPageContent = null;
					try {
						webPageContent = EntityUtils.toString(entity, "utf-8");
						parseHomeWebPage(webPageContent);
					} catch (java.net.SocketTimeoutException e) {
						logger.error(URL+"-��ȡ��ʱ", e);
					} catch (ParseException e) {
						logger.error(URL+"-ParseException", e);
					} catch (IOException e) {
						logger.error(URL+"-IOException", e);
					}
					try {
						if(response!=null){
							response.close();					
						}
					} catch (IOException e) {
						logger.error(URL+"-IOException", e);
					} // �ر������ͷ�ϵͳ��Դ
				}else{
					logger.error(URL+"-����״̬��200");
				}
			}else{
				logger.error(URL+"-���ӳ�ʱ");
			}
			try {
				if(response!=null){
					response.close();					
				}
				if(httpclient!=null){
					httpclient.close();					
				}
			} catch (IOException e) {
				logger.error(URL+"-IOException", e);
			}
			try {
				Thread.sleep(10*60*1000); // ÿ��10����ץȡһ����ҳ����
			} catch (InterruptedException e) {
				logger.error(URL+"-InterruptedException", e);
			}
			if(cache.getStatus()==Status.STATUS_ALIVE){
				cache.flush();				
			}
			manager.shutdown();
		}
	}
	
	/**
	 * ������ҳ���� ��ȡ����link
	 * @param webPageContent
	 */
	private static void parseHomeWebPage(String webPageContent){
		if (webPageContent.equals("")) {
			return;
		}
		Document doc = Jsoup.parse(webPageContent);
		Elements links=doc.select("#post_list .post_item .post_item_body h3 a");
		for(int i=0;i<links.size();i++){
			
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e2) {
				e2.printStackTrace();
			}
			
			Element link = links.get(i);
			String url = link.attr("href");
			if(cache.get(url)!=null){ // ��������д��ھ�ֹͣ
				logger.info(url+"-�������Ѿ�����");
				continue;
			}
			parseBlogLink(url);
		}
	}
	
	/**
	 * �����������ӵ�ַ ��ȡ����������ҳ ��ȡ��Ч��Ϣ
	 * @param link
	 */
	private static void parseBlogLink(String link){
		logger.info("��ʼ����"+link+"��ҳ");
		RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(10000) // ���ö�ȡ��ʱʱ��
				.setConnectTimeout(5000) // �������ӳ�ʱʱ��
				.build();
		CloseableHttpClient httpclient = HttpClients.createDefault(); // ����httpclientʵ��
		HttpGet httpget = new HttpGet(link); // ����httpgetʵ��
		httpget.setConfig(requestConfig);
		CloseableHttpResponse response = null;
		try {
			response = httpclient.execute(httpget);
		} catch (ClientProtocolException e1) {
			logger.error(URL+"-ClientProtocolException", e1);
		} catch (IOException e1) {
			logger.error(URL+"-IOException", e1);
		}
		if (response != null) {
			HttpEntity entity = response.getEntity(); // ��ȡ����ʵ��
			// �жϷ���״̬�Ƿ�Ϊ200
			if (response.getStatusLine().getStatusCode() == 200) {
				String blogContent = null;
				try {
					blogContent = EntityUtils.toString(entity, "utf-8");
					parseBlogPage(blogContent,link);
				} catch (java.net.SocketTimeoutException e) {
					logger.error(URL+"-��ȡ��ʱ", e);
				} catch (ParseException e) {
					logger.error(URL+"-ParseException", e);
				} catch (IOException e) {
					logger.error(URL+"-IOException", e);
				}
				try {
					if(response!=null){
						response.close();					
					}
				} catch (IOException e) {
					logger.error(URL+"-IOException", e);
				} // �ر������ͷ�ϵͳ��Դ
			}else{
				logger.error(URL+"-����״̬��200");
			}
		}else{
			logger.error(URL+"-���ӳ�ʱ");
		}
		try {
			httpclient.close();
		} catch (IOException e) {
			logger.error(URL+"-IOException", e);
		}
	}
	
	/**
	 * ������������ ��ȡ��Ч��Ϣ
	 * @param blogContent
	 * @param link
	 */
	private static void parseBlogPage(String blogContent,String link) {
		if (blogContent.equals("")) {
			return;
		}
		Document doc = Jsoup.parse(blogContent);
		Elements titleElements=doc.select("#cb_post_title_url"); // ��ȡ���ͱ���
		if(titleElements.size()==0){
			logger.error(link+"-δ��ȡ�����ͱ���");
			return;
		}
		String title=titleElements.get(0).text();
		System.out.println("�����ǣ�"+title);
		
		Elements contentElements=doc.select("#cnblogs_post_body"); // ��ȡ��������
		if(contentElements.size()==0){
			logger.error(link+"-δ��ȡ����������");
			return;
		}
		String content=contentElements.get(0).html();
		Document contentDoc = Jsoup.parse(content);
		Elements imgElements=contentDoc.select("img");
//		System.out.println("�������ݣ�"+content);
		
		List<String> imgUrlList=new LinkedList<String>();
		for(int i=0;i<imgElements.size();i++){
			Element imgE=imgElements.get(i);
			imgUrlList.add(imgE.attr("src"));
			System.out.println(imgE.attr("src")); 
		}
		if(imgUrlList.size()>0){
			Map<String,String> replaceImgMap=downLoadImags(imgUrlList); // ����ͼƬ
			String newContent=replaceWebPageImages(content,replaceImgMap); // �滻ͼƬ
			content=newContent;
		}
		
		// �������ݿ�
		String sql="insert into t_blog values(null,?,?,now(),0,0,?,14,null)";
		try {
			PreparedStatement pstmt = con.prepareStatement(sql);
			pstmt.setString(1, title);
			pstmt.setString(2, title);
			pstmt.setString(3, content);
			if (pstmt.executeUpdate() == 1) {
				logger.info(link+"-�ɹ��������ݿ⣡");
				// ���뻺��
				cache.put(new net.sf.ehcache.Element(link, link));
				logger.info(link+"-�Ѽ��뻺��");
			} else {
				logger.info(link+"-�������ݿ�ʧ�ܣ�");
			}
		} catch (SQLException e) {
			logger.error("SQLException", e);
		}
		
	}
	
	/**
	 * ��ԭ������ҳ��ͼƬ��ַ�滻���µ�
	 * @param content
	 * @param replaceImgMap
	 * @return
	 */
	public static String replaceWebPageImages(String content,Map<String,String> replaceImgMap){
		for(String url:replaceImgMap.keySet()){
			String newPath=replaceImgMap.get(url);
			content=content.replace(url, newPath);
		}
		return content;
	}
	
	/**
	 * ����ͼƬ������
	 * @param imgUrlList
	 */
	private static Map<String,String> downLoadImags(List<String> imgUrlList){
		Map<String,String> replaceImgMap=new HashMap<String,String>();
		
		RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(10000) // ���ö�ȡ��ʱʱ��
				.setConnectTimeout(5000) // �������ӳ�ʱʱ��
				.build();
		CloseableHttpClient httpclient = HttpClients.createDefault(); // ����httpclientʵ��
		for(int i=0;i<imgUrlList.size();i++){
			
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}
			
			String url=imgUrlList.get(i);
			logger.info("��ʼ��ȡ"+url+"��ҳͼƬ");
			CloseableHttpResponse response = null;
			try {
				HttpGet httpget = new HttpGet(url); // ����httpgetʵ��
				httpget.setConfig(requestConfig);
				response = httpclient.execute(httpget);
			} catch (ClientProtocolException e1) {
				logger.error(URL+"-ClientProtocolException", e1);
			} catch (IOException e1) {
				logger.error(URL+"-IOException", e1);
			}catch(IllegalArgumentException e1){
				logger.error(URL+"-IllegalArgumentException", e1);
			}
			if (response != null) {
				HttpEntity entity = response.getEntity(); // ��ȡ����ʵ��
				// �жϷ���״̬�Ƿ�Ϊ200
				if (response.getStatusLine().getStatusCode() == 200) {
					try {
						InputStream inputStream=entity.getContent();
						String imageType=entity.getContentType().getValue();
						String urlB=imageType.split("/")[1];
						String currentDatePath=DateUtil.getCurrentDatePath(); // ����������·��
						String uuid=UUID.randomUUID().toString(); // uuid
						String newPath="http://blog.open.com/static/blogImages/"+currentDatePath+"/"+uuid+"."+urlB;
						FileUtils.copyToFile(inputStream, new File(PropertiesUtil.getValue("imageFilePath")+currentDatePath+"/"+uuid+"."+urlB));
						replaceImgMap.put(url, newPath);
					} catch (UnsupportedOperationException e) {
						logger.error(URL+"-UnsupportedOperationException", e);
					} catch (IOException e) {
						logger.error(URL+"-IOException", e);
					} catch (Exception e) {
						logger.error(URL+"-Exception", e);
					}
				}else{
					logger.error(URL+"-����״̬��200");
				}
			}else{
				logger.error(URL+"-���ӳ�ʱ");
			}
			try {
				if(response!=null){
					response.close();					
				}
			} catch (IOException e) {
				logger.error(URL+"-IOException", e);
			}
		}
		logger.info("ͼƬץȡ����");
		try {
			httpclient.close();
		} catch (IOException e) {
			logger.error(URL+"-IOException", e);
		}
		return replaceImgMap;
	}
	
	
	public static void start(){
		DbUtil dbUtil = new DbUtil();
		try {
			con = dbUtil.getCon();
		} catch (Exception e) {
			logger.error("�������ݿ�����ʧ��", e);
		}
		parseHomePage();
	}
	
	
	public static void main(String[] args) {
		start();
	}
}
