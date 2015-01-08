package org.mybatis.jpetstore.persistence.jpa;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mybatis.jpetstore.domain.Domain;
import org.mybatis.jpetstore.domain.Sequence;
import org.springframework.beans.factory.annotation.Autowired;

import static org.hamcrest.CoreMatchers.is;
import static org.mybatis.jpetstore.domain.Domain.ORDER;

/**
 * @author Igor Baiborodine
 */
public class SequenceBasePersistenceTest extends AbstractBasePersistenceTest {

    @Autowired
    private SequenceBasePersistence persistence;

    @Before
    public void setUp() throws Exception {

        cleanDomain(Domain.SEQUENCE);
    }

    @Test
    public void getNextSequence_shouldFindSequenceWithOneInNextIdForNonExistingSequence() {

        Sequence sequence = persistence.getNextSequence(ORDER.toString());
        Assert.assertThat(sequence.getIntNextId(), is(1));
    }

    @Test
    public void getNextSequence_shouldFindSequenceWithNextIdIncreasedByOneForExistingSequence() {

        Sequence existingSequence = new Sequence(ORDER, 10);
        persistence.create(existingSequence);

        Sequence sequence = persistence.getNextSequence(existingSequence.getName());
        Assert.assertThat(sequence.getIntNextId(), is(11));
    }

}
