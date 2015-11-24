package ru.osslabs.modules.report.reflections;

import org.apache.poi.hssf.record.formula.functions.T;
import ru.osslabs.model.datasource.DataObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ikuchmin on 24.11.15.
 */
public class ListObjectFactory extends AbstractObjectFactory<List<?>> {

    public ListObjectFactory(ObjectRegistry objectRegistry) {
        super(objectRegistry);
    }

    @Override
    public List<?> build(DataObject dataObject, Class<? extends List<?>> type) {
        return new ArrayList<>();
    }

    @Override
    public List<?> build(List<DataObject> dataObjectList, Class<? extends List<?>> type) {
        return new ArrayList<>();
    }

    @Override
    public List<?> build(DataObject[] dataObjects, Class<? extends List<?>> type) {
        return new ArrayList<>();
    }
}
