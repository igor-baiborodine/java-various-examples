package com.kiroule.example.mybatis.config;

import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;

/**
 * @author Igor Baiborodine
 */
@Configuration
@MapperScan("com.kiroule.example.mybatis.mapper")
public class BaseDataSourceConfig {

  @Bean
  public DataSource dataSource() {
    // not implemented
    return null;
  }

  @Bean
  public DataSourceTransactionManager transactionManager() {
    return new DataSourceTransactionManager(dataSource());
  }

  @Bean
  public SqlSessionFactoryBean sqlSessionFactory() throws Exception {
    SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
    sqlSessionFactoryBean.setDataSource(dataSource());
    sqlSessionFactoryBean.setTypeAliasesPackage("com.kiroule.example.mybatis.domain");
    return sqlSessionFactoryBean;
  }
}
