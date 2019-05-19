package com.cjl.util;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 获取一个String中的图片路径
 * 
 * @author caojl1521
 *
 */
public class ObtainPictureName {

	public static List<String> ImgName(String str) {
		List<String> list = getImg(str);
		List<String> Imglist = new ArrayList<String>();
		for (String string : list) {
			int begin = string.indexOf("\"") + 1;
			int end = string.lastIndexOf("\"");
			System.out.println(string.substring(begin, end));
			Imglist.add(string.substring(begin, end));
		}
		return Imglist;
	}

	/**
	 * 
	 * @param s
	 * @return 获得图片
	 */
	public static List<String> getImg(String s) {
		String regex;
		List<String> list = new ArrayList<String>();
		regex = "src=\"(.*?)\"";
		Pattern pa = Pattern.compile(regex, Pattern.DOTALL);
		Matcher ma = pa.matcher(s);
		while (ma.find()) {
			list.add(ma.group());
		}
		return list;
	}
	
}
