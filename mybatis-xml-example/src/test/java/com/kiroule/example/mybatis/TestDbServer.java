package com.kiroule.example.mybatis;

import org.hsqldb.Server;
import org.hsqldb.persist.HsqlProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * @author Igor Baiborodine
 */
public class TestDbServer {

  private static final Logger logger = LoggerFactory.getLogger(TestDbServer.class);
  private static Server server;

  public static void start() {

    HsqlProperties props = new HsqlProperties();
    props.setProperty("server.database.0", "mem:testdb;");
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

  public static void createTables() {
    try (Connection conn = getConnection();
         Statement stmt = conn.createStatement()) {
      stmt.executeQuery(COUNTRY_CREATE_TABLE);
      stmt.executeQuery(COUNTRY_ALTER_TABLE);
      //stmt.executeQuery(CITY_TABLE_SCHEMA);
      //stmt.executeQuery(ADDRESS_TABLE_SCHEMA);
      logger.info("Created test tables");
    } catch (SQLException | ClassNotFoundException e) {
      logger.error("Error while initializing test database: ", e);
    }
  }

  public static void dropTables() {
    try (Connection conn = getConnection();
         Statement stmt = conn.createStatement()) {
      stmt.executeQuery(DROP_TABLE + "CITY");
      stmt.executeQuery(DROP_TABLE + "COUNTRY");
      stmt.executeQuery(DROP_TABLE + "ADDRESS");
      logger.info("Dropped test tables");
    } catch (SQLException | ClassNotFoundException e) {
      logger.error("Error while initializing test database: ", e);
    }
  }

  private static Connection getConnection() throws ClassNotFoundException, SQLException {
    Class.forName("org.hsqldb.jdbcDriver");
    return DriverManager.getConnection("jdbc:hsqldb:mem:testdb", "SA", "");
  }

  private static final String DROP_TABLE = "DROP TABLE IF EXISTS ";

  private static final String COUNTRY_CREATE_TABLE =
      "CREATE TABLE country (" +
          "  country_id SMALLINT NOT NULL," +
          "  country VARCHAR(50) NOT NULL," +
          "  last_update TIMESTAMP NOT NULL,\n" +
          "  PRIMARY KEY  (country_id)\n" +
          ")";

  private static final String COUNTRY_ALTER_TABLE =
      "ALTER TABLE country ALTER COLUMN last_update SET DEFAULT CURRENT_TIMESTAMP";

  private static final String CITY_TABLE_SCHEMA =
      "CREATE TABLE city (" +
          "  city_id SMALLINT NOT NULL," +
          "  city VARCHAR(50) NOT NULL," +
          "  country_id SMALLINT NOT NULL," +
          "  last_update TIMESTAMP NOT NULL," +
          "  PRIMARY KEY  (city_id)," +
          "  KEY idx_fk_country_id (country_id)," +
          "  CONSTRAINT `fk_city_country` FOREIGN KEY (country_id) REFERENCES country (country_id)" +
          ")";

  private static final String ADDRESS_TABLE_SCHEMA =
      "CREATE TABLE address (" +
          "  address_id SMALLINT UNSIGNED NOT NULL AUTO_INCREMENT," +
          "  address VARCHAR(50) NOT NULL," +
          "  address2 VARCHAR(50) DEFAULT NULL," +
          "  district VARCHAR(20) NOT NULL," +
          "  city_id SMALLINT UNSIGNED NOT NULL," +
          "  postal_code VARCHAR(10) DEFAULT NULL," +
          "  phone VARCHAR(20) NOT NULL," +
          "  last_update TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP," +
          "  PRIMARY KEY  (address_id)," +
          "  KEY idx_fk_city_id (city_id)," +
          "  CONSTRAINT `fk_address_city` FOREIGN KEY (city_id) REFERENCES city (city_id)" +
          ")";
}
