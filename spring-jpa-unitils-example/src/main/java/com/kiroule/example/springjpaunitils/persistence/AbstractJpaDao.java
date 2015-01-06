package com.kiroule.example.springjpaunitils.persistence;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.io.Serializable;
import java.lang.reflect.ParameterizedType;

/**
 * @author Igor Baiborodine
 */
public abstract class AbstractJpaDao<T, ID extends Serializable>
        implements BaseDao<T, ID> {
    private static final Logger logger = LoggerFactory.getLogger(AbstractJpaDao.class);

    @PersistenceContext
    protected EntityManager entityManager;
    protected Class<T> entityClass;

    public AbstractJpaDao() {
        ParameterizedType genericSuperclass = (ParameterizedType) getClass()
                .getGenericSuperclass();
        entityClass = (Class<T>) genericSuperclass.getActualTypeArguments()[0];
    }

    @Override
    public T create(T t) {
        entityManager.persist(t);
        logger.debug("Created new entity[{}]", t);
        return t;
    }

    @Override
    public T read(ID id) {
        T read = entityManager.find(entityClass, id);
        logger.debug("Read entity[{}]", read);
        return read;
    }

    @Override
    public T update(T t) {
        T updated = entityManager.merge(t);
        logger.debug("Updated entity[{}]", updated);
        return updated;
    }

    @Override
    public void delete(ID id) {
        T deleted = read(id);
        entityManager.remove(deleted);
        logger.debug("Deleted entity[{}]", deleted);
    }

    public void flush() {
        entityManager.flush();
    }

    public void clear() {
        entityManager.clear();
    }

    public EntityManager getEntityManager() {
        return entityManager;
    }

    public void setEntityManager(final EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public Class<T> getEntityClass() {
        return entityClass;
    }
}
