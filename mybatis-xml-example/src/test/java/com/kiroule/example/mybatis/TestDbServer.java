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
  private static final String DROP_TABLE = "DROP TABLE IF EXISTS ";
  private static final String COUNTRY_CREATE_TABLE =
      "CREATE TABLE country (" +
          "  country_id SMALLINT NOT NULL IDENTITY," +
          "  country VARCHAR(50) NOT NULL," +
          "  last_update TIMESTAMP " +
          ")";
  private static final String CITY_CREATE_TABLE =
      "CREATE TABLE city (" +
          "  city_id SMALLINT NOT NULL IDENTITY," +
          "  city VARCHAR(50) NOT NULL," +
          "  country_id SMALLINT NOT NULL," +
          "  last_update TIMESTAMP NULL," +
          "  CONSTRAINT fk_city_country FOREIGN KEY (country_id) REFERENCES country (country_id) ON DELETE RESTRICT ON UPDATE CASCADE" +
          ")";
  private static final String ADDRESS_CREATE_TABLE =
      "CREATE TABLE address (" +
          "  address_id SMALLINT NOT NULL IDENTITY," +
          "  address VARCHAR(50) NOT NULL," +
          "  address2 VARCHAR(50) NULL," +
          "  district VARCHAR(20) NOT NULL," +
          "  city_id SMALLINT NOT NULL," +
          "  postal_code VARCHAR(10) NULL," +
          "  phone VARCHAR(20) NOT NULL," +
          "  last_update TIMESTAMP NULL," +
          "  CONSTRAINT fk_address_city FOREIGN KEY (city_id) REFERENCES city (city_id) ON DELETE RESTRICT ON UPDATE CASCADE\n" +
          ")";
  private static final String CUSTOMER_CREATE_TABLE =
      "CREATE TABLE customer (" +
          "  customer_id SMALLINT NOT NULL IDENTITY," +
          "  store_id TINYINT NOT NULL," +
          "  first_name VARCHAR(45) NOT NULL," +
          "  last_name VARCHAR(45) NOT NULL," +
          "  email VARCHAR(50) NULL," +
          "  address_id SMALLINT NOT NULL," +
          "  active BOOLEAN NOT NULL," +
          "  create_date DATETIME NOT NULL," +
          "  last_update TIMESTAMP" +
          ")";
  private static final String CITY_INSERT_DATA =
      "INSERT INTO city VALUES " +
          "  (1,'A Corua (La Corua)',1,'2006-02-15 04:45:25')," +
          "  (2,'Abha',1,'2006-02-15 04:45:25')";
  private static final String COUNTRY_INSERT_DATA =
      "INSERT INTO country VALUES (1,'Afghanistan','2006-02-15 04:44:00')";
  private static Server server;

  public static void start() {

    HsqlProperties props = new HsqlProperties();
    props.setProperty("server.database.0", "mem:testdb;");
    props.setProperty("server.dbname.0", "xdb");
    server = new org.hsqldb.Server();
    try {
      server.setProperties(props);
      logger.info("Starting HSQLDB server...");
      server.start();
    } catch (Exception e) {
      logger.error("Error while starting server:", e);
    }
  }

  public static void stop() {
    logger.info("Shutting down HSQLDB server...");
    server.shutdown();
  }

  public static void createTables() {
    try (Connection conn = getConnection();
         Statement stmt = conn.createStatement()) {
      stmt.executeQuery(COUNTRY_CREATE_TABLE);
      stmt.executeQuery(CITY_CREATE_TABLE);
      stmt.executeQuery(ADDRESS_CREATE_TABLE);
      stmt.executeQuery(CUSTOMER_CREATE_TABLE);

      logger.info("Created test tables");
    } catch (SQLException | ClassNotFoundException e) {
      logger.error("Error while creating test tables:", e);
    }
  }

  public static void dropTables() {
    try (Connection conn = getConnection();
         Statement stmt = conn.createStatement()) {
      stmt.executeQuery(DROP_TABLE + "CUSTOMER");
      stmt.executeQuery(DROP_TABLE + "ADDRESS");
      stmt.executeQuery(DROP_TABLE + "CITY");
      stmt.executeQuery(DROP_TABLE + "COUNTRY");

      logger.info("Dropped test tables");
    } catch (SQLException | ClassNotFoundException e) {
      logger.error("Error while dropping test tables:", e);
    }
  }

  public static void insertData() {
    try (Connection conn = getConnection();
         Statement stmt = conn.createStatement()) {
      stmt.executeQuery(COUNTRY_INSERT_DATA);
      stmt.executeQuery(CITY_INSERT_DATA);
      logger.info("Inserted test data");
    } catch (SQLException | ClassNotFoundException e) {
      logger.error("Error while inserting test data:", e);
    }
  }

  private static Connection getConnection() throws ClassNotFoundException, SQLException {
    Class.forName("org.hsqldb.jdbcDriver");
    return DriverManager.getConnection("jdbc:hsqldb:mem:testdb", "SA", "");
  }

}
