package ru.osslabs.modules.report.reflections;

import com.fasterxml.jackson.core.type.ResolvedType;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * This generic abstract class is used for obtaining full generics type information
 * by sub-classing.
 * Class is based on ideas from
 * <a href="http://gafter.blogspot.com/2006/12/super-type-tokens.html"
 * >http://gafter.blogspot.com/2006/12/super-type-tokens.html</a>,
 * <p>
 * Usage is by sub-classing: here is one way to instantiate reference
 * to generic type <code>List&lt;Integer&gt;</code>:
 * <pre>
 *  ReferenceSupplier ref = new ReferenceSupplier&lt;List&lt;Integer&gt;&gt;() { };
 * </pre>
 * which can be passed to methods that accept ReferenceSupplier, or resolved
 * using <code>TypeFactory</code> to obtain {@link ResolvedType}.
 */
public abstract class TypeReference<T> implements ReferenceSupplier<T> {
    protected final Type type;
//    protected Constructor<?> constructor;

    protected TypeReference() {
        Type superClass = getClass().getGenericSuperclass();
        if (superClass instanceof Class<?>) { // sanity check, should never happen
            throw new IllegalArgumentException("Internal error: ReferenceSupplier constructed without actual type information");
        }

        type = ((ParameterizedType) superClass).getActualTypeArguments()[0];
    }

//    @SuppressWarnings("unchecked")
//    public synchronized T newInstance()
//            throws NoSuchMethodException, IllegalAccessException,
//            InvocationTargetException, InstantiationException {
//        if (constructor == null) {
//            Class<?> rawType = type instanceof Class<?>
//                    ? (Class<?>) type
//                    : (Class<?>) ((ParameterizedType) type).getRawType();
//            constructor = rawType.getConstructor();
//        }
//        return (T) constructor.newInstance();
//    }
    @Override
    public Type getType() {
        return type;
    }


    /**
     * This fabric should use for types which it isn't parametrized
     * @param cls
     * @param <T>
     * @return
     */
    public static <T> ReferenceSupplier<T> referenceFabric(Class<T> cls) {
        return () -> cls;
    }

    /**
     * This fabric should use for types which it is parametrized
     * @param type
     * @param <T>
     * @return
     */
    public static <T> ReferenceSupplier<T> referenceFabric(Type type) {
        return () -> type;
    }
}

