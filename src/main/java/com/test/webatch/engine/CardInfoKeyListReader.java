package com.test.webatch.engine;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.test.webatch.dao.TmCardInfoDao;
import com.test.webatch.domain.TmCardInfo;

public class CardInfoKeyListReader extends KeyListReader<Integer, TmCardInfo> {

	@Autowired
	private TmCardInfoDao<TmCardInfo> tmCardInfoDao;

	@Override
	protected List<Integer> loadKeys() {
		// TmCardInfo c = new TmCardInfo();����������ѯ���Ը������������
		return tmCardInfoDao.loadKeysList(
				"com.test.webatch.mapping.TmCardInfoMapper.loadKeyList", null);
	}

	@Override
	protected TmCardInfo loadItem(Integer key) {
		TmCardInfo m = new TmCardInfo();
		m.setId(key);
		return tmCardInfoDao.queryByKey(m);
	}

}
