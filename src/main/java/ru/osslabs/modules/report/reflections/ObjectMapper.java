package ru.osslabs.modules.report.reflections;

import javaslang.Function2;
import javaslang.control.Option;
import javaslang.control.Try;
import ru.osslabs.model.datasource.DataObject;
import ru.osslabs.model.datasource.DataObjectField;
import ru.osslabs.model.datasource.IData;
import ru.osslabs.modules.report.CMDBuildField;
import ru.osslabs.modules.report.domain.spu.SubServices;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.*;
import java.util.function.Function;

import static ru.osslabs.modules.report.reflections.ObjectUtils.actualFieldName;

/**
 * Created by ikuchmin on 23.11.15.
 */
public class ObjectMapper {
//    private Map<Class<?>, Function2<DataObjectField, Class<?>, ?>> factoryMethods;

    private ObjectRegistry objectRegistry;

    public ObjectMapper() {
        objectRegistry = new ObjectRegistryImpl();
//        factoryMethods = new HashMap<>();
//        init();
    }

//    protected synchronized void init() {
//        factoryMethods.put(Object.class, (obj, type) -> Option.of(obj.getValue()).ifDefined(() -> new ObjectFactoryImpl(objectRegistry).build(obj.getValue(), type), () -> null));
//        factoryMethods.put(Integer.class, (obj, type) -> Option.of(obj.getValue()).ifDefined(() -> Integer.valueOf((String) obj.getValue()), () -> null));
//        factoryMethods.put(String.class, (obj, type) -> Option.of(obj.getValue()).ifDefined(() -> (String) obj.getValue(), () -> null)); // You shouldn't remove cast to String
//        factoryMethods.put(Boolean.class, (obj, type) -> Option.of(obj.getValue()).ifDefined(() -> Boolean.valueOf((String) obj.getValue()), () -> null));
//        factoryMethods.put(List.class, this::buildList);
//    }

//    private <T> List<T> buildList(DataObjectField obj, Class<T> type) {
//        return null;
//    }

    /**
     * TODO: Temp decision
     *
     * @param dataObject
     * @param clazz
     * @param <T>
     * @return
     */
    public <T> Option<T> readValue(IData dataObject, Class<? extends T> clazz, Class<T> cls) {
        Objects.requireNonNull(dataObject, "DataObject wouldn't null");
        return Option.of(objectRegistry.dispatch(cls))
                .flatMap((fn) -> fn.apply(dataObject, clazz));
    }

    /**
     * @param dataObject
     * @param clazz
     * @param <T>
     * @return
     * @throws NoSuchElementException
     */
    public <T> T readValue(DataObject dataObject, Class<? extends T> clazz, Class<T> cls) {
        Objects.requireNonNull(dataObject, "DataObject wouldn't null");
        return Option.of(objectRegistry.dispatch(cls))
                .flatMap((fn) -> fn.apply(dataObject, clazz)).get();
    }


    public <T> Option<T> readValue(IData dataObject, Class<? extends T> clazz) {
        Objects.requireNonNull(dataObject, "DataObject wouldn't null");
        return objectRegistry.dispatch(clazz).apply(dataObject, clazz);
    }


//    public <T> T readValue(DataObjectField dataObject, Class<T> clazz) {
//
//        T instance;
//        try {
//            instance = clazz.getConstructor().newInstance();
//        } catch (NoSuchMethodException e) {
//            throw new RuntimeException("Constructor without arguments not found", e);
//        } catch (IllegalAccessException | InstantiationException | InvocationTargetException e) {
//            throw new RuntimeException("Instance don't get from constructor", e);
//        }
//
//        for (Field field : clazz.getDeclaredFields()) {
//
//            String fieldName;
//            if (field.isAnnotationPresent(CMDBuildField.class)) {
//                fieldName = field.getAnnotation(CMDBuildField.class).name();
//            } else {
//                fieldName = field.getName();
//            }
//
//            // TODO: This code need to be refactoring
//            Object value = factoryMethods.get(field.getType())
//                    .apply(dataObject.getFields().get(fieldName));
//
////            DataObjectField dataObjectField = dataObject.getFields().get(fieldName);
////            if (dataObjectField.getValue() != null) {
////                if (field.getType().isAssignableFrom(List.class)) {
////                    value = createCollection(dataObject, fieldName);
////                } else if (field.getType().isAssignableFrom(Integer.class)) {
////                    value = Integer.valueOf((String) dataObjectField.getValue());
////                } else if (field.getType().isAssignableFrom(Boolean.class)) {
////                    value = Boolean.valueOf((String) dataObjectField.getValue());
////                } else if (field.getType().isAssignableFrom(String.class)) {
////                    if (dataObjectField.getMetaObject().getType().getMain().equals("LOOKUP")) { // TODO: Возможно lookup  нужно выделить в виде типа с параметризацией
////                        value = dataObjectField.getValueLabel();
////                    } else {
////                        value = dataObjectField.getValue();
////                    }
////                } else {
////                    value = dataObject.getFields().get(fieldName).getValue();
////                }
//
//            field.setAccessible(true);
//        }
//
//        return instance;
//    }

//    private <T> T buildInstance(DataObject data, Class<T> cls) {
//        T instance;
//        try {
//            instance = cls.getConstructor().newInstance();
//        } catch (NoSuchMethodException e) {
//            throw new RuntimeException("Constructor without arguments not found", e);
//        } catch (IllegalAccessException | InstantiationException | InvocationTargetException e) {
//            throw new RuntimeException("Instance don't get from constructor", e);
//        }
//
//        for (Field field : cls.getDeclaredFields()) {
//            field.setAccessible(true);
//            Option.of(factoryMethods.get(field.getType())) // TODO: При поиске метода построения объекта необходимо использовать его иерархию. По аналогии с бинами
//                    .map((func) -> func.apply(data.getFields().get(actualFieldName(field)), field.getType()))
//                    .peek((val) -> Try.run(() -> field.set(instance, val)).onFailure(e -> {
//                        throw new RuntimeException("Field with name " + field.getName() + " isn't set", e);
//                    }));
//        }
//        return instance;
//    }

//    private <T> Collection<T> createCollection(DataObject dataObject, String fieldName) {
//        return null;
//    }

//    @SuppressWarnings("unchecked")
//    private static <T, R> R cast(T val, Class<R> cls) {
//        return (R) val;
//    }
}
