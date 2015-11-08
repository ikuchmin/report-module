package ru.osslabs.modules.report;

import java.util.Objects;
import java.util.function.Supplier;

/**
 * Created by ikuchmin on 06.11.15.
 */
public class ReportUtils {
    public static <T> T objectNotNull(Supplier<T> main, Supplier<T> def) {
        Objects.requireNonNull(main);
        Objects.requireNonNull(def);

        T res = main.get();
        if (res == null)
            res = def.get();
        return res;
    }
}
