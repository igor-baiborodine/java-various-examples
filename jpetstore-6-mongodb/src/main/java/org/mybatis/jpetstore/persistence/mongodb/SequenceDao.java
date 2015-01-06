package org.mybatis.jpetstore.persistence.mongodb;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import org.mybatis.jpetstore.domain.Sequence;
import org.mybatis.jpetstore.persistence.SequenceMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * @author Igor Baiborodine
 */
public class SequenceDao
        extends AbstractBaseDao<Sequence, String>
        implements SequenceMapper {

    private static final Logger logger = LoggerFactory.getLogger(SequenceDao.class);

    public SequenceDao(final String collectionName) {
        super(collectionName);
    }

    public Sequence getSequence(final Sequence seq) {

        checkNotNull(seq, "Argument[seq] must not be null");

        DBObject sequenceObj = collection.findOne(new BasicDBObject("_id", seq.getName()));
        Sequence sequence = Sequence.fromDBObject((BasicDBObject) sequenceObj);
        if (sequence == null) {
            sequence = new Sequence(seq.getName(), 1);
        }
        logger.info("Found sequence: {}", sequence);

        return sequence;
    }

    public void updateSequence(final Sequence seq) {

        checkNotNull(seq, "Argument[seq] must not be null");

        BasicDBObject query = new BasicDBObject("_id", seq.getName());
        BasicDBObject update = new BasicDBObject("$set", new BasicDBObject("next_id", seq.getNextId()));
        collection.update(query, update);
        logger.info("Updated sequence: {}", seq);
    }

}
