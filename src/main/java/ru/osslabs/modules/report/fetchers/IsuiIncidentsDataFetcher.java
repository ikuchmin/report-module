package ru.osslabs.modules.report.fetchers;

import ru.osslabs.model.datasource.*;
import ru.osslabs.modules.report.functions.Fetcher;
import ru.osslabs.modules.report.isui.domain.Incident;
import ru.osslabs.modules.report.reflections.*;
import ru.osslabs.modules.report.types.Report;

import javax.inject.Inject;
import java.util.*;
import java.util.logging.Logger;

/**
 * Created by ikuchmin on 18.11.15.
 */
public class IsuiIncidentsDataFetcher implements Fetcher<Report, List<Incident>> {

    @Inject
    private Logger log;

    @Inject
    private ExternalDataSource dataSource;

    @Override
    @SuppressWarnings("unchecked")
    public List<Incident> compose(Report report) {
        List<DataObject> dataObjects = dataSource.getObjectList("Incidents", null);
        List<Incident> incidents = new ArrayList<>();
        dataObjects.stream()
            .filter(Objects::nonNull)
            .forEach(dataObject -> {
                Incident incident = null;
                try{
                    incident = (Incident) new ObjectMapper().readValue(dataObject,
                        new TypeReference<Incident>() {}, Object.class);
                }catch (Exception ex){
                    log.severe(ex.getMessage());
                }
                if (incident != null) {
                    incidents.add(incident);
                }
            });
        return incidents;
    }
}
