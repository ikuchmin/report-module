package ru.osslabs.modules.report.fetchers.isui;

import javaslang.control.*;
import ru.osslabs.model.datasource.ExternalDataSource;
import ru.osslabs.modules.report.functions.Fetcher;
import ru.osslabs.modules.report.isui.domain.Incident;
import ru.osslabs.modules.report.reflections.*;
import ru.osslabs.modules.report.spu.ServiceIdReport;

import javax.inject.Inject;
import java.util.logging.Logger;

/**
 * Created by ikuchmin on 18.11.15.
 */
public class IsuiIncidentDataFetcher implements Fetcher<ServiceIdReport, Option<Incident>> {

    @Inject
    private Logger log;

    @Inject
    private ExternalDataSource dataSource;

    @Override
    @SuppressWarnings("unchecked")
    public Option<Incident> compose(ServiceIdReport report) {
        String serviceId = report.getServiceId();
        log.info(() -> String.format("Data for compose function %s was started. ServiceId: %s", IsuiIncidentDataFetcher.class, report.getServiceId()));
        return Try.of(()->dataSource.getObject("Incidents", serviceId))
                .map(dataObject->(Incident) new ObjectMapper().readValue(dataObject, new TypeReference<Incident>() {
                }, Object.class))
                .onFailure(e -> log.warning(() -> String.format("Incident with id %s not found. Message: %s",
                        report.getServiceId(), e.getMessage())))
                .toOption();
    }
}
