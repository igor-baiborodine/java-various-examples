package com.kiroule.jpetstore.vaadinspring.config;

import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

import javax.sql.DataSource;

/**
 * @author Igor Baiborodine
 */
@Configuration
@MapperScan("com.kiroule.jpetstore.vaadinspring.persistence")
public class DataSourceConfig {

  @Bean
  public DataSource dataSource() {
    return new EmbeddedDatabaseBuilder()
        .setType(EmbeddedDatabaseType.HSQL)
        .addScript("classpath:database/jpetstore-hsqldb-schema.sql")
        .addScript("classpath:database/jpetstore-hsqldb-dataload.sql")
        .build();
  }

  @Bean
  public DataSourceTransactionManager transactionManager() {
    return new DataSourceTransactionManager(dataSource());
  }

  @Bean
  public SqlSessionFactoryBean sqlSessionFactory() throws Exception {
    SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
    sqlSessionFactoryBean.setDataSource(dataSource());
    PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
    sqlSessionFactoryBean.setMapperLocations(
        resolver.getResources("classpath:com/kiroule/jpetstore/vaadinspring/persistence/*.xml"));
    sqlSessionFactoryBean.setTypeAliasesPackage("com.kiroule.jpetstore.vaadinspring.domain");
    return sqlSessionFactoryBean;
  }

}
