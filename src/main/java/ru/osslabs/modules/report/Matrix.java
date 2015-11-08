package ru.osslabs.modules.report;

import javaslang.Function3;
import ru.osslabs.modules.report.functions.Consumer3;

import java.util.List;

/**
 * Created by ikuchmin on 06.11.15.
 */
public class Matrix<T> {
    private final List<List<T>> data;

    private Matrix(List<List<T>> data) {
        this.data = data;
    }

    public static <T> Matrix<T> of(List<List<T>> data) {
        return new Matrix<T>(data);
    }

    public Matrix<T> traversal(Consumer3<Integer, Integer, T> consumer) {
        for (int i = 0; i < data.size(); i++) {
            List<T> col = data.get(i);
            for (int j = 0; j < col.size(); j++) {
                consumer.accept(i, j, col.get(j));
            }
        }

        return this;
    }

    public Matrix<T> matrixMap(Function3<Integer, Integer, T, T> func) {
        for (int i = 0; i < data.size(); i++) {
            List<T> col = data.get(i);
            for (int j = 0; j < col.size(); j++) {
                col.set(j, func.apply(i, j, col.get(j)));
            }
        }
        return this;
    }
}
