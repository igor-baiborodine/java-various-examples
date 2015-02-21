package com.kiroule.example.springjpahsqldb.config;

import com.google.common.collect.Maps;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

import java.util.Map;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

@Configuration
@PropertySource({"classpath:com/kiroule/example/springjpahsqldb/config/jpa-persistence.properties"})
public class JpaPersistenceConfig {

  @Autowired
  Environment environment;

  @Autowired
  private DataSource dataSource;

  @Bean
  public JpaTransactionManager transactionManager() {

    JpaTransactionManager jpaTransactionManager = new JpaTransactionManager();
    jpaTransactionManager.setEntityManagerFactory(entityManagerFactory());
    return jpaTransactionManager;
  }

  @Bean
  public EntityManagerFactory entityManagerFactory() {

    LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();
    factory.setDataSource(dataSource);
    factory.setPersistenceUnitName("springJpaHsqldbUnit");
    factory.setPackagesToScan("com.kiroule.example.springjpahsqldb.domain");
    factory.setJpaVendorAdapter(jpaVendorAdapter());
    factory.setJpaPropertyMap(additionalProperties());
    factory.afterPropertiesSet();
    return factory.getObject();
  }

  @Bean
  public JpaVendorAdapter jpaVendorAdapter() {

    HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
    vendorAdapter.setDatabase(environment.getProperty("jpa.database", Database.class));
    vendorAdapter.setGenerateDdl(environment.getProperty("jpa.generateDdl", Boolean.class));
    return vendorAdapter;
  }

  private Map<String, Object> additionalProperties() {

    Map<String, Object> properties = Maps.newHashMap();
    properties.put("hibernate.dialect", environment.getProperty("hibernate.dialect"));
    properties.put("hibernate.show_sql", environment.getProperty("hibernate.show_sql"));
    properties.put("hibernate.format_sql", environment.getProperty("hibernate.format_sql"));
    properties.put("hibernate.connection.shutdown",
                   environment.getProperty("hibernate.connection.shutdown"));
    properties.put("hibernate.hbm2ddl.auto", environment.getProperty("hibernate.hbm2ddl.auto"));
    return properties;
  }

}