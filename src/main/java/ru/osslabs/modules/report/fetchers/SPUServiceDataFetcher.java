package ru.osslabs.modules.report.fetchers;

import javaslang.control.Option;
import javaslang.control.Try;
import ru.osslabs.model.datasource.DataObject;
import ru.osslabs.model.datasource.ExternalDataSource;
import ru.osslabs.modules.report.functions.Fetcher;
import ru.osslabs.modules.report.reflections.ObjectMapper;
import ru.osslabs.modules.report.reflections.TypeReference;
import ru.osslabs.modules.report.spu.ServiceIdReport;
import ru.osslabs.modules.report.spu.domain.Service;
import ru.osslabs.modules.report.spu.domain.SubServices;

import javax.inject.Inject;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Stream;

/**
 * Created by ikuchmin on 18.11.15.
 */
public class SPUServiceDataFetcher implements Fetcher<ServiceIdReport, Option<Service>> {

    @Inject
    private Logger log;

    @Inject
    private ExternalDataSource dataSource;

    @Override
    @SuppressWarnings("unchecked")
    public Option<Service> compose(ServiceIdReport report) {
        String serviceId = report.getServiceId();
        log.info(() -> String.format("Data for compose function %s was started. ServiceId: %s", SPUServiceDataFetcher.class, report.getServiceId()));
        return Try.of(()->dataSource.getObject("services", serviceId))
                .map(dataObject->(Service) new ObjectMapper().readValue(dataObject, new TypeReference<Service>() {
                }, Object.class))
                .onFailure(e -> log.warning(() -> String.format("Service with id %s not found. Message: %s",
                        report.getServiceId(), e.getMessage())))
                .toOption();
    }
}
