package com.cjl.entity;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

/**
 * 博客实体
 * @author Administrator
 *
 */
public class Blog {

	private Integer id; // 编号
	private String title; // 博客标题
	private String summary; // 摘要
	private Date releaseDate; // 发布日期
	private String setDate;//设置时间
	private Integer clickHit; // 查看次数
	private Integer replyHit; // 回复次数
	private String content; // 博客内容
	private String	privateBlog;//设置私人博客 1为私人
	private String contentNoTag; // 博客内容，无网页标签 Lucene分词用到
	private int blogTypeId;
	
	private BlogType blogType; // 博客类型
	
	private String keyWord; // 关键字 空格隔开
	private Integer blogCount; // 博客数量 非博客实际属性 主要是 根据发布日期归档查询数量用到
	private String releaseDateStr; // 发布日期的字符串 只取年和月
	
	private List<String> imageList=new LinkedList<String>(); // 博客里存在的图片，主要用于列表展示的缩略图
	
	
	public String getPrivateBlog() {
		return privateBlog;
	}
	public void setPrivateBlog(String privateBlog) {
		this.privateBlog = privateBlog;
	}
	public Blog(){}
	public Blog(Integer id, String title, String summary, Date releaseDate,
			Integer clickHit, Integer replyHit, String content,String privateBlog,
			BlogType blogType, String keyWord) {
		super();
		this.id = id;
		this.title = title;
		this.summary = summary;
		this.releaseDate = releaseDate;
		this.clickHit = clickHit;
		this.replyHit = replyHit;
		this.content = content;
		this.privateBlog = privateBlog;
		this.blogType = blogType;
		this.keyWord = keyWord;
	}
	public String getSetDate() {
		return setDate;
	}
	public void setSetDate(String setDate) {
		this.setDate = setDate;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getSummary() {
		return summary;
	}
	public void setSummary(String summary) {
		this.summary = summary;
	}
	public Date getReleaseDate() {
		return releaseDate;
	}
	public void setReleaseDate(Date releaseDate) {
		this.releaseDate = releaseDate;
	}
	public Integer getClickHit() {
		return clickHit;
	}
	public void setClickHit(Integer clickHit) {
		this.clickHit = clickHit;
	}
	public Integer getReplyHit() {
		return replyHit;
	}
	public void setReplyHit(Integer replyHit) {
		this.replyHit = replyHit;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	
	public String getContentNoTag() {
		return contentNoTag;
	}
	public void setContentNoTag(String contentNoTag) {
		this.contentNoTag = contentNoTag;
	}
	public BlogType getBlogType() {
		return blogType;
	}
	public void setBlogType(BlogType blogType) {
		this.blogType = blogType;
	}
	public String getKeyWord() {
		return keyWord;
	}
	public void setKeyWord(String keyWord) {
		this.keyWord = keyWord;
	}
	public Integer getBlogCount() {
		return blogCount;
	}
	public void setBlogCount(Integer blogCount) {
		this.blogCount = blogCount;
	}
	public String getReleaseDateStr() {
		return releaseDateStr;
	}
	public void setReleaseDateStr(String releaseDateStr) {
		this.releaseDateStr = releaseDateStr;
	}
	public List<String> getImageList() {
		return imageList;
	}
	public void setImageList(List<String> imageList) {
		this.imageList = imageList;
	}
	
	
}
