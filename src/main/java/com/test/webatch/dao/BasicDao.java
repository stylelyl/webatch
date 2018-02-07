package com.test.webatch.dao;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContextAware;


public interface BasicDao<T> {
	public int save(T entity);

	public int save(String sqlId, T entity);

	public int update(T entity);

	public int updateNotNullable(T entity);

	public int update(String sqlId, T entity);

	public int deleteByKey(T entity);

	public int delete(String sqlId, T entity);

	public T queryByKey(T entity);

	public List<Integer> loadKeysList(T entity);

	public List<Integer> loadKeysList(String sqlId, Map<String, Object> params);

	public Page<T> queryForPageList(T entity, Map<String,Object> params, Page<T> page);

	public Page<T> queryForPage(String className, Map<String, Object> params,Page<T> page);
}
