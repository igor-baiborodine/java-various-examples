/*
 *    Copyright 2010-2013 the original author or authors.
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package org.mybatis.jpetstore.domain;

import com.google.common.base.Objects;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

import javax.annotation.Nullable;
import java.io.Serializable;

/**
 * @author Igor Baiborodine
 *
 */
public class Sequence
        extends AbstractBaseDomain<String>
        implements Serializable {

    private String name;
    private Integer nextId;

    public Sequence() {}

    public Sequence(String name, int nextId) {
        this.name = name;
        this.nextId = nextId;
    }

    @Override
    public String getId() {
        return name;
    }

    @Override
    public DBObject toDBObject() {

        BasicDBObject sequenceObj = new BasicDBObject();
        appendTo(sequenceObj, "_id", getName());
        appendTo(sequenceObj, "name", getName());
        appendTo(sequenceObj, "next_id", getNextId());

        return sequenceObj;
    }

    public static Sequence fromDBObject(@Nullable final BasicDBObject sequenceObj) {

        Sequence sequence = null;

        if (sequenceObj != null) {
            String name = sequenceObj.getString("name");
            Integer nextId = sequenceObj.getInt("next_id");
            sequence = new Sequence(name, nextId);
        }
        return sequence;
    }

    @Override
    public boolean equals(final Object o) {

        if (o == this) {
            return true;
        }
        if (! (o instanceof Sequence)) {
            return false;
        }
        Sequence that = (Sequence) o;
        return Objects.equal(this.getName(), that.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getName());
    }

    @Override
    public String toString() {

        return Objects.toStringHelper(this)
                .add("name", name)
                .add("nextId", nextId)
                .toString();
    }


    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public Integer getNextId() {
        return nextId;
    }

    public void setNextId(final Integer nextId) {
        this.nextId = nextId;
    }

}
