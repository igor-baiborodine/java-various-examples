package org.mybatis.jpetstore.util;

import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;

/**
 * @author Igor Baiborodine
 */
public class InitialDataLoader {

    @Autowired
    InitialDataLoadUtils loadUtils;

    @PostConstruct
    public void execute() {

        loadUtils.importInitialDataToMongo();
    }

}
