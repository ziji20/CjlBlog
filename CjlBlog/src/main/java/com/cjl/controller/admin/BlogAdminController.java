package com.cjl.controller.admin;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.cjl.entity.Blog;
import com.cjl.entity.Blogger;
import com.cjl.entity.PageBean;
import com.cjl.lucene.BlogIndex;
import com.cjl.service.BlogService;
import com.cjl.service.BloggerService;
import com.cjl.util.CryptographyUtil;
import com.cjl.util.ResponseUtil;
import com.cjl.util.StringUtil;
import com.cjl.util.ObtainPictureName;

import com.cjl.entity.FilePojo;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

/**
 * 管理员博客Controller层
 * 
 * @author Administrator
 *
 */
@Controller
@RequestMapping("/admin/blog")
public class BlogAdminController {

	@Resource
	private BlogService blogService;
	@Resource
	private BloggerService bloggerService;

	private BlogIndex blogIndex = new BlogIndex();

	/**
	 * 添加或者修改博客信息
	 * 
	 * @param blog
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/save")
	public String save(Blog blog, HttpServletResponse response) throws Exception {
		if (blog.getSummary() == null) {
			blog.setSummary(blog.getTitle());
		}
		int resultTotal = 0;
		if (blog.getId() == null) {
			resultTotal = blogService.add(blog);
			if (blog.getSummary()==null) {
				blog.setSummary(blog.getTitle());
			}
			blogIndex.addIndex(blog);
		} else {
			resultTotal = blogService.update(blog);
			blogIndex.updateIndex(blog);
		}
		JSONObject result = new JSONObject();
		if (resultTotal > 0) {
			result.put("success", true);
		} else {
			result.put("success", false);
		}

		ResponseUtil.write(response, result);
		return null;
	}

	public String save(Blog blog) throws Exception {
		int resultTotal = 0;
		resultTotal = blogService.add(blog);
		blogIndex.addIndex(blog);
		JSONObject result = new JSONObject();
		if (resultTotal > 0) {
			result.put("success", true);
		} else {
			result.put("success", false);
		}
		return null;
	}

	/**
	 * 分页查询博客信息
	 * 
	 * @param page
	 * @param rows
	 * @param s_blog
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/list")
	public String list(@RequestParam(value = "page", required = false) String page,
			@RequestParam(value = "rows", required = false) String rows, Blog s_blog, HttpServletResponse response)
			throws Exception {
		PageBean pageBean = new PageBean(Integer.parseInt(page), Integer.parseInt(rows));
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("title", StringUtil.formatLike(s_blog.getTitle()));
		map.put("start", pageBean.getStart());
		map.put("size", pageBean.getPageSize());
		List<Blog> blogList = blogService.list(map);
		Long total = blogService.getTotal(map);
		JSONObject result = new JSONObject();
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.registerJsonValueProcessor(java.util.Date.class, new DateJsonValueProcessor("yyyy-MM-dd"));
		JSONArray jsonArray = JSONArray.fromObject(blogList, jsonConfig);
		result.put("rows", jsonArray);
		result.put("total", total);
		ResponseUtil.write(response, result);
		return null;
	}

	/**
	 * 博客信息删除
	 * 
	 * @param ids
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/delete")
	public String delete(@RequestParam(value = "ids", required = false) String ids, HttpServletResponse response)
			throws Exception {
		String[] idsStr = ids.split(",");
		for (int i = 0; i < idsStr.length; i++) {
			blogService.delete(Integer.parseInt(idsStr[i]));
			blogIndex.deleteIndex(idsStr[i]);
		}
		JSONObject result = new JSONObject();
		result.put("success", true);
		ResponseUtil.write(response, result);
		return null;
	}

	/**
	 * 通过Id查找实体
	 * 
	 * @param id
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/findById")
	public String findById(@RequestParam(value = "id") String id, HttpServletResponse response) throws Exception {
		Blog blog = blogService.findById(Integer.parseInt(id));
		JSONObject result = JSONObject.fromObject(blog);
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
	 * 
	 * @param response
	 * @return
	 */
	List<FilePojo> filelist = new ArrayList<FilePojo>();//所有图片的名字和路径
	List<FilePojo> contentlist = new ArrayList<FilePojo>();//博客中的图片名字
	@RequestMapping("clearPicture")
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
		//在吧博主的图片添加进去
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
}
