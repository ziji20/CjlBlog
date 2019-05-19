package com.cjl.controller;

import java.util.Timer;
import java.util.TimerTask;

import javax.annotation.Resource;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.support.RequestContextUtils;

import com.cjl.entity.Blogger;
import com.cjl.entity.LMessage;
import com.cjl.service.LMessageService;
import com.cjl.util.ResponseUtil;
import com.cjl.util.SendEmail;

import net.sf.json.JSONObject;

/**
 * ����Controller��
 * 
 * @author Administrator
 *
 */
@Controller
@RequestMapping("/lmessage")
public class LMessageController {

	@Resource
	private LMessageService lMessageService;

	/**
	 * �������
	 * @param lmessage
	 * @param imageCode
	 * @param request
	 * @param response
	 * @param session
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/save")
	public String save(final LMessage lmessage, @RequestParam("imageCode") String imageCode, HttpServletRequest request,
			HttpServletResponse response, HttpSession session) throws Exception {
		final ServletContext application=RequestContextUtils.getWebApplicationContext(request).getServletContext();
		final Blogger blogger = (Blogger) application.getAttribute("blogger");
		String sRand=(String) session.getAttribute("sRand");
		JSONObject result=new JSONObject();
		int resultTotal=0;
		if(!imageCode.equals(sRand)){
			result.put("success", false);
			result.put("errorInfo", "��֤����д����!");
		}else{
			resultTotal = lMessageService.add(lmessage);
			if(resultTotal>0){
				result.put("success", true);
				Timer timer = new Timer();
			    timer.schedule(new TimerTask() {
			      public void run() {
			    	  SendEmail SendEmail = new SendEmail();
			    	  String text = lmessage.getName()+"һ����ǰ���������ˣ���ϵ��ʽ�ǣ�"+lmessage.getContactInformation()+",�����ǣ�"+lmessage.getContent()+"";
			    	  try {
							SendEmail.SendEmailFicationCode(blogger.getEmail(), text);
						} catch (Exception e) {
							e.printStackTrace();
						}
			      }
			    }, 70000);
				
			}else{
				result.put("success", false);
			}
		}
		ResponseUtil.write(response, result);
		return null;
	}
}
