package com.cjl.controller.admin;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.support.RequestContextUtils;

import com.cjl.entity.AccessInformation;
import com.cjl.entity.Blog;
import com.cjl.entity.BlogType;
import com.cjl.entity.Blogger;
import com.cjl.entity.FilePojo;
import com.cjl.entity.Link;
import com.cjl.lucene.BlogIndex;
import com.cjl.service.BlogService;
import com.cjl.service.BlogTypeService;
import com.cjl.service.BloggerService;
import com.cjl.service.LinkService;
import com.cjl.util.ObtainPictureName;
import com.cjl.util.ResponseUtil;
import com.cjl.util.SendEmail;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * 管理员系统Controller层
 * @author Administrator
 *
 */
@Controller
@RequestMapping("/admin/system")
public class SystemAdminController {

	@Resource
	private BloggerService bloggerService;
	
	@Resource
	private LinkService linkService;
	
	@Resource
	private BlogTypeService blogTypeService;
	
	@Resource
	private BlogService blogService;
	
	private BlogIndex blogIndex = new BlogIndex();

	
	/**
	 * 刷新系统缓存
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/refreshSystem")
	public String refreshSystem(HttpServletRequest request,HttpServletResponse response)throws Exception{
		ServletContext application=RequestContextUtils.getWebApplicationContext(request).getServletContext();
		
		Blogger blogger=bloggerService.find(); // 获取博主信息
		blogger.setPassword(null);
		application.setAttribute("blogger", blogger);
		
		List<Link> linkList=linkService.list(null); // 查询所有的友情链接信息
		application.setAttribute("linkList", linkList);
		
		List<BlogType> blogTypeCountList=blogTypeService.countList(); // 查询博客类别以及博客的数量
		application.setAttribute("blogTypeCountList", blogTypeCountList);
		
		List<Blog> blogCountList=blogService.countList(); // 根据日期分组查询博客
		application.setAttribute("blogCountList", blogCountList);
		
		JSONObject result=new JSONObject();
		result.put("success", true);
		ResponseUtil.write(response, result);
		return null;
	}
	
	/**
	 * 刷新全文检索
	 * 
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/sxqwjs")
	public String sxqwjs(HttpServletResponse response) throws Exception {
		List<Blog> blogList = blogService.list(null);
		blogIndex.deleteAll();
		for (Blog blog : blogList) {
			blog.setBlogCount(null);
			blog.setClickHit(null);
			blog.setReplyHit(null);
			blog.setContentNoTag(blog.getContent());
			blog.setReleaseDate(null);
			blog.setReleaseDateStr(null);
			blogIndex.addIndex(blog);
		}
		JSONObject result = new JSONObject();
		result.put("success", true);
		ResponseUtil.write(response, result);
		return null;
	}

	/**
	 * 删除博客时清理图片
	 * 刷新内存
	 * @param response
	 * @return
	 */
	List<FilePojo> filelist = new ArrayList<FilePojo>();//所有图片的名字和路径
	List<FilePojo> contentlist = new ArrayList<FilePojo>();//博客中的图片名字
	@RequestMapping("/clearPicture")
	public String clearPicture(HttpServletRequest request, HttpServletResponse response, HttpSession session)
			throws IOException {
		// 获取项目的图片存储路径
		String url = session.getServletContext().getRealPath("/") + "static/userImages/";
		//获取所有图片的名字和路径
		getAllFilePaths(new File(url));
		//获取博客中的内容
	    getContentFiles();        
	    //循环判断图片在博客中是否存在
        for(FilePojo file : contentlist){
            search(file.getName());
        }
        //循环删除图片
        for(FilePojo file : filelist){
            if(!file.isFind()) {                
                new File(file.getPath()).delete();
            }
        }
		return null;
	}
	/**
	 * 获取图片的名字和路径
	 * @param filePath
	 */
	private void getAllFilePaths(File filePath) {
		File[] files = filePath.listFiles();

		for (File file : files) {
			if (file.isDirectory()) {
				getAllFilePaths(file);
			} else {
				String filename = file.getName();
				if (filename.contains(".jpg") || filename.contains(".png") || filename.contains(".jpeg")
						|| filename.contains(".bmp") || filename.contains(".gif") || filename.contains(".js")
						|| filename.contains(".css")) {
					FilePojo pojo = new FilePojo();
					pojo.setName(file.getName());
					pojo.setPath(file.getPath());
					filelist.add(pojo);
				}
			}
		}
	}
	/**
	 * 获取博客中的图片路径
	 */
	private void getContentFiles() {
		List<Blog> blogList = blogService.list(null);
		for (Blog blog : blogList) {
			List<String> imgList = ObtainPictureName.ImgName(blog.getContent());
			for (String ImgName : imgList) {
				FilePojo pojo = new FilePojo();
				pojo.setName(ImgName);
				contentlist.add(pojo);
			}
		}
		//在把博主的图片添加进去
		Blogger blogger = bloggerService.find();
		blogger.setPassword(null);
		FilePojo pojo2 = new FilePojo();
		pojo2.setName(blogger.getImageName());
		contentlist.add(pojo2);
	}
	/**
	 * 循环判断图片是否存在博客里
	 * @param filename
	 * @throws IOException
	 */
	public void search(String filename) throws IOException {
		for (FilePojo file : filelist) {
			if (filename.contains(file.getName())) {
				file.setFind(true);
			}
		}
	}
	
	
	/**
	 * 查询访问者信息
	 * @param page
	 * @param rows
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/getAccessinFormationList")
	public String getAccessinFormationList(@RequestParam(value = "page", required = false) String page,
			@RequestParam(value = "rows", required = false) String rows,HttpServletRequest request, HttpServletResponse response) throws Exception {
		ServletContext application = RequestContextUtils.getWebApplicationContext(request).getServletContext();
		if (application.getAttribute("vnipMap")!=null) {
			Map<String, AccessInformation> vnipMap= (LinkedHashMap<String, AccessInformation>) application.getAttribute("vnipMap");
			Collection<AccessInformation> valueCollection = null;
			List<AccessInformation> vnidList = null;
			Long total = null;
			if (vnipMap !=  null) {
				valueCollection = vnipMap.values();
				vnidList= new ArrayList<AccessInformation>(valueCollection);//map转list
				total=(long) vnidList.size();
			}
			JSONObject result=new JSONObject();
			JSONArray jsonArray=JSONArray.fromObject(vnidList);
			result.put("rows", jsonArray);
			result.put("total", total);
			ResponseUtil.write(response, result);
		}
		return null;
	}
	
	/**
	 * 删除访问者信息
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/deleteAccessInformation")
	public String deleteAccessInformation(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ServletContext application = RequestContextUtils.getWebApplicationContext(request).getServletContext();
		application.removeAttribute("vnipMap");
		Map<String, AccessInformation> vnipMap = new LinkedHashMap<String, AccessInformation>();
		application.setAttribute("vnipMap", vnipMap);
		JSONObject result = new JSONObject();
		result.put("success", true);
		ResponseUtil.write(response, result);
		return null;
	}
	
	/**
	 * 发送访问量到邮箱
	 * @param request
	 * @param response
	 * @param session
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping("/getVNItoEmail")
	public String getVNItoEmail(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ServletContext application = RequestContextUtils.getWebApplicationContext(request).getServletContext();
		Map<String, AccessInformation> vnipMap= (Map<String, AccessInformation>) application.getAttribute("vnipMap");
		JSONObject result = new JSONObject();
		if(vnipMap.size() == 0) {
			result.put("success", false);
			result.put("error", "没有游客访问!");
			ResponseUtil.write(response, result);
			return null;
		}
		StringBuffer text = new StringBuffer();
		for(String key : vnipMap.keySet()) {
			AccessInformation ai = vnipMap.get(key);
			text.append("访问 id:"+key+";访问次数:"+ai.getCount()+";访问时间:"+ai.getTime()+";访问地址:"+ai.getAddress()+"\n");
		}
		Blogger blogger = (Blogger) application.getAttribute("blogger");
		SendEmail SendEmail = new SendEmail();
			System.out.println(text.toString());
		try {
			SendEmail.SendEmailFicationCode(blogger.getEmail(), text.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		result.put("success", true);
		ResponseUtil.write(response, result);
		return null;
	}
}
