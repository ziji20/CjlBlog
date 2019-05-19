package com.cjl.dao;

import java.util.List;
import java.util.Map;

import com.cjl.entity.LMessage;

/**
 * 留言Dao接口
 * @author Administrator
 *
 */
public interface LMessageDao {

	/**
	 * 查询用户留言信息
	 * @param map
	 * @return
	 */
	public List<LMessage> list(Map<String,Object> map);
	
	/**
	 * 添加留言
	 * @param lMessage
	 * @return
	 */
	public int add(LMessage lMessage);
	
	/**
	 * 获取总记录数
	 * @param map
	 * @return
	 */
	public Long getTotal(Map<String,Object> map);
	
	/**
	 * 删除留言
	 * @param id
	 * @return
	 */
	public Integer delete(Integer id);
	
}
