package ru.osslabs.modules.report.types;

import net.sf.jasperreports.engine.JasperReport;

/**
 * Created by ikuchmin on 03.11.15.
 */
public class JRReport implements Report {
    private final JasperReport jasperReport;

    public JRReport(JasperReport jasperReport) {
        this.jasperReport = jasperReport;
    }
}
