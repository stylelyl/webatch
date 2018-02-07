package com.test.webatch.engine;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.batch.item.database.AbstractPagingItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.ClassUtils;

/**
 * 说明：spring batch 中只提供了IbatisPagingItemReader，这里只有重写了
 * 以SqlSession替换SqlMapClient，SqlSessionTemplate替换SqlMapClientTemplate
 * */

public class MybatisPagingItemReader<T> extends AbstractPagingItemReader<T> {

	//private SqlSessionFactory sqlSessionFactory;
	//private SqlSession sqlSession;

	private String queryId;

	@Autowired
	private SqlSessionTemplate sqlSession;

	private Map<String, Object> parameterValues;

	public MybatisPagingItemReader() {
		setName(ClassUtils.getShortName(MybatisPagingItemReader.class));
	}

	public void setQueryId(String queryId) {
		this.queryId = queryId;
	}

	/**
	 * The parameter values to be used for the query execution.
	 * 
	 * @param parameterValues
	 *            the values keyed by the parameter named used in the query
	 *            string.
	 */
	public void setParameterValues(Map<String, Object> parameterValues) {
		this.parameterValues = parameterValues;
	}

	/**
	 * Check mandatory properties.
	 * 
	 * @see org.springframework.beans.factory.InitializingBean#afterPropertiesSet()
	 */
	public void afterPropertiesSet() throws Exception {
		super.afterPropertiesSet();
		//Assert.notNull(sqlSession);
		//sqlSessionTemplate = new SqlSessionTemplate(sqlSessionFactory);
		Assert.notNull(queryId);
	}

	/**
	 * 在此处读取每一页页的数据，读完之后getPage()在父类里面增加1。
	 * 所以将读取的当页的数据查询到results当中
	 * */
	@Override
	@SuppressWarnings("unchecked")
	protected void doReadPage() {
		Map<String, Object> parameters = new HashMap<String, Object>();
		if (parameterValues != null) {
			parameters.putAll(parameterValues);
		}
		parameters.put("_page", getPage());
		parameters.put("_pagesize", getPageSize());
		parameters.put("_skiprows", getPage() * getPageSize());
		if (results == null) {
			results = new ArrayList<T>();// CopyOnWriteArrayList
		} else {
			results.clear();
		}
		results.addAll((List<T>) sqlSession.selectList(queryId,
				parameters));
	}

	@Override
	protected void doJumpToPage(int itemIndex) {
	}

//	public void setSqlSessionFactory(SqlSessionFactory sqlSessionFactory) {
//		this.sqlSessionFactory = sqlSessionFactory;
//	}
//
//	public void setSqlSession(SqlSession sqlSession) {
//		this.sqlSession = sqlSession;
//	}
//
//	public void setSqlSessionTemplate(SqlSessionTemplate sqlSessionTemplate) {
//		this.sqlSessionTemplate = sqlSessionTemplate;
//	}

}
