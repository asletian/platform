package com.crazy.pss.common.persistence;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.dialect.H2Dialect;
import org.hibernate.dialect.MySQL5InnoDBDialect;
import org.hibernate.dialect.Oracle10gDialect;

/**
 * 
 * @Description Hibernate帮助类
 * 
 * @author crazy/Y
 * @date 2013-4-5
 */
public class HibernateUtils {
	
	/**
	 * @Description 动态获取数据库方言
	 * 
	 * @param dataSource	数据源
	 * @return	数据库方言类名
	 */
	public static String getDialect(DataSource dataSource){
		String url = null;
		Connection connection = null;
		try {
			connection = dataSource.getConnection();
			if (connection == null) {
				throw new IllegalStateException("Connection returned by DataSource [" + dataSource + "] was null");
			}
			url = connection.getMetaData().getURL();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if(connection != null){
				try {
					connection.close();
				} catch (SQLException e) {
					connection = null;
				}
			}
		}
		
		//根据jdbc url判断dialect
		if (StringUtils.contains(url, ":h2:")) {
			return H2Dialect.class.getName();
		} else if (StringUtils.contains(url, ":mysql:")) {
			return MySQL5InnoDBDialect.class.getName();
		} else if (StringUtils.contains(url, ":oracle:")) {
			return Oracle10gDialect.class.getName();
		} else {
			throw new IllegalArgumentException("Unknown Database");
		}
		
	}
}
