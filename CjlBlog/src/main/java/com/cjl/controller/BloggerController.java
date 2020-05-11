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
 * ����Controller��
 * @author hasee
 *
 */
@Controller
@RequestMapping("/blogger")
public class BloggerController {

	@Resource
	private BloggerService bloggerService;
	
	/**
	 * ������¼
	 * @param blogger
	 * @param request
	 * @return
	 */
	@RequestMapping("/login")
	public String login(Blogger blogger,HttpServletRequest request){
		Subject subject=SecurityUtils.getSubject();
		UsernamePasswordToken token=new UsernamePasswordToken(blogger.getUserName(), CryptographyUtil.md5(blogger.getPassword(), "ziji"));
		try{
			subject.login(token); // ��¼��֤
 			return "redirect:/admin/main.jsp";
		}catch(Exception e){
			request.setAttribute("blogger", blogger);
			request.setAttribute("errorInfo", "�û������������");
			return "login";
		}
	}
	
	/**
	 * ������Ϣ
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/aboutMe")
	public ModelAndView aboutMe()throws Exception{
		ModelAndView mav=new ModelAndView();
		mav.addObject("pageTitle", "���ڲ���");
		mav.addObject("mainPage", "foreground/blogger/info.jsp");
		mav.setViewName("mainTemp");
		return mav;
	}
	
	/**
	 * �ҵ��Ŷ�
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/myTeam")
	public ModelAndView myTeam()throws Exception{
		ModelAndView mav=new ModelAndView();
		mav.addObject("pageTitle", "�ҵ��Ŷ�");
		mav.addObject("mainPage", "foreground/system/myTeam.jsp");
		mav.setViewName("mainTemp");
		return mav;
	}
	
	/**
	 * ����
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goLeavingMessage")
	public ModelAndView goLeavingMessage()throws Exception{
		ModelAndView mav=new ModelAndView();
		mav.addObject("pageTitle", "����");
		mav.addObject("mainPage", "foreground/system/leavingMessage.jsp");
		mav.setViewName("mainTemp");
		return mav;
	}
	
	/**
	 * ������֤��
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/SendVcod")
	public String  SendVcod(HttpServletRequest request,HttpServletResponse response)throws Exception{
		final ServletContext application=RequestContextUtils.getWebApplicationContext(request).getServletContext();
		SendEmail SendEmail = new SendEmail();
		//��ȡ��֤��
		String vcod = SendEmail.VerificationCode();
		//ȡ�ò�������
		Blogger blogger = (Blogger) application.getAttribute("blogger");
		//�����ʼ�
		String text = "��֤��Ϊ��"+vcod+"���������޸ĵ�¼���룡��֤�뽫������Ӻ�ʧЧ!";
		boolean is = SendEmail.SendEmailFicationCode(blogger.getEmail(), text); 
		JSONObject result = new JSONObject();
		if(is) {
			result.put("success", true);
			application.setAttribute("vcod", vcod);
			//���ö�ʱ�������֤��,
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
	 * ��������
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
				request.setAttribute("errorInfo", "�����޸�ʧ�ܣ�");
			}
		}else {
			request.setAttribute("errorInfo", "��֤������ʧЧ��");
		}
		request.setAttribute("blogger", newBlogger);
		return "changePassword";
	}
}
