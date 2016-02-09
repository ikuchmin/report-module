package ru.osslabs.modules.report.fetchers.prc;

import ru.osslabs.commons.dataproviders.StatusesDataProvider;
import ru.osslabs.model.datasource.*;
import ru.osslabs.model.statuses.*;
import ru.osslabs.modules.report.functions.Fetcher;
import ru.osslabs.modules.report.prc.domain.Direction;
import ru.osslabs.modules.report.reflections.*;
import ru.osslabs.modules.report.types.Report;

import javax.inject.Inject;
import java.util.*;
import java.util.logging.Logger;

public class PrcDirectionsDataFetcher implements Fetcher<Report, List<Direction>> {

    @Inject
    private Logger log;

    @Inject
    private ExternalDataSource dataSource;

    @Inject
    private StatusesDataProvider statusesDataProvider;

    @Override
    @SuppressWarnings("unchecked")
    public List<Direction> compose(Report report) {
        List<DataObject> dataObjects = dataSource.getObjectList("direction", null);
        List<Direction> directions = new ArrayList<>();
        dataObjects.stream()
            .filter(Objects::nonNull)
            .forEach(dataObject -> {
                String statusCode = null;
                try {
                    ObjectStatus objectStatus = statusesDataProvider.getLatestStatus(dataObject.getMetaObject().getType().getMain(),
                        dataObject.getId());
                    ObjectStatusCode objectStatusCode = objectStatus.getStatus();
                    statusCode = objectStatusCode.getCode();
                } catch (Exception ex) {
                    log.severe(ex.getMessage());
                }

                Direction direction = null;
                try{
                    direction = (Direction) new ObjectMapper().readValue(dataObject,
                        new TypeReference<Direction>() {}, Object.class);
                }catch (Exception ex){
                    log.severe(ex.getMessage());
                }
                if (direction != null) {
                    direction.setStatusCode(statusCode);
                    directions.add(direction);
                }
            });
        return directions;
    }
}
