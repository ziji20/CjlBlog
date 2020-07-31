package com.cjl.util;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.springframework.web.servlet.support.RequestContextUtils;

/**
 * ȡapplica�е�ֵ
 * @author hasee
 *
 */
public class GetApplicationContext {
	/**
	 * ���ط��� 
	 * @param value ��ȡ��valueֵ
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static  <T> T getAPPlicationValue(String value,HttpServletRequest request) {
		ServletContext application=RequestContextUtils.getWebApplicationContext(request).getServletContext();
		T t = null;
		if ((application.getAttribute(value)!= null)) {
			t= (T) application.getAttribute(value);
		}
		 return t;
	 }

}
