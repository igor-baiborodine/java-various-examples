package org.mybatis.jpetstore.domain.builder;

import javax.annotation.Nonnull;

/**
 * @author Igor Baiborodine
 */
public interface Builder<T> {
  @Nonnull
  T build();
}
