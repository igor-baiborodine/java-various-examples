package com.kiroule.example.mybatis;

import org.hsqldb.Server;
import org.hsqldb.persist.HsqlProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Igor Baiborodine
 */
public class TestDbServer {

  private static final Logger logger = LoggerFactory.getLogger(TestDbServer.class);
  private static Server server;

  public static void start() {

    HsqlProperties props = new HsqlProperties();
    props.setProperty("server.database.0", "file:/home/bender/Temp;");
    props.setProperty("server.dbname.0", "xdb");
    server = new org.hsqldb.Server();
    try {
      server.setProperties(props);
    } catch (Exception e) {
      logger.error("Error while setting server properties:", e);
      return;
    }
    logger.info("Starting HSQLDB server...");
    server.start();
  }

  public static void stop() {
    logger.info("Shutting down HSQLDB server...");
    server.shutdown();
  }

}
