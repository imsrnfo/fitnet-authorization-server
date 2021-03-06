package com.igmasiri.fitnet.authorizationserver.config.database;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Properties;
import java.util.function.Supplier;

import javax.annotation.PreDestroy;
import javax.naming.NamingException;
import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import com.fasterxml.jackson.databind.annotation.JsonAppend;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
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

	@Value("${fitnet.datasource.url}")
	private String url;
	@Value("${fitnet.datasource.username}")
	private String username;
	@Value("${fitnet.datasource.password}")
	private String password;
	@Value("${fitnet.datasource.action}")
	private String action;
	
	@Bean
	public LocalContainerEntityManagerFactoryBean entityManagerFactory() throws IllegalArgumentException, NamingException, ScriptException, IOException, SQLException {
		LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
		em.setDataSource(dataSource());
		em.setPackagesToScan(new String[] { "com.igmasiri.fitnet.authorizationserver.models" });
		JpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
		em.setJpaVendorAdapter(vendorAdapter);

		setJpaProperties : {
			Supplier<Properties> getProperties = () -> {
				Properties properties = new Properties();
				properties.setProperty("hibernate.hbm2ddl.auto", Arrays.asList(environment.getActiveProfiles()).contains("test") ? "none" : action);
				properties.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQL57Dialect");
				return properties;
			};
			em.setJpaProperties(getProperties.get());
		}

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
			bean.setJndiName(url);
			bean.setProxyInterface(DataSource.class);
			bean.setLookupOnStartup(false);
			bean.afterPropertiesSet();
			return (DataSource) bean.getObject();
		} else if (Arrays.asList(environment.getActiveProfiles()).contains("test")) {
		  	DriverManagerDataSource dataSource = new DriverManagerDataSource();
	        dataSource.setDriverClassName("org.h2.Driver");
			dataSource.setUrl(url);
	        dataSource.setUsername(username);
	        dataSource.setPassword(password);
			ScriptUtils.executeSqlScript(dataSource.getConnection(), new ByteArrayResource(IOUtils.resourceToByteArray("/scripts/v1_0_0.sql")));
			return dataSource;
		} else {
			System.out.println("Ambiente de desarrollo");
			return DataSourceBuilder.create()
					.url(url)
					.username(username)
					.password(password)
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

}
