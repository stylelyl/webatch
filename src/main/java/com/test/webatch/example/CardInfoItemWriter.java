package com.test.webatch.example;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;

import com.test.webatch.domain.TmCardInfo;


/**
 * Dummy {@link ItemWriter} which only logs data it receives.
 */
@Component("cardwriter")
public class CardInfoItemWriter implements ItemWriter<TmCardInfo> {

	private static final Log log = LogFactory.getLog(CardInfoItemWriter.class);
	
	public void write(List<? extends TmCardInfo> data) throws Exception {
		log.info(data);
	}

}
