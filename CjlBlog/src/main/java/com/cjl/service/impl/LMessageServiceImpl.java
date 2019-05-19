package com.cjl.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.cjl.dao.LMessageDao;
import com.cjl.entity.LMessage;
import com.cjl.service.LMessageService;

/**
 * ¡Ù—‘Service µœ÷¿‡
 * @author Administrator
 *
 */
@Service("lMessageService")
public class LMessageServiceImpl implements LMessageService{

	@Resource
	private LMessageDao lMessageDao;
	
	public List<LMessage> list(Map<String, Object> map) {
		return lMessageDao.list(map);
	}

	public int add(LMessage lMessage) {
		return lMessageDao.add(lMessage);
	}

	public Long getTotal(Map<String, Object> map) {
		return lMessageDao.getTotal(map);
	}

	public Integer delete(Integer id) {
		return lMessageDao.delete(id);
	}
	
	

}
