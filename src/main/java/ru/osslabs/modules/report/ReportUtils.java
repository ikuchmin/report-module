package ru.osslabs.modules.report;

import java.util.Objects;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

/**
 * Created by ikuchmin on 06.11.15.
 */
public class ReportUtils {
    public static <T> T objectNotNull(Predicate<T> predicate, Supplier<T> main, Supplier<T> def) {
        Objects.requireNonNull(main);
        Objects.requireNonNull(def);

        T res = main.get();
        if (predicate.test(res))
            res = def.get();
        return res;
    }

    public static <T> T objectNotNull(Supplier<T> main, Supplier<T> def) {
        return objectNotNull((mainRes) -> mainRes == null, main, def);
    }

    /**
     * Попробовать переопределить Supplier через Function
     * @param initValue
     * @param predicate
     * @param main
     * @param def
     * @param <T>
     * @param <R>
     * @return
     */
    public static <T, R> R objectNotNull(T initValue, Predicate<R> predicate, Function<T, R> main, Function<T, R> def) {
        Objects.requireNonNull(main);
        Objects.requireNonNull(def);

        R res = main.apply(initValue);
        if (predicate.test(res))
            res = def.apply(initValue);
        return res;
    }

    public static <T, R> R objectNotNull(T initValue, Function<T, R> main, Function<T, R> def) {
        return objectNotNull(initValue, (mainRes) -> mainRes == null, main, def);
    }
}
