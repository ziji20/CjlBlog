package com.cjl.controller;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.cjl.entity.Blog;
import com.cjl.lucene.BlogIndex;
import com.cjl.service.BlogService;
import com.cjl.service.CommentService;
import com.cjl.util.GetApplicationContext;
import com.cjl.util.StringUtil;

@Controller
@RequestMapping("/blog")
public class BlogController {


	@Resource
	private BlogService blogService;

	@Resource
	private CommentService commentService;	
	
	private BlogIndex blogIndex=new BlogIndex();
	/**
	 * 请求博客详细信息
	 * @param id
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/articles/{id}")
	public ModelAndView details(@PathVariable("id") Integer id,HttpServletRequest request)throws Exception{
		ModelAndView mav=new ModelAndView();
		Blog blog=blogService.findById(id);
		//获取applicat中是否设置查看私人博客
		String privateBlog = GetApplicationContext.getAPPlicationValue("privateBlog",request);
		if (privateBlog== null) {
			privateBlog = "0";
		}
		//没有查询博客
		if (blog==null) {
			mav = new ModelAndView("redirect:/index.html");
		}else if (privateBlog.equals("1") || blog.getPrivateBlog().equals(privateBlog) ) {
			//是否有权限查看博客
			String keyWords=blog.getKeyWord();
			if(StringUtil.isNotEmpty(keyWords)){
				String arr[]=keyWords.split(" ");
				mav.addObject("keyWords", StringUtil.filterWhite(Arrays.asList(arr)));
			}else{
				mav.addObject("keyWords",null);
			}
			mav.addObject("blog",blog);
			blog.setClickHit(blog.getClickHit()+1);
			blogService.update(blog);
			Map<String,Object> map=new HashMap<String,Object>();
			map.put("blogId", blog.getId());
			map.put("state", 1);
			mav.addObject("commentList", commentService.list(map));
			mav.addObject("pageCode", this.getUpAndDownPageCode(blogService.getLastBlog(id), blogService.getNextBlog(id), request.getServletContext().getContextPath()));
			mav.addObject("pageTitle", blog.getTitle()+"紫极20");
			mav.addObject("mainPage", "foreground/blog/view.jsp");
			mav.setViewName("mainTemp");
		}else {
			mav = new ModelAndView("redirect:/index.html");
		}
		return mav;
	}
	
	/**
	 * 获取上一篇博客和下一篇博客
	 * @param lastBlog
	 * @param nextBlog
	 * @param projectContext
	 * @return
	 */
	private String getUpAndDownPageCode(Blog lastBlog,Blog nextBlog,String projectContext){
		StringBuffer pageCode=new StringBuffer();
		if(lastBlog==null || lastBlog.getId()==null){
			pageCode.append("<p>上一篇：没有了</p>");
		}else{
			pageCode.append("<p id='prev-page'>上一篇：<a href='"+projectContext+"/blog/articles/"+lastBlog.getId()+".html'>"+lastBlog.getTitle()+"</a></p>");
		}
			
		if(nextBlog==null || nextBlog.getId()==null){
			pageCode.append("<p>下一篇：没有了</p>");
		}else{
			pageCode.append("<p id='next-page'>下一篇：<a href='"+projectContext+"/blog/articles/"+nextBlog.getId()+".html'>"+nextBlog.getTitle()+"</a></p>");
		}
		return pageCode.toString();
	}
	
	/**
	 *  根据关键字查询相关博客信息
	 * @param q
	 * @param page
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/q")
	public ModelAndView search(@RequestParam(value="q",required=false) String q,@RequestParam(value="page",required=false) String page,HttpServletRequest request)throws Exception{
		String privateBlog = GetApplicationContext.getAPPlicationValue("privateBlog",request);
		if (privateBlog== null) {
			privateBlog = "0";
		}
		int pageSize=5;
		if(StringUtil.isEmpty(page)){
			page="1";
		}
		ModelAndView mav=new ModelAndView();
		mav.addObject("pageTitle", "搜索关键字'"+q+"'结果页面_紫极博客系统");
		mav.addObject("mainPage", "foreground/blog/result.jsp");
		List<Blog> blogList=blogIndex.searchBlog(q,privateBlog);
		Integer toIndex=blogList.size()>=Integer.parseInt(page)*pageSize?Integer.parseInt(page)*pageSize:blogList.size();
		mav.addObject("blogList", blogList.subList((Integer.parseInt(page)-1)*pageSize, toIndex));
		mav.addObject("pageCode",this.genUpAndDownPageCode(Integer.parseInt(page), blogList.size(), q, pageSize, request.getServletContext().getContextPath()));
		mav.addObject("q", q);
		mav.addObject("resultTotal", blogList.size());
		mav.setViewName("mainTemp");
		return mav;
	}
	
	/**
	 * 获取上一页，下一页代码 
	 * @param page
	 * @param totalNum
	 * @param q
	 * @param pageSize
	 * @param projectContext
	 * @return
	 */
	private String genUpAndDownPageCode(Integer page,Integer totalNum,String q,Integer pageSize,String projectContext){
		long totalPage=totalNum%pageSize==0?totalNum/pageSize:totalNum/pageSize+1;
		StringBuffer pageCode=new StringBuffer();
		if(totalPage==0){
			return "";
		}else{
			pageCode.append("<div class='pagelist'>");
			if(page>1){
				pageCode.append("<a href='"+projectContext+"/blog/q.html?page="+(page-1)+"&q="+q+"' id='prev-page'>上一页</a>");
			}else{
				pageCode.append("<a href='#' id='prev-page' class='allpage'>上一页</a>");
			}
			if(page<totalPage){
				pageCode.append("<a href='"+projectContext+"/blog/q.html?page="+(page+1)+"&q="+q+"' id='next-page'>下一页</a>");
			}else{
				pageCode.append("<a href='#' class='allpage' id='next-page'>下一页</a>");
			}
			pageCode.append("</div>");
		}
		return pageCode.toString();
	}
	
}
