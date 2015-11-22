package ru.osslabs.modules.report.fetchers;

import ru.osslabs.model.datasource.DataObject;
import ru.osslabs.model.datasource.DataObjectField;
import ru.osslabs.model.datasource.ExternalDataSource;
import ru.osslabs.modules.report.decorators.BetweenDateReport;
import ru.osslabs.modules.report.domain.spu.SubService;
import ru.osslabs.modules.report.functions.Fetcher;

import javax.inject.Inject;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by ikuchmin on 18.11.15.
 */
public class SPUSubServicesDataFetcher implements Fetcher<BetweenDateReport, Stream<SubService>> {

    @Inject
    ExternalDataSource dataSource;

    @Override
    @SuppressWarnings("unchecked")
    public Stream<SubService> compose(BetweenDateReport report) {
        Integer serviceId = 4023;

        List<DataObject> subServices = (List<DataObject>) dataSource.getObject("services", serviceId).getFields().get("Servicecommunication").getValue();

        return subServices.stream().map(this::createObjectSubService);

//        // TODO: Replace stream() on parallel execution after CMDBuildWrapperWS is synchronized. Ticket: #9676
//        Map<String, Integer> countInstanceObjects = cmdClasses.stream()
//                .collect(toMap(
//                        (cmdClass) -> cmdClass,
//                        (cmdClass) -> dataSource.getObjectList(cmdClass, null).size())); // Collect is used because in return iterate on cmdClasses
//
//        // TODO: Replace stream() on parallel execution after CMDBuildWrapperWS is synchronized. Ticket: #9676
//        Map<String, String> descriptionClasses = cmdClasses.stream()
//                .collect(toMap(
//                        (cmdClass) -> cmdClass,
//                        (cmdClass) -> dataSource.getObjectType(cmdClass).getName()));
//
//        return cmdClasses.stream().map((cmdClass) ->
//                Tuple.of(descriptionClasses.get(cmdClass), countInstanceObjects.get(cmdClass)));
    }

    private SubService createObjectSubService(DataObject dataObject) {
        Map<String, DataObjectField> fields = dataObject.getFields();
        return new SubService(
                getFieldAndCast(fields, "namesubservice"),
                new SubService.PeriodSubService(
                        new SubService.Unit<>(
                                getFieldAndCast(fields, "periodsubservice"),
                                getFieldAndCast(fields, "FormPeriodSubservice")),
                        new SubService.Unit<>(
                                getFieldAndCast(fields, "PeriodSubservice_ExTerr"),
                                getFieldAndCast(fields, "FormPeriodSubservice_ExTer"))),
                createCollectionOnAttribute(getFieldAndCast(fields, "reject_noRecept"), "Description"),
                createCollectionOnAttribute(getFieldAndCast(fields, "rejection_noProv"), "Description"),
                createCollectionOnAttribute(getFieldAndCast(fields, "rejection_noAct"), "Description"),
                new SubService.Unit<>(
                        getFieldAndCast(fields, "suspension_days"),
                        getFieldAndCast(fields, "FormSuspension_days")),
                null,
                null,
                null,
                null);
    }

    private List<String> createCollectionOnAttribute(List<DataObject> collection, String field) {
        return collection.stream()
                .map(DataObject::getFields)
                .map((obj) -> this.<String>getFieldAndCast(obj, field))
                .collect(Collectors.toList());
    }




    private <R> R getFieldAndCast(Map<String, DataObjectField> fields, String value) {
        return cast(fields.get(value).getValue());
    }

    @SuppressWarnings("unchecked")
    private static <R> R cast(Object obj) {
        return (R) obj;
    }
}
