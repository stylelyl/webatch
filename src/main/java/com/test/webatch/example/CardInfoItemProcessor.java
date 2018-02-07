package com.test.webatch.example;

import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.test.webatch.dao.TmCardInfoDao;
import com.test.webatch.domain.TmCardInfo;

/**
 * {@link ItemReader} with hard-coded input data.
 */

public class CardInfoItemProcessor implements
		ItemProcessor<TmCardInfo, TmCardInfo> {

	private static final Log log = LogFactory
			.getLog(CardInfoItemProcessor.class);
	@Autowired
	private TmCardInfoDao<TmCardInfo> tmCardInfoDao;
	
	
	@Override
	public TmCardInfo process(TmCardInfo s) throws Exception {
		log.info(Thread.currentThread().getName()+"-"+s.toString());
		s.setUpdateDate(new Date());
		//tmCardInfoDao.updateNotNullable(s);
		return s;
	}

}
