package org.mybatis.jpetstore.persistence.helper;

/**
 * Reference: https://github.com/npryce/goos-code-examples
 */
public abstract class QueryUnitOfWork<T> implements UnitOfWork {
    public T result;

    public void work() throws Exception {
        result = query();
    }

    public abstract T query() throws Exception;
}
