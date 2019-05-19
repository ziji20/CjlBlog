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
 * http://www.cnblogs.com/ 爬虫
 */
public class CnBlogCrawler {
	
	private static Logger logger = Logger.getLogger(CnBlogCrawler.class);

	private static final String URL="http://www.cnblogs.com/"; // 解析地址
	
	private static CacheManager manager=null; // cache管理器
	private static Cache cache=null; // cache缓存对象
	
	private static Connection con = null;
	
	/**
	 * 解析首页 获取内容
	 */
	public static void parseHomePage(){
		while(true){
			manager=CacheManager.create(PropertiesUtil.getValue("cacheFilePath"));
			cache=manager.getCache("cnblog");
			logger.info("开始爬取"+URL+"网页");
			RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(100000) // 设置读取超时时间
					.setConnectTimeout(5000) // 设置连接超时时间
					.build();
			CloseableHttpClient httpclient = HttpClients.createDefault(); // 创建httpclient实例
			HttpGet httpget = new HttpGet(URL); // 创建httpget实例
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
				HttpEntity entity = response.getEntity(); // 获取返回实体
				// 判断返回状态是否为200
				if (response.getStatusLine().getStatusCode() == 200) {
					String webPageContent = null;
					try {
						webPageContent = EntityUtils.toString(entity, "utf-8");
						parseHomeWebPage(webPageContent);
					} catch (java.net.SocketTimeoutException e) {
						logger.error(URL+"-读取超时", e);
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
					} // 关闭流和释放系统资源
				}else{
					logger.error(URL+"-返回状态非200");
				}
			}else{
				logger.error(URL+"-连接超时");
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
				Thread.sleep(10*60*1000); // 每隔10分钟抓取一次网页数据
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
	 * 解析首页内容 提取博客link
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
			if(cache.get(url)!=null){ // 如果缓存中存在就停止
				logger.info(url+"-缓存中已经存在");
				continue;
			}
			parseBlogLink(url);
		}
	}
	
	/**
	 * 解析博客链接地址 获取博客内容网页 提取有效信息
	 * @param link
	 */
	private static void parseBlogLink(String link){
		logger.info("开始解析"+link+"网页");
		RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(10000) // 设置读取超时时间
				.setConnectTimeout(5000) // 设置连接超时时间
				.build();
		CloseableHttpClient httpclient = HttpClients.createDefault(); // 创建httpclient实例
		HttpGet httpget = new HttpGet(link); // 创建httpget实例
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
			HttpEntity entity = response.getEntity(); // 获取返回实体
			// 判断返回状态是否为200
			if (response.getStatusLine().getStatusCode() == 200) {
				String blogContent = null;
				try {
					blogContent = EntityUtils.toString(entity, "utf-8");
					parseBlogPage(blogContent,link);
				} catch (java.net.SocketTimeoutException e) {
					logger.error(URL+"-读取超时", e);
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
				} // 关闭流和释放系统资源
			}else{
				logger.error(URL+"-返回状态非200");
			}
		}else{
			logger.error(URL+"-连接超时");
		}
		try {
			httpclient.close();
		} catch (IOException e) {
			logger.error(URL+"-IOException", e);
		}
	}
	
	/**
	 * 解析博客内容 提取有效信息
	 * @param blogContent
	 * @param link
	 */
	private static void parseBlogPage(String blogContent,String link) {
		if (blogContent.equals("")) {
			return;
		}
		Document doc = Jsoup.parse(blogContent);
		Elements titleElements=doc.select("#cb_post_title_url"); // 获取博客标题
		if(titleElements.size()==0){
			logger.error(link+"-未获取到博客标题");
			return;
		}
		String title=titleElements.get(0).text();
		System.out.println("标题是："+title);
		
		Elements contentElements=doc.select("#cnblogs_post_body"); // 获取博客内容
		if(contentElements.size()==0){
			logger.error(link+"-未获取到博客内容");
			return;
		}
		String content=contentElements.get(0).html();
		Document contentDoc = Jsoup.parse(content);
		Elements imgElements=contentDoc.select("img");
//		System.out.println("博客内容："+content);
		
		List<String> imgUrlList=new LinkedList<String>();
		for(int i=0;i<imgElements.size();i++){
			Element imgE=imgElements.get(i);
			imgUrlList.add(imgE.attr("src"));
			System.out.println(imgE.attr("src")); 
		}
		if(imgUrlList.size()>0){
			Map<String,String> replaceImgMap=downLoadImags(imgUrlList); // 下载图片
			String newContent=replaceWebPageImages(content,replaceImgMap); // 替换图片
			content=newContent;
		}
		
		// 插入数据库
		String sql="insert into t_blog values(null,?,?,now(),0,0,?,14,null)";
		try {
			PreparedStatement pstmt = con.prepareStatement(sql);
			pstmt.setString(1, title);
			pstmt.setString(2, title);
			pstmt.setString(3, content);
			if (pstmt.executeUpdate() == 1) {
				logger.info(link+"-成功插入数据库！");
				// 插入缓存
				cache.put(new net.sf.ehcache.Element(link, link));
				logger.info(link+"-已加入缓存");
			} else {
				logger.info(link+"-插入数据库失败！");
			}
		} catch (SQLException e) {
			logger.error("SQLException", e);
		}
		
	}
	
	/**
	 * 把原来的网页的图片地址替换成新的
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
	 * 下载图片到本地
	 * @param imgUrlList
	 */
	private static Map<String,String> downLoadImags(List<String> imgUrlList){
		Map<String,String> replaceImgMap=new HashMap<String,String>();
		
		RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(10000) // 设置读取超时时间
				.setConnectTimeout(5000) // 设置连接超时时间
				.build();
		CloseableHttpClient httpclient = HttpClients.createDefault(); // 创建httpclient实例
		for(int i=0;i<imgUrlList.size();i++){
			
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}
			
			String url=imgUrlList.get(i);
			logger.info("开始爬取"+url+"网页图片");
			CloseableHttpResponse response = null;
			try {
				HttpGet httpget = new HttpGet(url); // 创建httpget实例
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
				HttpEntity entity = response.getEntity(); // 获取返回实体
				// 判断返回状态是否为200
				if (response.getStatusLine().getStatusCode() == 200) {
					try {
						InputStream inputStream=entity.getContent();
						String imageType=entity.getContentType().getValue();
						String urlB=imageType.split("/")[1];
						String currentDatePath=DateUtil.getCurrentDatePath(); // 当年年月日路径
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
					logger.error(URL+"-返回状态非200");
				}
			}else{
				logger.error(URL+"-连接超时");
			}
			try {
				if(response!=null){
					response.close();					
				}
			} catch (IOException e) {
				logger.error(URL+"-IOException", e);
			}
		}
		logger.info("图片抓取结束");
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
			logger.error("创建数据库连接失败", e);
		}
		parseHomePage();
	}
	
	
	public static void main(String[] args) {
		start();
	}
}
