package ru.osslabs.modules.report.fetchers;

import javaslang.control.Option;
import ru.osslabs.model.datasource.DataObject;
import ru.osslabs.model.datasource.DataObjectField;
import ru.osslabs.model.datasource.ExternalDataSource;
import ru.osslabs.model.datasource.IData;
import ru.osslabs.modules.report.reflections.ObjectMapper;
import ru.osslabs.modules.report.domain.spu.SubService2;
import ru.osslabs.modules.report.domain.spu.SubServices;
import ru.osslabs.modules.report.functions.Fetcher;
import ru.osslabs.modules.report.types.Report;

import javax.inject.Inject;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

/**
 * Created by ikuchmin on 18.11.15.
 */
public class SPUSubServicesDataFetcher implements Fetcher<Report, Stream<SubServices>> {

    @Inject
    ExternalDataSource dataSource;

    @Override
    @SuppressWarnings("unchecked")
    public Stream<SubServices> compose(Report report) {
        Integer serviceId = 2187;
        List<DataObject> subServices = (List<DataObject>)dataSource.getObject("services", serviceId).getFields().get("Servicecommunication").getValue();

        List<SubServices> objs = subServices.stream().map((dataObject) -> {
            DataObjectField dof = new DataObjectField();
            dof.setValue(dataObject);
            return new ObjectMapper().readValue(dataObject, SubServices.class, SubServices.class);
        }).collect(toList());
//        Object obj = new ObjectMapper().readValue(subServices, SubServices.class, Object.class);
//        List<SubServices> subServicesList = subServices.stream().map(e -> new ObjectMapper().readValue(e, SubServices.class)).collect(Collectors.toList());
//        Stream<SubServices> subServicesStream = subServices.stream().map(e -> new ObjectMapper().readValue(subServices.get(0), SubServices.class));
//        return subServicesStream;

        return null;
//        return subServices.stream().map(this::createObjectSubService);

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

    private SubService2 createObjectSubService(DataObject dataObject) {
        Map<String, DataObjectField> fields = dataObject.getFields();
        return new SubService2(
                getFieldAndCast(fields, "namesubservice"),
                new SubService2.PeriodSubService(
                        new SubService2.Unit<>(
                                getFieldAndCast(fields, "periodsubservice"),
                                getFieldAndCast(fields, "FormPeriodSubservice")),
                        new SubService2.Unit<>(
                                getFieldAndCast(fields, "PeriodSubservice_ExTerr"),
                                getFieldAndCast(fields, "FormPeriodSubservice_ExTer"))),
                createCollectionOnAttribute(getFieldAndCast(fields, "reject_noRecept"), "Description"),
                createCollectionOnAttribute(getFieldAndCast(fields, "rejection_noProv"), "Description"),
                createCollectionOnAttribute(getFieldAndCast(fields, "rejection_noAct"), "Description"),
                new SubService2.Unit<>(
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
                .collect(toList());
    }




    private <R> R getFieldAndCast(Map<String, DataObjectField> fields, String value) {
        return cast(fields.get(value).getValue());
    }

    @SuppressWarnings("unchecked")
    private static <R> R cast(Object obj) {
        return (R) obj;
    }
}
