package com.test.webatch.service;

import java.util.List;
import java.util.Map;

import com.test.webatch.dao.Page;
import com.test.webatch.domain.TmCardInfo;



public interface CardService {
	
	public TmCardInfo queryByKey(TmCardInfo entity);

	public List<Integer> loadKeyStrList(TmCardInfo entity);

	public List<Integer> loadKeyStrList(String sqlId, Map<String, Object> params);

	public Page<TmCardInfo> queryForPageList(TmCardInfo entity, Map<String,Object> params, Page<TmCardInfo> page);

	public Page<TmCardInfo> queryForPage(Map<String, Object> params,Page<TmCardInfo> page);

}
