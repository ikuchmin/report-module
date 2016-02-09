package ru.osslabs.modules.report.fetchers.spu;

import javaslang.control.Try;
import ru.osslabs.model.datasource.DataObject;
import ru.osslabs.model.datasource.ExternalDataSource;
import ru.osslabs.modules.report.reflections.ObjectMapper;
import ru.osslabs.modules.report.spu.ServiceIdReport;
import ru.osslabs.modules.report.spu.domain.SubServices;
import ru.osslabs.modules.report.functions.Fetcher;
import ru.osslabs.modules.report.reflections.TypeReference;

import javax.inject.Inject;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Stream;

/**
 * Created by ikuchmin on 18.11.15.
 */
public class SPUSubServicesDataFetcher implements Fetcher<ServiceIdReport, Stream<SubServices>> {

    @Inject
    private Logger log;

    @Inject
    private ExternalDataSource dataSource;

    @Override
    @SuppressWarnings("unchecked")
    public Stream<SubServices> compose(ServiceIdReport report) {
        log.info(() -> String.format("Data for compose function %s was started. ServiceId: %s", SPUSubServicesDataFetcher.class, report.getServiceId()));
        return Try.of(() -> dataSource.getObject("services", report.getServiceId()).getFields().get("Servicecommunication"))
                .onFailure(e -> log.warning(() -> String.format("Service with id $s not found or reference %s isn't. Message: %s",
                        report.getServiceId(), "Servicecommunication", e.getMessage())))
                .filter(v -> v != null)
                .onFailure(e -> log.warning(() -> String.format("Service with id $s not found or reference %s is null. Message: %s",
                        report.getServiceId(), "Servicecommunication", e.getMessage())))
                .map((dataObjectField) -> ((List<DataObject>)dataObjectField.getValue()).stream())
                .onFailure(e -> log.warning(() -> String.format("Information about subservice for serviceid %s is not avalilable. Message: %s",
                        report.getServiceId(), e.getMessage())))
                .map(ss -> ss.map((dataObject) ->
                        (SubServices) new ObjectMapper().readValue(dataObject, new TypeReference<SubServices>() {}, Object.class)))
                .onFailure(e -> log.warning(() -> String.format("Object subservice was not created. Message: %s", e.getMessage())))
                .recover(e -> Stream.empty())
                .get();

//        List<DataObject> subServices = (List<DataObject>)dataSource.getObject("services", serviceId).getFields().get("Servicecommunication").getValue();
//
//        return subServices.stream().map((dataObject) ->
//                (SubServices) new ObjectMapper().readValue(dataObject, new TypeReference<SubServices>() {}, Object.class));
    }
}
