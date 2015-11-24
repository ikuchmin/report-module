package ru.osslabs.modules.report.reflections;

import org.apache.poi.hssf.record.formula.functions.T;
import ru.osslabs.model.datasource.DataObject;
import ru.osslabs.modules.report.domain.spu.SubServices;

import java.util.List;
import java.util.Map;
import java.util.function.Function;

/**
 * Created by ikuchmin on 23.11.15.
 */
public interface ObjectFactory<T> {

    T build(DataObject dataObject, Class<? extends T> type);

    T build(List<DataObject> dataObjectList, Class<? extends T> type);

    T build(DataObject[] dataObjects, Class<? extends T> type);

    T build(Object dataObjects, Class<? extends T> type);
}
