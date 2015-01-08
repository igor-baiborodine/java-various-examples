package org.mybatis.jpetstore.persistence.jpa;

import org.mybatis.jpetstore.domain.Sequence;
import org.mybatis.jpetstore.persistence.SequenceMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import javax.annotation.Nonnull;

import static com.google.common.base.Preconditions.checkNotNull;
import static org.mybatis.jpetstore.domain.Domain.ORDER;

/**
 * @author Igor Baiborodine
 */
@Repository
public class SequenceBasePersistence
        extends AbstractBasePersistence<Sequence, String>
        implements SequenceMapper {

    private Logger logger = LoggerFactory.getLogger(SequenceBasePersistence.class);

    @Override
    public synchronized Sequence getNextSequence(@Nonnull final String id) {

        checkNotNull(id, "Argument[id] must not be null");

        Sequence sequence = read(id);
        if (sequence == null) {
            sequence = create(new Sequence(ORDER, 1));
        } else {
            sequence.setIntNextId(sequence.getIntNextId() + 1);
            sequence = update(sequence);
        }
        logger.info("Found sequence: {}", sequence);

        return sequence;
    }

}
