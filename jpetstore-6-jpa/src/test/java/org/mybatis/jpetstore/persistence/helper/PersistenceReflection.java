package org.mybatis.jpetstore.persistence.helper;

import org.mybatis.jpetstore.domain.LineItem;
import org.mybatis.jpetstore.domain.LineItemID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.Id;
import javax.persistence.Transient;
import java.lang.reflect.Field;
import java.util.Arrays;

import static java.lang.reflect.Modifier.isTransient;
import static org.junit.Assert.assertEquals;

public class PersistenceReflection {
    private static Logger logger = LoggerFactory.getLogger(PersistenceReflection.class);

    public static Object idOf(Object entity) {
        if (entity instanceof LineItem) {
            return new LineItemID(((LineItem) entity).getOrderId(),
                    ((LineItem) entity).getLineNumber());
        }
        for (Class<?> c = entity.getClass(); c != Object.class; c = c.getSuperclass()) {
            for (Field field : c.getDeclaredFields()) {
                if (field.isAnnotationPresent(Id.class)) {
                    return fieldValue(entity, field);
                }
            }
        }

        throw new IllegalArgumentException(entity + " does not have an entity id");
    }

    public static void assertHaveSamePersistentFields(Object e1, Object e2) {
        assertHaveSamePersistentFields(e1, e2, new String[]{});
    }

    public static void assertHaveSamePersistentFields(
            Object e1, Object e2, String...excludedFields) {
        logger.debug("Object 1[{}]", e1);
        logger.debug("Object 2[{}]", e2);
        logger.debug("Excluded fields[{}]", Arrays.toString(excludedFields));
        for (Class<?> c = e1.getClass(); c != Object.class; c = c.getSuperclass()) {
            for (Field field : c.getDeclaredFields()) {
                if (Arrays.asList(excludedFields).contains(field.getName())) {
                    continue;
                }
                if (!isTransient(field.getModifiers())
                        && !field.isAnnotationPresent(Transient.class)) {
                    assertEquals(c.getSimpleName() + "."
                            + field.getName(), fieldValue(e1, field), fieldValue(e2, field));
                }
            }
        }
    }

    private static Object fieldValue(Object entity, Field field) throws Error {
        field.setAccessible(true);
        try {
            return field.get(entity);
        } catch (IllegalAccessException e) {
            throw new Error("could not access accessible field " + field);
        }
    }
}
