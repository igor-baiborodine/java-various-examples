package org.mybatis.jpetstore.persistence.helper;

/**
 * Reference: https://github.com/npryce/goos-code-examples
 */
public interface UnitOfWork {
    void work() throws Exception;
}
