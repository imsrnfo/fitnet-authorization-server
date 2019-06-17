package com.igmasiri.fitnet.authorizationserver.config.database;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Properties;

import javax.annotation.PreDestroy;
import javax.naming.NamingException;
import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.jdbc.datasource.init.ScriptException;
import org.springframework.jdbc.datasource.init.ScriptUtils;
import org.springframework.jndi.JndiObjectFactoryBean;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
public class DatabaseConfig {
	
	@Autowired
	private Environment environment;
	
	@Bean
	public LocalContainerEntityManagerFactoryBean entityManagerFactory() throws IllegalArgumentException, NamingException, ScriptException, IOException, SQLException {
		LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
		em.setDataSource(dataSource());
		em.setPackagesToScan(new String[] { "com.igmasiri.fitnet.authorizationserver.models" });
		JpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
		em.setJpaVendorAdapter(vendorAdapter);
		em.setJpaProperties(additionalProperties());

		return em;
	}

	@PreDestroy
	public void destroy() {
		try {
			if (Arrays.asList(environment.getActiveProfiles()).contains("test")) {
				dataSource().getConnection().createStatement().execute("SHUTDOWN");
			}
		} catch (ScriptException | IllegalArgumentException | SQLException | NamingException | IOException e) {
			e.printStackTrace();
		}
	}
	
	@Bean
	public DataSource dataSource() throws IllegalArgumentException, NamingException, IOException, ScriptException, SQLException {
		if (Arrays.asList(environment.getActiveProfiles()).contains("produccion")) {
			System.out.println("Ambiente de produccion");
			JndiObjectFactoryBean bean = new JndiObjectFactoryBean();
			bean.setJndiName("java:/fitnetAuthorizationServerDS");
			bean.setProxyInterface(DataSource.class);
			bean.setLookupOnStartup(false);
			bean.afterPropertiesSet();
			return (DataSource) bean.getObject();
		} else if (Arrays.asList(environment.getActiveProfiles()).contains("test")) {
		  	DriverManagerDataSource dataSource = new DriverManagerDataSource();
	        dataSource.setDriverClassName("org.h2.Driver");
			dataSource.setUrl("jdbc:h2:mem:db;DB_CLOSE_DELAY=-1;MODE=MySQL");
	        dataSource.setUsername("sa");
	        dataSource.setPassword("sa");
			ScriptUtils.executeSqlScript(dataSource.getConnection(), new ByteArrayResource(IOUtils.resourceToByteArray("/scripts/v1_0_0.sql")));
			return dataSource;
		} else {
			System.out.println("Ambiente de desarrollo");
			String parametrosDB = "?useSSL=false&useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
			return DataSourceBuilder.create()
					.url("jdbc:mysql://127.0.0.1/tmp"+parametrosDB)
					.username("root")
					.password("puntos")
					.build();
		}
	}
	
	@Bean
	public PlatformTransactionManager transactionManager(EntityManagerFactory emf) {
		JpaTransactionManager transactionManager = new JpaTransactionManager();
		transactionManager.setEntityManagerFactory(emf);
		return transactionManager;
	}

	@Bean
	public PersistenceExceptionTranslationPostProcessor exceptionTranslation() {
		return new PersistenceExceptionTranslationPostProcessor();
	}

	Properties additionalProperties() {
		
		String hbm2ddl;
		
		if (Arrays.asList(environment.getActiveProfiles()).contains("test")) {
			hbm2ddl = "none";
		} else {
			hbm2ddl = "validate";
		}
		
		Properties properties = new Properties();
		properties.setProperty("hibernate.hbm2ddl.auto", hbm2ddl);
		properties.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQL57Dialect");
		return properties;
	}

}
