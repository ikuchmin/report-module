package ru.osslabs.modules.report;

import ru.osslabs.commons.dataproviders.ReportsDataProvider;
import ru.osslabs.model.datasource.DataObject;
import ru.osslabs.model.reports.ReportInfo;
import ru.osslabs.modules.report.impls.sed.SEDDDunaevReport;
import ru.osslabs.modules.report.impls.sed.SEDDDunaevReportWrapper;
import ru.osslabs.modules.report.types.Report;

import javax.annotation.PostConstruct;
import javax.ejb.ConcurrencyManagement;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.OutputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
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
public class ReportDiscoveryImpl implements ReportsDataProvider, ReportDiscovery {

//    @Inject
//    private SEDDDunaevReportWrapper dunaevReportWrapper;

//    static final String REPORT_DIR = "/reports/juniprint/";

    private Map<Path, ReportWrapper> reportWrappers;

    public ReportDiscoveryImpl() {
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

    public Collection<ReportWrapper> getReports() {
        return reportWrappers.values();
    }

    @Override
    public ru.osslabs.model.reports.Report getReportByPath(String s) {
        return null;
    }

    @Override
    public DataObject getReportParams(String s) {
        return null;
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

    public ReportWrapper getReport(String reportPath){
        return reportWrappers.get(Paths.get(reportPath));
    }

    @Override
    public <T extends Report> void registerWrapper(ReportWrapper<T> wrapper) {
        reportWrappers.put(wrapper.getReportPath(), wrapper);
    }
}
