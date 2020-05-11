package com.cjl.controller;

import java.util.Timer;
import java.util.TimerTask;

import javax.annotation.Resource;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.support.RequestContextUtils;

import com.cjl.entity.Blogger;
import com.cjl.service.BloggerService;
import com.cjl.util.CryptographyUtil;
import com.cjl.util.ResponseUtil;
import com.cjl.util.SendEmail;

import net.sf.json.JSONObject;

/**
 * 博主Controller层
 * @author hasee
 *
 */
@Controller
@RequestMapping("/blogger")
public class BloggerController {

	@Resource
	private BloggerService bloggerService;
	
	/**
	 * 博主登录
	 * @param blogger
	 * @param request
	 * @return
	 */
	@RequestMapping("/login")
	public String login(Blogger blogger,HttpServletRequest request){
		Subject subject=SecurityUtils.getSubject();
		UsernamePasswordToken token=new UsernamePasswordToken(blogger.getUserName(), CryptographyUtil.md5(blogger.getPassword(), "ziji"));
		try{
			subject.login(token); // 登录验证
 			return "redirect:/admin/main.jsp";
		}catch(Exception e){
			request.setAttribute("blogger", blogger);
			request.setAttribute("errorInfo", "用户名或密码错误！");
			return "login";
		}
	}
	
	/**
	 * 博主信息
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/aboutMe")
	public ModelAndView aboutMe()throws Exception{
		ModelAndView mav=new ModelAndView();
		mav.addObject("pageTitle", "关于博主");
		mav.addObject("mainPage", "foreground/blogger/info.jsp");
		mav.setViewName("mainTemp");
		return mav;
	}
	
	/**
	 * 我的团队
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/myTeam")
	public ModelAndView myTeam()throws Exception{
		ModelAndView mav=new ModelAndView();
		mav.addObject("pageTitle", "我的团队");
		mav.addObject("mainPage", "foreground/system/myTeam.jsp");
		mav.setViewName("mainTemp");
		return mav;
	}
	
	/**
	 * 留言
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goLeavingMessage")
	public ModelAndView goLeavingMessage()throws Exception{
		ModelAndView mav=new ModelAndView();
		mav.addObject("pageTitle", "留言");
		mav.addObject("mainPage", "foreground/system/leavingMessage.jsp");
		mav.setViewName("mainTemp");
		return mav;
	}
	
	/**
	 * 发送验证码
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/SendVcod")
	public String  SendVcod(HttpServletRequest request,HttpServletResponse response)throws Exception{
		final ServletContext application=RequestContextUtils.getWebApplicationContext(request).getServletContext();
		SendEmail SendEmail = new SendEmail();
		//获取验证码
		String vcod = SendEmail.VerificationCode();
		//取得博主邮箱
		Blogger blogger = (Blogger) application.getAttribute("blogger");
		//发送邮件
		String text = "验证码为："+vcod+"，你正在修改登录密码！验证码将在五分钟后失效!";
		boolean is = SendEmail.SendEmailFicationCode(blogger.getEmail(), text); 
		JSONObject result = new JSONObject();
		if(is) {
			result.put("success", true);
			application.setAttribute("vcod", vcod);
			//设置定时器清除验证码,
			Timer timer = new Timer();
		    timer.schedule(new TimerTask() {
		      public void run() {
		    	  if(application.getAttribute("vcod") != null) {
		    		  application.removeAttribute("vcod");
		    	  }
		      }
		    }, 350000);
		}else {
			result.put("success", false);
		}
		ResponseUtil.write(response, result);
		return null;
	}
	
	/**
	 * 忘记密码
	 * @param newBlogger
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/forgetPassword")
	public String forgetPassword(Blogger newBlogger,HttpServletRequest request,HttpServletResponse response)throws Exception{
		ServletContext application=RequestContextUtils.getWebApplicationContext(request).getServletContext();
		String vocd = (String) application.getAttribute("vcod");
		if (vocd != null || newBlogger.getVcod().equals(vocd)) {
			Blogger blogger=new Blogger();
			blogger.setPassword(CryptographyUtil.md5(newBlogger.getNewPassowrd(),"ziji"));
			int resultTotal=bloggerService.update(blogger);
			if(resultTotal>0){
				application.removeAttribute("vcod");
				return "redirect:../login.jsp";
			}else {
				request.setAttribute("errorInfo", "密码修改失败！");
			}
		}else {
			request.setAttribute("errorInfo", "验证码错误或失效！");
		}
		request.setAttribute("blogger", newBlogger);
		return "changePassword";
	}
}
