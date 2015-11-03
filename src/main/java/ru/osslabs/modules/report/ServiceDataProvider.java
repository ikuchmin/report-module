package ru.osslabs.modules.report;

import ru.osslabs.modules.report.domain.Service;
import ru.osslabs.modules.report.functions.Fetcher;
import ru.osslabs.modules.report.functions.Transformer;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Stream;

/**
 * Created by ikuchmin on 02.11.15.
 */
public class ServiceDataProvider {

    public static Fetcher<Report, Stream<Service>> fromStatic() {
        return (report) -> {
            List<Service> services = new ArrayList<>();

            services.add(
                    new Service(0)
                            .addQualityRating("Тестовый рейтинг 1 в услуге 0").addQualityRating("Тестовый рейтинг 2 в услуге 0")
                            .addSubService("Тестовая подуслуга 1 в услуге 0").addSubService("Тестовая подуслуга 2 в услуге 0"));
            services.add(
                    new Service(1)
                            .addQualityRating("Тестовый рейтинг 1 в услуге 1").addQualityRating("Тестовый рейтинг 2 в услуге 1")
                            .addSubService("Тестовая подуслуга 1 в услуге 1").addSubService("Тестовая подуслуга 2 в услуге 1"));
            services.add(
                    new Service(2)
                            .addSubService("Тестовая подуслуга 1 в услуге 1"));
            return services.stream();
        };
    }

    public static Transformer<Report, Stream<Service>, Stream<Service>> addCommonSubService() {
        return (data, report) ->
                data.map(service -> service.addSubService("Общая подуслуга"));
    }

    public static Transformer<Report, Stream<Service>, Integer> addCommonSubService2() {
        return (data) ->
                data.map(service -> service.addSubService("Общая подуслуга"));
    }
}
//    public static Collection<Service> fromDB() {
//        return null;
//    }
//
//    public Collection<Service> fromLocalStorage(T data) {
//        return null;
//    }
}
