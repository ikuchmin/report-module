package ru.osslabs.modules.report.transformers;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.CellReference;
import ru.osslabs.modules.report.types.JUniPrintReport;
import ru.osslabs.modules.report.Matrix;
import ru.osslabs.modules.report.functions.Transformer;

import java.util.List;

import static ru.osslabs.modules.report.ReportUtils.objectNotNull;

/**
 * Created by ikuchmin on 03.11.15.
 */
public class HSSFWorkbookTransformers {
    public static Transformer<JUniPrintReport, List<List<Double>>, HSSFWorkbook> fromDoubleArray() {
        return (report, data) -> {
            CellReference anchorRef = new CellReference(report.getNamedDataBag().getRefersToFormula());
            HSSFWorkbook anchorWorkbook = report.getWorkbook();
            HSSFSheet anchorSheet = anchorWorkbook.getSheet(anchorRef.getSheetName());
            Matrix.of(data).traversal((row, col, el) -> {
                int actualRow = row + anchorRef.getRow();
                int actualCol = col + anchorRef.getCol();
                HSSFRow rowCells = objectNotNull(() -> anchorSheet.getRow(actualRow), () -> anchorSheet.createRow(actualRow));
                HSSFCell cell = objectNotNull(() -> rowCells.getCell(actualCol), () -> rowCells.createCell(actualCol));
                cell.setCellValue(el);
            });
            return anchorWorkbook;
        };
    }

    public static Transformer<JUniPrintReport, Matrix<Double>, HSSFWorkbook> fromMatrix() {
        return (report, data) -> {
            CellReference anchorRef = new CellReference(report.getNamedDataBag().getRefersToFormula());
            HSSFWorkbook anchorWorkbook = report.getWorkbook();
            HSSFSheet anchorSheet = anchorWorkbook.getSheet(anchorRef.getSheetName());
            data.traversal((row, col, el) -> {
                int actualRow = row + anchorRef.getRow();
                int actualCol = col + anchorRef.getCol();
                HSSFRow rowCells = objectNotNull(() -> anchorSheet.getRow(actualRow), () -> anchorSheet.createRow(actualRow));
                HSSFCell cell = objectNotNull(() -> rowCells.getCell(actualCol), () -> rowCells.createCell(actualCol));
                cell.setCellValue(el);
            });
            return anchorWorkbook;
        };
    }
}
