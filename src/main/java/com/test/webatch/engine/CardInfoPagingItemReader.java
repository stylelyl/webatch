package com.test.webatch.engine;

import java.util.ArrayList;
import java.util.Map;

import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.database.AbstractPagingItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ClassUtils;

import com.test.webatch.dao.Page;
import com.test.webatch.dao.TmCardInfoDao;
import com.test.webatch.domain.TmCardInfo;

/**
 * 说明：spring batch 中只提供了IbatisPagingItemReader，这里只有重写了
 * 以SqlSession替换SqlMapClient，SqlSessionTemplate替换SqlMapClientTemplate
 * */
@StepScope()
public class CardInfoPagingItemReader extends
		AbstractPagingItemReader<TmCardInfo> {

	@Autowired
	private TmCardInfoDao<TmCardInfo> tmCardInfoDao;

	private Map<String, Object> parameterValues;

	public CardInfoPagingItemReader() {
		setName(ClassUtils.getShortName(CardInfoPagingItemReader.class));
	}

	public void afterPropertiesSet() throws Exception {
		super.afterPropertiesSet();
		// sqlSessionTemplate = new SqlSessionTemplate(sqlSessionFactory,
		// ExecutorType.BATCH);
		// notNull(queryId);
	}

	@Override
	protected void doReadPage() {
		int curpage = ((Integer) (parameterValues.get("page")));
		int pageSize = (Integer) (parameterValues.get("pageSize"));
		if (results == null) {
			results = new ArrayList<TmCardInfo>();
		} else {
			results.clear();
		}

		Page<TmCardInfo> page = new Page<TmCardInfo>();
		page.setPageNumber(curpage);
		page.setPageSize(pageSize);
		System.out.println(Thread.currentThread().getName() + ":" + curpage
				+ "," + getPageSize() + "," + getPage());
		tmCardInfoDao.queryForPageList(new TmCardInfo(), parameterValues, page);
		if (getPage() <= (page.getTotal() / page.getPageSize() + 1)) {
			results.addAll(page.getRows());
		}
		// System.out.println("===" + page.getRows().size());
		// results.addAll(page.getRows());
	}

	@Override
	protected void doJumpToPage(int itemIndex) {
	}

	public void setParameterValues(Map<String, Object> parameterValues) {
		this.parameterValues = parameterValues;
	}

	public Map<String, Object> getParameterValues() {
		return parameterValues;
	}

}
