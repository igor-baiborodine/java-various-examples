package org.mybatis.jpetstore.persistence.jpa;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.io.Serializable;
import java.lang.reflect.ParameterizedType;

/**
 * @author Igor Baiborodine
 */
public abstract class AbstractBasePersistence<T, ID extends Serializable>
        implements BasePersistence<T, ID> {

    private static final Logger logger = LoggerFactory.getLogger(AbstractBasePersistence.class);

    @PersistenceContext
    protected EntityManager entityManager;
    protected Class<T> entityClass;

    public AbstractBasePersistence() {
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

    protected void flushContext() {
        entityManager.flush();
    }

    protected void clearContext() {
        entityManager.clear();
    }

    protected void detachEntity(T entity) {
        entityManager.detach(entity);
    }

    protected boolean contextContains(T entity) {
        return entityManager.contains(entity);
    }

    protected Query createNamedQuery(String queryName) {
        return entityManager.createNamedQuery(queryName);
    }
    
    protected EntityManager getEntityManager() {
        return entityManager;
    }

    protected void setEntityManager(final EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    protected Class<T> getEntityClass() {
        return entityClass;
    }
}
