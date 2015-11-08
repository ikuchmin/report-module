package ru.osslabs.modules.report.fetchers;

import ru.osslabs.modules.report.functions.Fetcher;
import ru.osslabs.modules.report.types.Report;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by ikuchmin on 06.11.15.
 */
public class MockDataFetchers {
    public static Fetcher<Report, List<List<Double>>> matrixFiveOnTwentyFive() {
        return (report) -> {
            Random rnd = new Random();
            List<List<Double>> sourceData = new ArrayList<>();
            for (int i = 0; i < 25; i++) {
                List<Double> innerList = new ArrayList<>();
                for (int j = 0; j < 4; j++)
                    innerList.add(rnd.nextDouble());

                sourceData.add(innerList);
            }
            return sourceData;
        };
    }
}
