package com.cjl.dao;

import java.util.List;
import java.util.Map;

import com.cjl.entity.LMessage;

/**
 * ����Dao�ӿ�
 * @author Administrator
 *
 */
public interface LMessageDao {

	/**
	 * ��ѯ�û�������Ϣ
	 * @param map
	 * @return
	 */
	public List<LMessage> list(Map<String,Object> map);
	
	/**
	 * �������
	 * @param lMessage
	 * @return
	 */
	public int add(LMessage lMessage);
	
	/**
	 * ��ȡ�ܼ�¼��
	 * @param map
	 * @return
	 */
	public Long getTotal(Map<String,Object> map);
	
	/**
	 * ɾ������
	 * @param id
	 * @return
	 */
	public Integer delete(Integer id);
	
}
