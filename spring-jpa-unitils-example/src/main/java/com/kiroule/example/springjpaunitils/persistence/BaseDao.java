package com.kiroule.example.springjpaunitils.persistence;

import java.io.Serializable;

/**
 * @author Igor Baiborodine
 */
public interface BaseDao<T, ID extends Serializable> {
    T create(T t);
    T read(ID id);
    T update(T t);
    void delete(ID id);
}