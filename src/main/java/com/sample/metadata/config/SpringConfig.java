package com.sample.metadata.config;

import java.util.Properties;

import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSource;
import org.hibernate.SessionFactory;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.hibernate4.HibernateTransactionManager;
import org.springframework.orm.hibernate4.LocalSessionFactoryBean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@ComponentScan({ "com.sample.metadata" })
@EnableTransactionManagement
@EnableScheduling
public class SpringConfig {
	@Bean
	public DataSource dataSource() {

		BasicDataSource ds = new org.apache.commons.dbcp.BasicDataSource();
		ds.setDriverClassName("com.mysql.jdbc.Driver");
		ds.setUrl("jdbc:mysql://localhost:3306/test");
		ds.setUsername("root");
		// ds.setPassword("root");
		ds.setPassword("root");
		return ds;
	}

	@Bean
	public SessionFactory sessionFactory() throws Exception{
		LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
		sessionFactory.setDataSource(this.dataSource());
		sessionFactory.setPackagesToScan("com.sample.metadata.domain");
		Properties hibernateProperties = new Properties();
		
		hibernateProperties.setProperty("hibernate.dialect",
				"org.hibernate.dialect.MySQLDialect");
		
		hibernateProperties.setProperty("hbm2ddl.auto",
				"create");
		
		
		sessionFactory.setHibernateProperties(hibernateProperties);
		sessionFactory.afterPropertiesSet();
		return sessionFactory.getObject();
	}
	@Bean
	public HibernateTransactionManager transactionManager()throws Exception{
		HibernateTransactionManager hibernateTransactionManager = new HibernateTransactionManager(this.sessionFactory());
//		hibernateTransactionManager.setSessionFactory(this.sessionFactory());
		return hibernateTransactionManager;
	}

}
