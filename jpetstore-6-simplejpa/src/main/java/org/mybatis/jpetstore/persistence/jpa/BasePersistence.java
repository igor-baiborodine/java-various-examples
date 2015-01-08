package org.mybatis.jpetstore.persistence.jpa;

import java.io.Serializable;

/**
 * @author Igor Baiborodine
 */
public interface BasePersistence<T, ID extends Serializable> {
    T create(T t);
    T read(ID id);
    T update(T t);
    void delete(ID id);
}
