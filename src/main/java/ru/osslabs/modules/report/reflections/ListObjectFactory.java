package ru.osslabs.modules.report.reflections;

import org.apache.poi.hssf.record.formula.functions.T;
import ru.osslabs.model.datasource.DataObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ikuchmin on 24.11.15.
 */
public class ListObjectFactory<T> extends AbstractObjectFactory<List<T>> {

    public ListObjectFactory(ObjectRegistry objectRegistry) {
        super(objectRegistry);
    }

    @Override
    public List<T> build(DataObject dataObject, Class<? extends List<T>> type) {
        return null;
    }

    @Override
    public List<T> build(List<DataObject> dataObjectList, Class<? extends List<T>> type) {
        // get type generic
//        return dataObjectList.stream()
//                .map();
        return null;
    }

    @Override
    public List<T> build(DataObject[] dataObjects, Class<? extends List<T>> type) {
        return null;
    }

    @Override
    public List<T> build(Object dataObjects, Class<? extends List<T>> type) {
        return null;
    }
}
