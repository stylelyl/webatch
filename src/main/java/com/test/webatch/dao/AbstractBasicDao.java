package com.test.webatch.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

import com.test.webatch.dao.impl.BasicDaoImpl;

public abstract class AbstractBasicDao<T> implements BasicDao<T> {

	@Autowired
	private BasicDaoImpl<T> basicDao;

	public int save(T entity) {

		return basicDao.save(entity);
	}

	public int save(String sqlId, T entity) {

		return basicDao.save(sqlId, entity);
	}

	public int update(T entity) {
		return basicDao.update(entity);
	}

	public int update(String sqlId, T entity) {

		return basicDao.update(sqlId, entity);
	}

	public int deleteByKey(T entity) {

		return basicDao.deleteByKey(entity);
	}

	public int delete(String sqlId, T entity) {

		return basicDao.delete(sqlId, entity);
	}

	public T queryByKey(T entity) {

		return basicDao.queryByKey(entity);
	}

	public final SqlSession getSqlSession() {

		return basicDao.getSqlSession();
	}

	@Override
	public int updateNotNullable(T entity) {

		return basicDao.updateNotNullable(entity);
	}

	@Override
	public List<Integer> loadKeysList(T entity) {
		return basicDao.loadKeysList(entity);
	}

	@Override
	public List<Integer> loadKeysList(String sqlId, Map<String, Object> params) {
		return basicDao.loadKeysList(sqlId, params);
	}

	@Override
	public Page<T> queryForPageList(T entity, Map<String, Object> params,
			Page<T> page) {
		return basicDao.queryForPageList(entity, params, page);
	}

	@Override
	public Page<T> queryForPage(String className, Map<String, Object> params,
			Page<T> page) {
		return basicDao.queryForPage(className, params, page);
	}
}
