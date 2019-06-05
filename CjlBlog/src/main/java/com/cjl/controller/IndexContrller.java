package com.cjl.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
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

import com.cjl.entity.Blog;
import com.cjl.entity.Blogger;
import com.cjl.entity.PageBean;
import com.cjl.service.BlogService;
import com.cjl.util.GetIdtoAddress;
import com.cjl.util.PageUtil;
import com.cjl.util.SendEmail;
import com.cjl.util.StringUtil;

/**
 * 主页Contrller
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
	 * 请求主页
	 * 
	 * @return
	 */
	@RequestMapping("/index")
	public ModelAndView index(@RequestParam(value = "page", required = false) String page,
			@RequestParam(value = "typeId", required = false) String typeId,
			@RequestParam(value = "releaseDateStr", required = false) String releaseDateStr, HttpServletRequest request)
			throws Exception {
		ModelAndView mav = new ModelAndView();
		if (StringUtil.isEmpty(page)) {
			page = "1";
		}
		PageBean pageBean = new PageBean(Integer.parseInt(page), 10);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("start", pageBean.getStart());
		map.put("size", pageBean.getPageSize());
		map.put("typeId", typeId);
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
		mav.addObject("pageTitle", "紫极20博客");
		mav.addObject("mainPage", "foreground/blog/list.jsp");
		mav.setViewName("mainTemp");
		
		final ServletContext application = RequestContextUtils.getWebApplicationContext(request).getServletContext();
	    String userIp = request.getRemoteAddr();
	    if ((application.getAttribute("vnid") == null) || (!application.getAttribute("vnid").equals(userIp)))
	    {
	      application.setAttribute("vnid", userIp);
	      Blogger blogger = (Blogger)application.getAttribute("blogger");
	      SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	      VisitNameId(blogger.getEmail(), userIp, df.format(new Date()));
	      Timer timer = new Timer();
	      timer.schedule(new TimerTask()
	      {
	        public void run()
	        {
	          if (application.getAttribute("vnid") != null) {
	            application.removeAttribute("vnid");
	          }
	        }
	      }, 600000);
	    }
	    
		return mav;
	}

	public void VisitNameId(final String email, final String id, final String time) throws Exception {
		Timer timer = new Timer();
		timer.schedule(new TimerTask() {
			public void run() {
				//发送邮箱类	
				SendEmail SendEmail = new SendEmail();
				//根据id获取地理
				GetIdtoAddress getAddress = new GetIdtoAddress();
				String text;
				try {
					text = id+"一分钟前在"+getAddress.getAddressByIp(id)+ "访问了你的主页,时间是：" + time;
					SendEmail.SendEmailFicationCode(email, text);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}, 60000);
	}
}
