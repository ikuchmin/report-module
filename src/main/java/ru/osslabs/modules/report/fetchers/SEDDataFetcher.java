package ru.osslabs.modules.report.fetchers;

import javaslang.Tuple;
import javaslang.Tuple2;
import javaslang.concurrent.Future;
import ru.osslabs.model.datasource.ExternalDataSource;
import ru.osslabs.model.datasource.MetaObject;
import ru.osslabs.modules.report.decorators.BetweenDateReport;
import ru.osslabs.modules.report.functions.Fetcher;

import javax.inject.Inject;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toMap;

/**
 * Created by ikuchmin on 08.11.15.
 */
public class SEDDataFetcher implements Fetcher<BetweenDateReport, Stream<Tuple2<String, Integer>>> {

    @Inject
    ExternalDataSource dataSource;

    @Override
    public Stream<Tuple2<String, Integer>> compose(BetweenDateReport report) {
        List<String> cmdClasses = Arrays.asList("Claim", "Agreements", "Internal", "Inbox", "Outgoing", "ORD");

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
