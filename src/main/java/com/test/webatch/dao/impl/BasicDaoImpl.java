package com.test.webatch.dao.impl;

import static org.springframework.util.Assert.notNull;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.ibatis.builder.BuilderException;
import org.apache.ibatis.builder.xml.XMLMapperEntityResolver;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.io.Resource;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.ErrorHandler;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

import com.github.pagehelper.PageHelper;
import com.test.webatch.dao.BasicDao;
import com.test.webatch.dao.Page;

public class BasicDaoImpl<T> implements BasicDao<T>, InitializingBean,
		ApplicationContextAware {
	private ApplicationContext applicationContext;
	private Map<String, String> typeNameSpaceMapping = new HashMap<String, String>();

	private static final String INSERT_STATEMENT = ".insert";

	private static final String UPDATE_STATEMENT = ".updateByPrimaryKey";

	private static final String UPDATE_NOT_NULL_STATEMENT = ".updateNotNullByPrimaryKey";

	private static final String DELETE_STATEMENT = ".deleteByPrimaryKey";

	private static final String SELECT_STATEMENT = ".selectByPrimaryKey";

	private static final String SELECT_ALL_STATEMENT = ".selectAll";

	private static final String SELECT_KEY_LIST = ".loadKeyList";

	private static final String SELECT_KEY_LIST_STR = ".loadKeyStrList";

	private static final String SUFFIX = "Mapper.xml";

	// @Autowired
	private SqlSessionTemplate sqlSession;

	private Resource[] mapperLocations;

	// private Map<String,String> typeNameSpaceMapping = new
	// HashMap<String,String> ();
	// private static final String SUFFIX = "Mapper.xml";

	@Override
	public void setApplicationContext(ApplicationContext arg0)
			throws BeansException {
		this.applicationContext = arg0;
	}

	public final SqlSessionTemplate getSqlSession() {
		return (SqlSessionTemplate) applicationContext.getBean("sqlSession");
	}

	// public final SqlSessionTemplate getSqlSession() {
	// return sqlSession;
	// }

	@Override
	public void afterPropertiesSet() throws Exception {
		notNull(mapperLocations, "Property 'mapperLocations' is required");
	}

	public void setMapperLocations(Resource[] mapperLocations) {
		this.mapperLocations = mapperLocations;
	}

	@Override
	public int save(T entity) {
		return getSqlSession().insert(
				obtainNameSpace(entity.getClass()) + INSERT_STATEMENT, entity);
	}

	@Override
	public int save(String sqlId, T entity) {
		return getSqlSession().insert(sqlId, entity);
	}

	@Override
	public int update(T entity) {
		return getSqlSession().update(
				obtainNameSpace(entity.getClass()) + UPDATE_STATEMENT, entity);
	}

	@Override
	public int updateNotNullable(T entity) {
		return getSqlSession().update(
				obtainNameSpace(entity.getClass()) + UPDATE_NOT_NULL_STATEMENT,
				entity);
	}

	@Override
	public int update(String sqlId, T entity) {
		return getSqlSession().update(sqlId, entity);
	}

	@Override
	public int deleteByKey(T entity) {
		return getSqlSession().delete(
				obtainNameSpace(entity.getClass()) + DELETE_STATEMENT,
				buildMap(entity.getClass(), entity));
	}

	@Override
	public int delete(String sqlId, T entity) {
		return getSqlSession().delete(sqlId, entity);
	}

	@Override
	public T queryByKey(T entity) {
		return getSqlSession().selectOne(
				obtainNameSpace(entity.getClass()) + SELECT_STATEMENT,
				buildMap(entity.getClass(), entity));
	}

	@Override
	public List<Integer> loadKeysList(T entity) {
		return getSqlSession().selectList(
				obtainNameSpace(entity.getClass()) + SELECT_KEY_LIST_STR,
				buildMap(entity.getClass(), entity));
	}

	@Override
	public List<Integer> loadKeysList(String sqlId, Map<String, Object> params) {
		return getSqlSession().selectList(sqlId, params);
	}

	@Override
	public Page<T> queryForPageList(T entity, Map<String, Object> params,
			Page<T> page) {
		com.github.pagehelper.Page<T> pageTemp = PageHelper.startPage(
				page.getPageNumber(), page.getPageSize(), true);
		if (params != null) {
			params.putAll(page.getQuery());
		} else {
			params = page.getQuery();
		}

		queryForList(entity, params);

		page.setTotal(pageTemp.getTotal());
		page.setRows(pageTemp.getResult());

		return page;
	}

	public List<T> queryForList(T entity, Map<String, Object> params) {
		return getSqlSession().selectList(
				obtainNameSpace(entity.getClass()) + SELECT_ALL_STATEMENT,
				buildMapWithParams(entity.getClass(), entity, params));
	}

	public Page<T> queryForPage(String className, Map<String, Object> params,
			Page<T> page) {
		try {
			com.github.pagehelper.Page<T> pageTemp = PageHelper.startPage(
					page.getPageNumber(), page.getPageSize(), true);
			if (params != null) {
				params.putAll(page.getQuery());
			} else {
				params = page.getQuery();
			}

			// queryForList(entity, params);
			Class cls = Class.forName(className);
			Object obj = cls.newInstance();
			List<T> list = getSqlSession().selectList(
					obtainNameSpace(cls) + SELECT_ALL_STATEMENT,
					buildMapWithParams(cls, obj, params));
			// System.out.println("queryForPage size=" + list.size());
			page.setTotal(pageTemp.getTotal());
			page.setRows(pageTemp.getResult());
			return page;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	private Map<String, Object> buildMap(Class clazz, Object beanInstance) {
		Map<String, Object> map = null;
		try {
			map = buildMapFromBean(clazz, beanInstance);
		} catch (Exception e) {
			map = new HashMap<String, Object>();
		}

		return map;
	}

	private Map<String, Object> buildMapWithParams(Class clazz,
			Object beanInstance, Map<String, Object> params) {
		Map<String, Object> map = null;
		try {
			map = buildMapFromBean(clazz, beanInstance);
		} catch (Exception e) {
			map = new HashMap<String, Object>();
		}
		if (params != null && params.size() > 0) {
			map.putAll(params);
		}

		return map;
	}

	private Map<String, Object> buildMapFromBean(Class clazz,
			Object beanInstance) throws Exception {
		Map<String, Object> context = new HashMap<String, Object>();

		BeanInfo beanInfo = Introspector.getBeanInfo(clazz);
		PropertyDescriptor[] pds = beanInfo.getPropertyDescriptors();

		if (null == pds) {
			return context;
		}

		for (PropertyDescriptor pd : pds) {
			context.put(pd.getName(),
					pd.getReadMethod().invoke(beanInstance, new Object[] {}));
		}

		return context;
	}

	private String obtainNameSpace(Class clazz) {
		String modelName = clazz.getSimpleName();
		return obtainNamespace(modelName);
	}

	private String obtainNamespace(String modelName) {
		String namespace = typeNameSpaceMapping.get(modelName);

		if (namespace != null && namespace.length() > 0) {
			return namespace;
		}

		for (Resource mapperLocation : this.mapperLocations) {
			if (mapperLocation == null) {
				continue;
			}

			String mappingFileName = mapperLocation.getFilename();
			String keyName = null;
			if ((mappingFileName != null && mappingFileName.length() > 0)
					&& mappingFileName.endsWith(SUFFIX)) {
				int pos = mappingFileName.lastIndexOf(SUFFIX);
				if (pos > 0) {
					keyName = mappingFileName.substring(0, pos);
				}
			}

			if (keyName == null || "".equals(keyName)) {
				continue;
			}

			if (!modelName.equals(keyName)) {
				continue;
			}

			try {
				Document document = createDocument(new InputSource(
						mapperLocation.getInputStream()));
				Element root = document.getDocumentElement();
				String nameSpace = root.getAttribute("namespace");

				typeNameSpaceMapping.put(keyName, nameSpace);

				return nameSpace;

			} catch (Exception e) {
				throw new RuntimeException(
						"Failed to parse mapping resource: '" + mapperLocation
								+ "'", e);
			} finally {
				try {
					mapperLocation.getInputStream().close();
				} catch (Exception e) {
					;
				}
			}
		}

		throw new RuntimeException("not found mapping file for " + modelName);
	}

	private Document createDocument(InputSource inputSource) {
		try {
			DocumentBuilderFactory factory = DocumentBuilderFactory
					.newInstance();
			factory.setValidating(false);

			factory.setNamespaceAware(false);
			factory.setIgnoringComments(true);
			factory.setIgnoringElementContentWhitespace(false);
			factory.setCoalescing(false);
			factory.setExpandEntityReferences(true);

			DocumentBuilder builder = factory.newDocumentBuilder();
			builder.setEntityResolver(new XMLMapperEntityResolver());
			builder.setErrorHandler(new ErrorHandler() {
				public void error(SAXParseException exception)
						throws SAXException {
					throw exception;
				}

				public void fatalError(SAXParseException exception)
						throws SAXException {
					throw exception;
				}

				public void warning(SAXParseException exception)
						throws SAXException {
				}
			});

			return builder.parse(inputSource);
		} catch (Exception e) {
			throw new BuilderException(
					"Error creating document instance.  Cause: " + e, e);
		}
	}

}
