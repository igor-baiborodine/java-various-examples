package org.mybatis.jpetstore.util;

import com.amazonaws.services.simpledb.AmazonSimpleDBClient;
import com.amazonaws.services.simpledb.model.BatchPutAttributesRequest;
import com.amazonaws.services.simpledb.model.ReplaceableAttribute;
import com.amazonaws.services.simpledb.model.ReplaceableItem;
import com.google.common.collect.Lists;
import com.spaceprogram.simplejpa.EntityManagerSimpleJPA;
import com.spaceprogram.simplejpa.util.AmazonSimpleDBUtil;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.mybatis.jpetstore.domain.Domain;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.URL;
import java.util.List;

/**
 * @author Igor Baiborodine
 */
@Component
public class InitialDataLoadUtils {

    private static final Logger logger = LoggerFactory.getLogger(InitialDataLoadUtils.class);

    private static final int BATCH_PUT_LIMIT = 25;
    @Autowired
    protected AmazonSimpleDBClient amazonSimpleDBClient;

    @Value("${persistenceUnitName}")
    protected String persistenceUnitName;

    public void importInitialDataToSimpleDB() {

        for (Domain domain : Domain.values()) {

            importInitialDataToSimpleDB(domain);
        }
    }

    public void importInitialDataToSimpleDB(Domain domain) {

            JSONArray jsonArray = readJSONFile(domain);
            if (jsonArray.size() > 0) {
                importDomain(domain, jsonArray);
            }
    }

    public JSONArray readJSONFile(final Domain domain) {

        logger.info("Read file for domain[{}]: starting...", domain);

        JSONArray result = new JSONArray();
        URL url = InitialDataLoadUtils.class.getResource("/dataload/" + domain + ".json");
        if (url == null) {
            logger.warn("File does not exist for domain[{}]", domain);
            return result;
        }
        String file = url.getFile();

        try {
            JSONObject jsonObj = (JSONObject) new JSONParser().parse(new FileReader(file));
            result = (JSONArray) jsonObj.get("Items");
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
        logger.info("Fetched [{}] JSON objects from file[{}]", result.size(), file);

        return result;
    }

    public void importDomain(final Domain domain, final JSONArray jsonArray) {

        logger.info("Import domain[{}]: starting...", domain);
        List<ReplaceableItem> items = Lists.newArrayListWithCapacity(jsonArray.size());
        BatchPutAttributesRequest request;
        int count = 0;

        jsonArrayLoop:
        for (Object o : jsonArray) {

            ReplaceableItem item = null;
            JSONObject jsonObject = (JSONObject) o;

            jsonObjectItemNameLoop:
            for (Object key : jsonObject.keySet()) {
                if ("itemName()".equals(key)) {
                    item = new ReplaceableItem((String) jsonObject.get(key));
                    break jsonObjectItemNameLoop;
                }
            }

            if (item == null) {
                logger.warn("Cannot initialize item for JSON object[" + jsonObject.toJSONString() + "]");
                break jsonArrayLoop;
            }

            jsonObjectAttributesLoop:
            for (Object key : jsonObject.keySet()) {
                if ("itemName()".equals(key)) {
                    continue jsonObjectAttributesLoop;
                }
                String value = (String) jsonObject.get(key);
                if (key.equals("listPrice") || key.equals("unitCost")) {
                    value = encodeBigDecimal(new BigDecimal(value));
                } else if (key.equals("quantity")) {
                    value = encodeInteger(new Integer(value));
                }
                item.withAttributes(new ReplaceableAttribute((String) key, value, true));
            }
            logger.info("Populated replaceable item[{}]", item);

            items.add(item);
            if (items.size() == BATCH_PUT_LIMIT) {
                request = new BatchPutAttributesRequest(getFullDomainName(domain), items);
                amazonSimpleDBClient.batchPutAttributes(request);
                count += items.size();
                items.clear();
            }
        }
        request = new BatchPutAttributesRequest(getFullDomainName(domain), items);
        amazonSimpleDBClient.batchPutAttributes(request);
        count += items.size();

        logger.info("Imported [{}] items to domain[{}]", count, domain);
    }

    private String getFullDomainName(Domain domain) {

        return persistenceUnitName + "-" + domain.toString();
    }

    private String encodeInteger(final Integer value) {

        return AmazonSimpleDBUtil.encodeRealNumberRange(new BigDecimal(value),
                AmazonSimpleDBUtil.LONG_DIGITS, EntityManagerSimpleJPA.OFFSET_VALUE);
    }

    private String encodeBigDecimal(final BigDecimal value) {

        return AmazonSimpleDBUtil.encodeRealNumberRange(value, AmazonSimpleDBUtil.LONG_DIGITS,
                AmazonSimpleDBUtil.LONG_DIGITS,EntityManagerSimpleJPA.OFFSET_VALUE);
    }

}
