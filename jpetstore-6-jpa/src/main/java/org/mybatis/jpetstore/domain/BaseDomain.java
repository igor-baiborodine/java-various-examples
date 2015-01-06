package org.mybatis.jpetstore.domain;

import javax.persistence.MappedSuperclass;
import javax.persistence.Version;
import java.io.Serializable;

/**
 * @author Igor Baiborodine
 */
@MappedSuperclass()
public class BaseDomain implements Serializable {
    @Version
    protected Long version;

    public Long getVersion() {
        return version;
    }
}
