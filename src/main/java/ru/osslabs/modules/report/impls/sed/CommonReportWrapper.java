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
import java.util.TreeSet;

import static javax.ejb.ConcurrencyManagementType.BEAN;

/**
 * Created by ikuchmin on 09.11.15.
 */
//@Startup
//@Singleton
//@ConcurrencyManagement(BEAN)
//@DependsOn("ReportRegistryImpl")
//@Dependent
//@ReportWrapperAnnotation
public class CommonReportWrapper implements ReportWrapper {
    private String reportCode;
    private Path reportPath;
    private String reportName;
    private Collection<ReportParameter> reportParams;
    private Collection<ExportType> exportTypes;


//    @Inject // Use qualifier for separate implementation
//    ReportFactory reportFactory;

//    @Inject  // Use qualifier for separate implementation
//    ReportPOJOBuilder<SEDDDunaevReport> reportPOJOBuilder;

//    @Inject
//    EntityManager entityManager;


    public CommonReportWrapper(String reportCode,
                               Path reportPath,
                               String reportName,
                               Collection<ReportParameter> reportParams,
                               Collection<ExportType> exportTypes) {
        this.reportCode = reportCode;
        this.reportPath = reportPath;
        this.reportName = reportName;
        this.reportParams = reportParams;
        this.exportTypes = exportTypes;
    }

    public CommonReportWrapper(String reportCode,
                               Path reportPath,
                               String reportName,
                               Collection<ReportParameter> reportParams) {
        this(reportCode, reportPath, reportName, reportParams, new TreeSet<>());
    }

    @Override
    public String getReportCode() {
        return reportCode;
    }

    @Override
    public Path getReportPath() {
        return reportPath;
    }

    @Override
    public String getReportName() {
        return reportName;
    }

    @Override
    public Collection<ReportParameter> getReportParams() {
        return reportParams;
    }

    @Override
    public Collection<ExportType> getExportTypes() {
        return exportTypes;
    }

    public CommonReportWrapper addExportType(ExportType type) {
        exportTypes.add(type);
        return this;
    }

//    @Override
//    public <R> R runReport(ExportType type, T report, Class<R> expectedResult) {
//        return null;
//        return reportFactory.runReport(type, report, expectedResult);
//    }

//    @Override
//    public <R> R runReport(ExportType type, OutputStream outputStream /*TODO: Избавиться от прямого указания типа выходного параметра*/, Class<R> expectedResult) {
//        return null;
//        return runReport(type,
//                new SEDDDunaevReport(
//                        () -> this.getClass().getClassLoader().getResourceAsStream(getReportPath().toString()),
//                        outputStream
//                ),
//                expectedResult);
//    }

//    @Override
//    public void runReport(String reportPath, ExportType exportType, OutputStream outputStream, Map<String, Object> parameters) {
//        reportFactory.runReport(exportType, reportPOJOBuilder.build(parameters));
//    }


}
