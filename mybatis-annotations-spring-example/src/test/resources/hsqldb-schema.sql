CREATE TABLE country (
  country_id  SMALLINT    NOT NULL IDENTITY,
  country     VARCHAR(50) NOT NULL,
  last_update TIMESTAMP
);

CREATE TABLE city (
   city_id SMALLINT NOT NULL IDENTITY,
   city VARCHAR(50) NOT NULL,
   country_id SMALLINT NOT NULL,
   last_update TIMESTAMP NULL,
   CONSTRAINT fk_city_country FOREIGN KEY (country_id) REFERENCES country (country_id) ON DELETE RESTRICT ON UPDATE CASCADE
);

CREATE TABLE address (
   address_id SMALLINT NOT NULL IDENTITY,
   address VARCHAR(50) NOT NULL,
   address2 VARCHAR(50) NULL,
   district VARCHAR(20) NOT NULL,
   city_id SMALLINT NOT NULL,
   postal_code VARCHAR(10) NULL,
   phone VARCHAR(20) NOT NULL,
   last_update TIMESTAMP NULL,
   CONSTRAINT fk_address_city FOREIGN KEY (city_id) REFERENCES city (city_id) ON DELETE RESTRICT ON UPDATE CASCADE
);

CREATE TABLE customer (
   customer_id SMALLINT NOT NULL IDENTITY,
   store_id TINYINT NOT NULL,
   first_name VARCHAR(45) NOT NULL,
   last_name VARCHAR(45) NOT NULL,
   email VARCHAR(50) NULL,
   address_id SMALLINT NOT NULL,
   active BOOLEAN NOT NULL,
   create_date DATETIME NOT NULL,
   last_update TIMESTAMP
);