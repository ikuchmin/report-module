package ru.osslabs.modules.report.reflections;

import ru.osslabs.modules.report.CMDBuildField;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * Created by ikuchmin on 24.11.15.
 */
public class ObjectUtils {
    public static String actualFieldName(Field field) {
        if (field.isAnnotationPresent(CMDBuildField.class)) {
            return field.getAnnotation(CMDBuildField.class).name();
        } else {
            return field.getName();
        }
    }

    public static Class<?> getRawType(ReferenceSupplier<?> typeRef) {
        Type type = typeRef.getType();
        return type instanceof Class<?>
                ? (Class<?>) type
                : (Class<?>) ((ParameterizedType) type).getRawType();
    }

    @SuppressWarnings("unchecked")
    public static <R> R cast(Object arg) {
        return (R) arg;
    }
}
