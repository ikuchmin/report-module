package ru.osslabs.modules.report;

import ru.osslabs.commons.dataproviders.ReportsDataProvider;
import ru.osslabs.model.datasource.DataObject;
import ru.osslabs.model.datasource.MetaObject;
import ru.osslabs.model.datasource.ObjectType;
import ru.osslabs.model.datasource.proxy.DataObjectProxyFactory;
import ru.osslabs.model.reports.ReportInfo;
import ru.osslabs.model.smartforms.MetaConstants;
import ru.osslabs.modules.report.types.Report;

import javax.ejb.ConcurrencyManagement;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.enterprise.context.Dependent;
import javax.faces.application.Application;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import java.io.OutputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static javax.ejb.ConcurrencyManagementType.BEAN;

/**
 * Created by ikuchmin on 09.11.15.
 * TODO: Поставить для всех Singletor контроль конкурентности EJB, не контейнера
 */
@Named("reportsDataProvider")
@Startup
@Singleton
@ConcurrencyManagement(BEAN)
@Dependent
public class ReportRegistryImpl implements ReportsDataProvider, ReportRegistry {

//    @Inject
//    private SEDDDunaevReportWrapper dunaevReportWrapper;

//    static final String REPORT_DIR = "/reports/juniprint/";

    private Map<Path, ReportWrapper<?>> reportWrappers;

    public ReportRegistryImpl() {
        reportWrappers = new ConcurrentHashMap<>();
    }

//    @PostConstruct
//    public void init() {
//        reportWrappers.put(dunaevReportWrapper.getReportPath(), dunaevReportWrapper);
//    }

    @Override
    public ReportInfo getReportInfo(String s) {
        return null;
    }

    @Override
    public Collection<ReportWrapper<?>> getReports() {
        return reportWrappers.values();
    }

    @Override
    public ru.osslabs.model.reports.Report getReportByPath(String s) {
        return null;
    }

    @Override
    public DataObject getReportParams(String reportPath) {
        MetaObject metaObject = new MetaObject(reportPath, reportPath, reportPath);
        FacesContext context = FacesContext.getCurrentInstance();
        Application application = context.getApplication();

//        try {
        int idx = 0;
        for (ReportParameter parameter : reportWrappers.get(Paths.get(reportPath)).getReportParams()) {
            idx++;
            MetaObject param = new MetaObject();
            param.getAttributes().put(MetaConstants.FIELD_ROW, idx);

            ObjectType paramType = new ObjectType(parameter.getTypeParameter().getCanonicalName());

//                    String itemsExp = jRparam.getPropertiesMap().getProperty(PARAM_ITEMS);


//                    if (!StringUtils.isEmpty(itemsExp)) {
//                        Object list = application.evaluateExpressionGet(context, itemsExp, Object.class);
//
//                        if (list instanceof String) {
//                            String[] items = StringUtils.split((String) list, ";");
//                            list = new ArrayList();
//                            for (String item : items) {
//                                String[] valLab = item.split(":");
//                                ((ArrayList) list).add(valLab);
//                            }
//                        }
//
//                        Class cls = null;
//                        if (Collection.class.isAssignableFrom(jRparam.getValueClass())) {
//                            cls = (new ArrayList<Object>()).getClass();
//                        } else if (jRparam.getValueClass().isArray()) {
//                            cls = Array.newInstance(Object.class, 0).getClass();
//                        } else if (!StringUtils.isEmpty(itemsExp)) {
//                            cls = jRparam.getValueClass();
//                        }
//                        paramType = ObjectTypeUtils.createReferenceTypeStatic(cls, (List) list, false);
//                        param.getAttributes().put(PARAMETERIZED_TYPE, cls.getName());
//                    }

            param.setType(paramType);

            param.setCode(parameter.getCode());
            param.setName(parameter.getDescription());
//                    if (!StringUtils.isEmpty(jRparam.getPropertiesMap().getProperty(PARAM_DEFAULT_VALUE)))
//                        param.getAttributes().put(MetaConstants.FIELD_DEFAULT_VALUE_EL, jRparam.getPropertiesMap().getProperty(PARAM_DEFAULT_VALUE));
//                    if (!jRparam.getPropertiesMap().containsProperty(PARAM_OPTIONAL))
//                        param.getAttributes().put(MetaConstants.FIELD_REQUIRED, true);


            metaObject.getChildren().put(param.getCode(), param);
        }
//            }

//        } catch (Exception e) {
//            e.printStackTrace();
//        }

        DataObject result = DataObjectProxyFactory.createDataObject(new Class[]{MetaObject.class, boolean.class}, new Object[]{metaObject, true});

        return result;
    }

    @Override
    public void runReport(String reportPath, ExportType exportType, OutputStream outputStream, Map<String, Object> map) {
        ReportWrapper reportWrapper = reportWrappers.get(Paths.get(reportPath));
        reportWrapper.runReport(exportType, outputStream, Void.class);
    }

    @Override
    public List<String> getReportFilesList() {
        return null;
    }

    @Override
    public ReportWrapper<?> getReport(String reportPath) {
        return reportWrappers.get(Paths.get(reportPath));
    }

    @Override
    public <T extends Report> void registerWrapper(ReportWrapper<T> wrapper) {
        reportWrappers.put(wrapper.getReportPath(), wrapper);
    }
}
