package org.mybatis.jpetstore.persistence.helper;

import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.Properties;

/**
 * @author Igor Baiborodine
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(value = "classpath:/persistence-context-test.xml")
public class DatabaseLoader {

    private static final Logger logger = LoggerFactory.getLogger(DatabaseLoader.class);
    private static Properties properties = new Properties();

    String[] data = new String[] {
        "INSERT INTO bannerdata VALUES ('DOGS','<image src=\"./images/banner_dogs.gif\">')",
        "INSERT INTO account(userid, version, email, firstname, lastname, status, addr1,"
                + " addr2, city, state, zip, country, phone, favcategory)"
                + " VALUES('j2ee', 0, 'yourname@yourdomain.com','ABC', 'XYX', 'OK', '901 San Antonio Road',"
                + " 'MS UCUP02-555', 'Palo Alto', 'CA', '94303', 'USA',  '555-555-5555', 'DOGS')",
        "INSERT INTO signon VALUES('j2ee','j2ee')",
        "INSERT INTO profile(userid, langpref, favcategory, mylistopt, banneropt)"
                + " VALUES('j2ee','english','DOGS',1,1)",
        "INSERT INTO supplier(suppid, version, name, status, addr1, addr2, city, state, zip, phone)"
                + " VALUES (1,0,'XYZ Pets','AC','600 Avon Way','','Los Angeles','CA','94024','212-947-0797')",
        "INSERT INTO category(catid, name, descn) VALUES ('DOGS','Dogs','<image src=\"../images/dogs_icon.gif\"><font size=\"5\" color=\"blue\"> Dogs</font>')",
        "INSERT INTO product(productid, categoryid, name, descn) VALUES ('K9-BD-01','DOGS','Bulldog','<image src=\"../images/dog2.gif\">Friendly dog from England')",
        "INSERT INTO  item (itemid, productid, listprice, unitcost, supplierid, status, attr1) VALUES('EST-6','K9-BD-01',18.50,12.00,1,'P','Male Adult')",
        "INSERT INTO inventory (itemid, qty ) VALUES ('EST-6',10000)"
    };

    static {
        InputStream in = DatabaseLoader.class.getResourceAsStream(
                "/persistence-context-test.properties");
        try {
            properties.load(in);
        } catch (IOException e) {
            logger.error("An error occurred while loading database properties[{}]",
                    e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        logger.info("Loaded database properties[{}]", properties);
    }

    public void loadTestData() {
        try {
            Class.forName(properties.getProperty("database.driver"));
            Connection conn = DriverManager.getConnection(
                    properties.getProperty("database.url"),
                    properties.getProperty("database.user"),
                    properties.getProperty("database.pwd"));
            Statement st = conn.createStatement();

            for (String sql : data) {
                int count = st.executeUpdate(sql);
                logger.debug("Executed update with count[{}] for query[{}]", count, sql);
            }
        } catch (Exception e) {
            logger.error("An error occurred while loading test data[{}]", e.getMessage());
            e.printStackTrace();
        }
    }
}
