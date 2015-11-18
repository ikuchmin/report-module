package ru.osslabs.modules.report.fetchers;

import javaslang.Tuple;
import javaslang.Tuple2;
import ru.osslabs.model.datasource.DataObject;
import ru.osslabs.model.datasource.ExternalDataSource;
import ru.osslabs.modules.report.decorators.BetweenDateReport;
import ru.osslabs.modules.report.functions.Fetcher;

import javax.inject.Inject;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toMap;

/**
 * Created by ikuchmin on 18.11.15.
 */
public class SPUSubServicesDataFetcher implements Fetcher<BetweenDateReport, Stream<Tuple2<String, Integer>>> {

    @Inject
    ExternalDataSource dataSource;

    @Override
    @SuppressWarnings("unchecked")
    public Stream<Tuple2<String, Integer>> compose(BetweenDateReport report) {
        Integer serviceId = 4023;

        List<DataObject> subServices = (List<DataObject>) dataSource.getObject("services", serviceId).getFields().get("Servicecommunication").getValue();

        // TODO: Replace stream() on parallel execution after CMDBuildWrapperWS is synchronized. Ticket: #9676
        Map<String, Integer> countInstanceObjects = cmdClasses.stream()
                .collect(toMap(
                        (cmdClass) -> cmdClass,
                        (cmdClass) -> dataSource.getObjectList(cmdClass, null).size())); // Collect is used because in return iterate on cmdClasses

        // TODO: Replace stream() on parallel execution after CMDBuildWrapperWS is synchronized. Ticket: #9676
        Map<String, String> descriptionClasses = cmdClasses.stream()
                .collect(toMap(
                        (cmdClass) -> cmdClass,
                        (cmdClass) -> dataSource.getObjectType(cmdClass).getName()));

        return cmdClasses.stream().map((cmdClass) ->
                Tuple.of(descriptionClasses.get(cmdClass), countInstanceObjects.get(cmdClass)));
    }
}
