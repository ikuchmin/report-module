package ru.osslabs.modules.report.fetchers;

import ru.osslabs.model.datasource.DataObject;
import ru.osslabs.model.datasource.DataObjectField;
import ru.osslabs.model.datasource.ExternalDataSource;
import ru.osslabs.modules.report.reflections.ObjectMapper;
import ru.osslabs.modules.report.domain.spu.SubService2;
import ru.osslabs.modules.report.domain.spu.SubServices;
import ru.osslabs.modules.report.functions.Fetcher;
import ru.osslabs.modules.report.reflections.ReferenceSupplier;
import ru.osslabs.modules.report.reflections.TypeReference;
import ru.osslabs.modules.report.types.Report;

import javax.inject.Inject;
import java.util.List;
import java.util.Map;
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
        String serviceId = "4293";
        List<DataObject> subServices = (List<DataObject>)dataSource.getObject("services", serviceId).getFields().get("Servicecommunication").getValue();

        return subServices.stream().map((dataObject) ->
                (SubServices) new ObjectMapper().readValue(dataObject, new TypeReference<SubServices>() {}, Object.class));
    }
}
