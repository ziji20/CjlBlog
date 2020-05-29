package com.cjl.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import javax.annotation.Resource;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.support.RequestContextUtils;

import com.cjl.entity.AccessInformation;
import com.cjl.entity.Blog;
import com.cjl.entity.Blogger;
import com.cjl.entity.PageBean;
import com.cjl.service.BlogService;
import com.cjl.util.GetIdtoAddress;
import com.cjl.util.PageUtil;
import com.cjl.util.SendEmail;
import com.cjl.util.StringUtil;

/**
 * Ö÷Ò³Contrller
 * 
 * @author Administrator
 *
 */
@Controller
@RequestMapping("/")
public class IndexContrller {

	@Resource
	private BlogService blogService;

	/**
	 * ÇëÇóÖ÷Ò³
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/index")
	public ModelAndView index(@RequestParam(value = "page", required = false) String page,
			@RequestParam(value = "typeId", required = false) String typeId,
			@RequestParam(value = "typeId", required = false) Integer privateBlog,
			@RequestParam(value = "releaseDateStr", required = false) String releaseDateStr, HttpServletRequest request)
			throws Exception {
		ModelAndView mav = new ModelAndView();
		if (StringUtil.isEmpty(page)) {
			page = "1";
		}
		if (StringUtil.isEmpty(Integer.toString(privateBlog))) {
			privateBlog = 0;
		}
		PageBean pageBean = new PageBean(Integer.parseInt(page), 10);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("start", pageBean.getStart());
		map.put("size", pageBean.getPageSize());
		map.put("typeId", typeId);
		map.put("privateBlog", privateBlog);
		map.put("releaseDateStr", releaseDateStr);
		List<Blog> blogList = blogService.list(map);
		for (Blog blog : blogList) {
			List<String> imageList = blog.getImageList();
			String blogInfo = blog.getContent();
			Document doc = Jsoup.parse(blogInfo);
			Elements jpgs = doc.select("img[src~=(?i)\\.(png|jpe?g)]");
			for (int i = 0; i < jpgs.size(); i++) {
				Element jpg = jpgs.get(i);
				imageList.add(jpg.toString());
				if (i == 2) {
					break;
				}
			}
		}
		mav.addObject("blogList", blogList);
		StringBuffer param = new StringBuffer();
		if (StringUtil.isNotEmpty(typeId)) {
			param.append("typeId=" + typeId + "&");
		}
		if (StringUtil.isNotEmpty(releaseDateStr)) {
			param.append("releaseDateStr=" + releaseDateStr + "&");
		}
		mav.addObject("pageCode", PageUtil.genPagination(request.getContextPath() + "/index.html",
				blogService.getTotal(map), Integer.parseInt(page), 10, param.toString()));
		mav.addObject("pageTitle", "My growth");
		mav.addObject("mainPage", "foreground/blog/list.jsp");
		mav.setViewName("mainTemp");
		
		ServletContext application=RequestContextUtils.getWebApplicationContext(request).getServletContext();
		String userIp = getIpAddr(request);
		Map<String, AccessInformation> vnipMap=new LinkedHashMap<String, AccessInformation>();
		if ((application.getAttribute("vnipMap")!= null)) {
			vnipMap= (Map<String, AccessInformation>) application.getAttribute("vnipMap");
		}
		GetIdtoAddress getAddress = new GetIdtoAddress();
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		if (vnipMap.containsKey(userIp)) {
			AccessInformation accessInformation = vnipMap.get(userIp);
			accessInformation.setCount(accessInformation.getCount()+1);
			accessInformation.setTime(df.format(new Date()));
			if (accessInformation.getAddress().equals("true")) {
				accessInformation.setAddress(getAddress.getAddressByIp(userIp));
			}
			vnipMap.put(userIp, accessInformation);
		}else {
			String addres = null;
			try {
				addres = getAddress.getAddressByIp(userIp);
			} catch (Exception e) {
				addres = "´íÎó!";
			}
			AccessInformation accessInformation = new AccessInformation(userIp,df.format(new Date()),addres,1);
			vnipMap.put(userIp, accessInformation);
		}
		application.setAttribute("vnipMap", vnipMap);
		return mav;
	}
	public String getIpAddr(HttpServletRequest request)  {
        String ip  =  request.getHeader( " x-forwarded-for " );
         if (ip  ==   null   ||  ip.length()  ==   0   ||   " unknown " .equalsIgnoreCase(ip))  {
            ip  =  request.getHeader( " Proxy-Client-IP " );
        } 
         if (ip  ==   null   ||  ip.length()  ==   0   ||   " unknown " .equalsIgnoreCase(ip))  {
            ip  =  request.getHeader( " WL-Proxy-Client-IP " );
        } 
         if (ip  ==   null   ||  ip.length()  ==   0   ||   " unknown " .equalsIgnoreCase(ip))  {
           ip  =  request.getRemoteAddr();
       } 
        return  ip;
   }
}
