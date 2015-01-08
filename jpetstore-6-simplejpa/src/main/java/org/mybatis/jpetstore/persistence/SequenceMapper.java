package org.mybatis.jpetstore.persistence;

import org.mybatis.jpetstore.domain.Sequence;

/**
 * @author Eduardo Macarron
 * @author Igor Baiborodine
 *
 */
public interface SequenceMapper {

    Sequence getNextSequence(String id);
}