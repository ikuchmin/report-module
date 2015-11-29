package ru.osslabs.modules.report.transformers;

import javaslang.control.Option;
import org.apache.poi.hssf.usermodel.HSSFRow;

import java.util.Objects;

import static ru.osslabs.modules.report.ReportUtils.objectNotNull;

/**
 * Created by ikuchmin on 29.11.15.
 */
public class Row {
    private final HSSFRow row;
    private int cursor;

    public Row(HSSFRow row, int cursor) {
        this.row = row;
        this.cursor = cursor;
    }

    public static Row of(HSSFRow row) {
        return Row.of(row, 0);
    }

    public static Row of(HSSFRow row, int cursor) {
        return new Row(row, cursor);
    }

    public Row addCellWithValue(String val) {
        objectNotNull(cursor++, row::getCell, row::createCell).setCellValue(val);
        return this;
    }
}
