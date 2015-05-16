package com.kiroule.example.mybatis;

import org.apache.ibatis.session.SqlSessionManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStream;

/**
 * @author Igor Baiborodine
 */
public class MybatisExampleUtil {

  private static final Logger logger = LoggerFactory.getLogger(MybatisExampleUtil.class);
  private static SqlSessionManager sqlSessionManager;

  static {
    try (InputStream inputStream = MybatisExampleUtil.class.getResourceAsStream(
        "/mybatisConfig.xml")) {
      sqlSessionManager = SqlSessionManager.newInstance(inputStream);
      logger.info("Created initial SqlSessionManager[{}]", sqlSessionManager);
    } catch (Throwable t) {
      logger.error("Error while creating initial SqlSessionManager", t);
    }
  }

  public static SqlSessionManager getSqlSessionManager() {
    return sqlSessionManager;
  }
}
