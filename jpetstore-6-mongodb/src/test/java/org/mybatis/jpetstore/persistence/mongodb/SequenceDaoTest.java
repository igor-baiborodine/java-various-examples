package org.mybatis.jpetstore.persistence.mongodb;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import org.junit.Test;
import org.mybatis.jpetstore.domain.Sequence;
import org.springframework.beans.factory.annotation.Autowired;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

/**
 * JUnit based unit tests for the {@link SequenceDao} class.
 *
 * @author Igor Baiborodine
 */
public class SequenceDaoTest extends AbstractBaseDaoTest {

    @Autowired
    private SequenceDao sequenceDao;

    @Override
    public String getCollectionName() {
        return sequenceDao.getCollectionName();
    }

    @Test
    public void getSequence_shouldFindSequenceWithOneInNextIdForNonExistingSequence() {

        DBCursor cursor = collection.find(new BasicDBObject("name", "order"));
        assertThat(cursor.size(), is(0));

        Sequence sequence = sequenceDao.getSequence(new Sequence("order", -1));
        assertThat(sequence.getNextId(), is(1));
    }

    @Test
    public void getSequence_shouldFindSequenceWithNextIdForExistingSequence() {

        Sequence existingSequence = new Sequence("order", 10);
        insertSequence(existingSequence);

        DBCursor cursor = collection.find(new BasicDBObject("name", "order"));
        assertThat(cursor.size(), is(1));

        Sequence sequence = sequenceDao.getSequence(new Sequence("order", -1));
        assertThat(sequence.getNextId(), is(10));
    }

    private void insertSequence(final Sequence sequence) {

        collection.insert(sequence.toDBObject());

        DBObject sequenceObj = collection.findOne(new BasicDBObject("_id", sequence.getName()));
        assertThat("Cannot find sequence with id[" + sequence.getName() + "]", sequenceObj, notNullValue());
    }

}
