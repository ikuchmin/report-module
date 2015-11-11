package ru.osslabs.modules.report.transformers;

import ru.osslabs.modules.report.Matrix;
import ru.osslabs.modules.report.types.Report;

import java.util.List;

/**
 * Created by ikuchmin on 08.11.15.
 */
public class MatrixTransformers {
    public static <T extends Report> Matrix<Double> fromListDouble(T report, List<List<Double>> data) {
        return Matrix.of(data)
                .matrixMap((row, col, el) -> el * 100);
    }
}
