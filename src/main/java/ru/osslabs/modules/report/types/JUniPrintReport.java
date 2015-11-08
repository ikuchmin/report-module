package ru.osslabs.modules.report.types;

import org.apache.poi.hssf.usermodel.HSSFName;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

/**
 * Created by ikuchmin on 03.11.15.
 */
public class JUniPrintReport implements Report {
    private HSSFWorkbook workbook;
    private HSSFName namedDataBag;

    public JUniPrintReport(HSSFWorkbook workbook, HSSFName namedDataBag) {
        this.workbook = workbook;
        this.namedDataBag = namedDataBag;
    }

    public HSSFWorkbook getWorkbook() {
        return workbook;
    }

    public HSSFName getNamedDataBag() {
        return namedDataBag;
    }
}
