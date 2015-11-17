package ru.osslabs.modules.report.impls.sed;

import ru.osslabs.modules.report.*;
import ru.osslabs.modules.report.types.Report;

import javax.ejb.*;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import java.io.OutputStream;
import java.nio.file.Path;
import java.util.Collection;
import java.util.Map;
import java.util.Set;

import static javax.ejb.ConcurrencyManagementType.BEAN;

/**
 * Created by ikuchmin on 09.11.15.
 */
@Startup
@Singleton
@ConcurrencyManagement(BEAN)
@DependsOn("ReportRegistryImpl")
@Dependent
@ReportWrapperAnnotation
public class ReportWrapperImpl<T extends Report> implements ReportWrapper<T> {
//    @Inject // Use qualifier for separate implementation
    ReportFactory reportFactory;

//    @Inject  // Use qualifier for separate implementation
//    ReportPOJOBuilder<SEDDDunaevReport> reportPOJOBuilder;

//    @Inject
//    EntityManager entityManager;

    @Override
    public String getReportCode() {
        return reportFactory.getReportCode();
    }

    @Override
    public Path getReportPath() {
        return reportFactory.getReportPath();
    }

    @Override
    public String getReportName() {
        return reportFactory.getReportName();
    }

    @Override
    public Collection<ReportParameter> getReportParams() {
        return reportFactory.getReportParams();
    }

    @Override
    public Collection<ExportType> getExportTypes() {
        return null;
//        return reportFactory.getExportTypes();
    }

    @Override
    public <R> R runReport(ExportType type, T report, Class<R> expectedResult) {
        return null;
//        return reportFactory.runReport(type, report, expectedResult);
    }

    @Override
    public <R> R runReport(ExportType type, OutputStream outputStream /*TODO: Избавиться от прямого указания типа выходного параметра*/, Class<R> expectedResult) {
        return null;
//        return runReport(type,
//                new SEDDDunaevReport(
//                        () -> this.getClass().getClassLoader().getResourceAsStream(getReportPath().toString()),
//                        outputStream
//                ),
//                expectedResult);
    }

//    @Override
//    public void runReport(String reportPath, ExportType exportType, OutputStream outputStream, Map<String, Object> parameters) {
//        reportFactory.runReport(exportType, reportPOJOBuilder.build(parameters));
//    }


}
