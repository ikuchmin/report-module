package ru.osslabs.modules.report.impls.sed;

import javaslang.Tuple;
import javaslang.Tuple2;
import ru.osslabs.model.datasource.ExternalDataSource;
import ru.osslabs.model.datasource.MetaObject;
import ru.osslabs.model.smartforms.MetaConstants.FieldTypes.SpecificationType;
import ru.osslabs.modules.report.decorators.BetweenDateReport;
import ru.osslabs.modules.report.functions.Fetcher;

import javax.inject.Inject;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
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

        // TODO: Replace stream() on parallelStream() after CMDBuildWrapperWS is synchronized. Ticket: #9676
        Map<String, Integer> countInstanceObjects = cmdClasses.stream()
                .map((cmdClass) -> Tuple.of(
                        cmdClass, dataSource.getObjectList(cmdClass, null).size()))
                .collect(toMap(
                        (tuplObj) -> tuplObj._1,
                        (tuplObj) -> tuplObj._2)); // Collect is used because in return iterate on cmdClasses

        // TODO: Replace stream() on parallelStream() after CMDBuildWrapperWS is synchronized. Ticket: #9676
        Map<String, String> descriptionClasses = cmdClasses.stream()
                .map((cmdClass) -> dataSource.getObjectType(cmdClass))
                .collect(toMap(
                        (metaObject -> metaObject.getType().getMain()),
                        MetaObject::getName));

        return cmdClasses.stream().map((cmdClass) ->
                Tuple.of(descriptionClasses.get(cmdClass), countInstanceObjects.get(cmdClass)));
    }
}
