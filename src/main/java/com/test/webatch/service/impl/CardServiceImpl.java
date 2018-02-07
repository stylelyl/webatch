package com.test.webatch.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.test.webatch.dao.Page;
import com.test.webatch.dao.TmCardInfoDao;
import com.test.webatch.domain.TmCardInfo;
import com.test.webatch.service.CardService;



@Component("cardService")
public class CardServiceImpl implements CardService {
	
	@Autowired
	private TmCardInfoDao tmCardInfoDao;

	@Override
	public TmCardInfo queryByKey(TmCardInfo entity) {
		return (TmCardInfo)tmCardInfoDao.queryByKey(entity);
	}

	@Override
	public List<Integer> loadKeyStrList(TmCardInfo entity) {
		return tmCardInfoDao.loadKeysList(entity);
	}

	@Override
	public List<Integer> loadKeyStrList(String sqlId, Map<String, Object> params) {
		return tmCardInfoDao.loadKeysList(sqlId, params);
	}

	@Override
	public Page<TmCardInfo> queryForPageList(TmCardInfo entity,
			Map<String, Object> params, Page<TmCardInfo> page) {
		return tmCardInfoDao.queryForPageList(entity, params, page);
	}

	@Override
	public Page<TmCardInfo> queryForPage(
			Map<String, Object> params, Page<TmCardInfo> page) {
		return tmCardInfoDao.queryForPage(TmCardInfo.class.getCanonicalName(), params, page);
	}

}
