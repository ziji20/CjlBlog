package com.cjl.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.cjl.entity.Blog;
import com.cjl.entity.Comment;
import com.cjl.service.BlogService;
import com.cjl.service.CommentService;
import com.cjl.util.ResponseUtil;

import net.sf.json.JSONObject;

/**
 * 评论Controller层
 * 
 * @author Administrator
 *
 */
@Controller
@RequestMapping("/comment")
public class CommentController {

	@Resource
	private CommentService commentService;

	@Resource
	private BlogService blogService;

	/**
	 * 添加或者修改评论
	 * @param comment
	 * @param imageCode
	 * @param request
	 * @param response
	 * @param session
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/save")
	public String save(Comment comment, @RequestParam("imageCode") String imageCode, HttpServletRequest request,
			HttpServletResponse response, HttpSession session) throws Exception {
		String sRand=(String) session.getAttribute("sRand");
		JSONObject result=new JSONObject();
		int resultTotal=0;
		if(!imageCode.equals(sRand)){
			result.put("success", false);
			result.put("errorInfo", "验证码填写错误!");
		}else{
			String userIp=request.getRemoteAddr(); // 获取用户IP
			comment.setUserIp(userIp);
			if(comment.getId()==null){
				resultTotal=commentService.add(comment);
			}
		}
		if(resultTotal>0){
			result.put("success", true);
		}else{
			result.put("success", false);
		}
		ResponseUtil.write(response, result);
		return null;
	}
}
