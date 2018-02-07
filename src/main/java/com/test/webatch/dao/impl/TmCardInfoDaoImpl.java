package com.test.webatch.dao.impl;

import java.util.Map;

import com.test.webatch.dao.AbstractBasicDao;
import com.test.webatch.dao.Page;
import com.test.webatch.dao.TmCardInfoDao;

public class TmCardInfoDaoImpl<TmCardInfo> extends AbstractBasicDao<TmCardInfo>
		implements TmCardInfoDao<TmCardInfo> {

	@Override
	public Page<TmCardInfo> queryForPage(String className,
			Map<String, Object> params, Page<TmCardInfo> page) {
		return super.queryForPage("com.test.webatch.domain.TmCardInfo", params, page);
	}

	
}
