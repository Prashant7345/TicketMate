package com.qfc.ticket.config;

import java.beans.PropertyVetoException;
import java.util.Properties;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.mchange.v2.c3p0.ComboPooledDataSource;

@ConditionalOnProperty(value = "qticket.mysql.db.connect", havingValue = "Y", matchIfMissing = false)
@Configuration
@EnableTransactionManagement
@Order(1)
public class MySqlDbConfig {
	private static final Logger logger = LoggerFactory.getLogger(MySqlDbConfig.class);
//qmandate details start

	@Value("${app.datasource.mysql.qticket.driverClassName}")
	private String qticket_DB_DRIVER;

	@Value("${app.datasource.mysql.qticket.password}")
	private String qticket_DB_PASSWORD;

	@Value("${app.datasource.mysql.qticket.hibernate.jdbc.batch_size:1000}")
	private Integer qticket_BatchSize;

	@Value("${app.datasource.mysql.qticket.url}")
	private String qticket_DB_URL;

	@Value("${app.datasource.mysql.qticket.username}")
	private String qticket_DB_USERNAME;

	@Value("${app.datasource.mysql.qticket.hibernate.dialect}")
	private String qticket_HIBERNATE_DIALECT;

	@Value("${app.datasource.mysql.qticket.hibernate.show_sql}")
	private String qticket_HIBERNATE_SHOW_SQL;

	@Value("${app.datasource.mysql.qticket.hibernate.hbm2ddl.auto}")
	private String qticket_HIBERNATE_HBM2DDL_AUTO;

	@Value("${app.datasource.mysql.qticket.entitymanager.packagesToScan}")
	private String qticket_ENTITYMANAGER_PACKAGES_TO_SCAN;

	@Value("${app.datasource.mysql.qticket.hibernate.c3p0.max_size}")
	private String qticket_CONN_POOL_MAX_SIZE;

	@Value("${app.datasource.mysql.qticket.hibernate.c3p0.min_size}")
	private String qticket_CONN_POOL_MIN_SIZE;

	@Value("${app.datasource.mysql.qticket.hibernate.c3p0.idle_test_period}")
	private String qticket_CONN_POOL_IDLE_PERIOD;

	@Value("${app.datasource.mysql.qticket.hibernate.c3p0.unreturnedConnectionTimeout}")
	private String qticket_UNRETURNED_CONNECTION_TIMEOUT;

	@PostConstruct
	public void init() {
//logger.warn("init() - MySqlDbConfig  initiated"); 
		System.out.println("MySql-Init");
	}

	@Bean
	public ComboPooledDataSource datasourceMysqlqticket() {
		ComboPooledDataSource datasourceMysql = new ComboPooledDataSource("q-ticket");
		try {
			datasourceMysql.setDriverClass(qticket_DB_DRIVER);
		} catch (PropertyVetoException pve) {
			System.out.println("Cannot load datasourceMysql driver (" + qticket_DB_DRIVER + ") : " + pve.getMessage());
			logger.error("Cannot load datasourceMysql driver (" + qticket_DB_DRIVER + ") : ", pve);
			return null;
		}
		datasourceMysql.setJdbcUrl(qticket_DB_URL);
		datasourceMysql.setUser(qticket_DB_USERNAME);
		datasourceMysql.setPassword(qticket_DB_PASSWORD);
		datasourceMysql.setMinPoolSize(Integer.parseInt(qticket_CONN_POOL_MIN_SIZE));
		datasourceMysql.setMaxPoolSize(Integer.parseInt(qticket_CONN_POOL_MAX_SIZE));
		datasourceMysql.setMaxIdleTime(Integer.parseInt(qticket_CONN_POOL_IDLE_PERIOD));
		return datasourceMysql;
	}

	@Qualifier(value = "sessionFactory")
	@Bean(name = "sessionFactory")
	public LocalSessionFactoryBean sessionFactoryqticket() {
		LocalSessionFactoryBean sessionFactoryBean = new LocalSessionFactoryBean();
		sessionFactoryBean.setDataSource(datasourceMysqlqticket());
		sessionFactoryBean.setPackagesToScan("com.qfc.ticket.service.dto");
		Properties hibernateProperties = new Properties();
		hibernateProperties.put("hibernate.dialect", qticket_HIBERNATE_DIALECT);
		hibernateProperties.put("hibernate.show_sql", qticket_HIBERNATE_SHOW_SQL);
		hibernateProperties.put("hibernate.hbm2ddl.auto", qticket_HIBERNATE_HBM2DDL_AUTO);
		hibernateProperties.put("hibernate.jdbc.batch_size", qticket_BatchSize);
		hibernateProperties.put("hibernate.order_inserts", "true");
		hibernateProperties.put("hibernate.order_updates", "true");
		sessionFactoryBean.setHibernateProperties(hibernateProperties);
		return sessionFactoryBean;
	}

	@Bean(name = "transactionManager")
	public HibernateTransactionManager transactionManagerqmandate() {
		HibernateTransactionManager transactionManager = new HibernateTransactionManager();
		transactionManager.setSessionFactory(sessionFactoryqticket().getObject());
		return transactionManager;
	}

	@Bean(name = "jdbcTemplateObject")
	@Qualifier(value = "jdbcTemplateObject")
	public JdbcTemplate jdbcTemplate() {
		return new JdbcTemplate(datasourceMysqlqticket());
	}

}