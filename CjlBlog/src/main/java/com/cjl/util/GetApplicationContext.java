package com.cjl.util;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.springframework.web.servlet.support.RequestContextUtils;

/**
 * 取applica中的值
 * @author hasee
 *
 */
public class GetApplicationContext {
	/**
	 * 返回泛型 
	 * @param value 想取的value值
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
