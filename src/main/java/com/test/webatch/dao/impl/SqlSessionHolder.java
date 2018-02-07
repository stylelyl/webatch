package com.test.webatch.dao.impl;

public class SqlSessionHolder {

	private SqlSessionHolder() {
	}

	private static ThreadLocal<String> useSqlSession = new ThreadLocal<String>();

	public static void setSqlSession(String sqlSessionName) {
		SqlSessionHolder.useSqlSession.set(sqlSessionName);
	}

	public static String get() {
		return SqlSessionHolder.useSqlSession.get();
	}

}
