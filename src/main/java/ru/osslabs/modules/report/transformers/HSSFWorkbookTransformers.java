package ru.osslabs.modules.report.transformers;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.CellReference;
import ru.osslabs.modules.report.decorators.BetweenDateReport;
import ru.osslabs.modules.report.decorators.DestinationPathReport;
import ru.osslabs.modules.report.decorators.SourceFututeHSSFWorkBookReport;
import ru.osslabs.modules.report.Matrix;
import ru.osslabs.modules.report.functions.Transformer;
import ru.osslabs.modules.report.types.JUniPrintReport;

import java.util.concurrent.ExecutionException;

import static ru.osslabs.modules.report.ReportUtils.objectNotNull;

/**
 * Created by ikuchmin on 03.11.15.
 */
public class HSSFWorkbookTransformers {

    public static <Re extends SourceFututeHSSFWorkBookReport> HSSFWorkbook fromMatrixToHSSFWorkbook(Re report, Matrix<Double> data) {
        try {
            // TODO: Возможно стоит как-то ограничить время на Future<V>::get
            HSSFWorkbook workbook = report.getHSSFWorkbookFuture().get();
            CellReference ref = new CellReference(workbook.getName(report.getDataBagCellName()).getRefersToFormula());
            HSSFSheet sheet = workbook.getSheet(ref.getSheetName());
            data.traversal((row, col, el) -> {
                int actualRow = row + ref.getRow();
                int actualCol = col + ref.getCol();
                HSSFRow rowCells = objectNotNull(() -> sheet.getRow(actualRow), () -> sheet.createRow(actualRow));
                HSSFCell cell = objectNotNull(() -> rowCells.getCell(actualCol), () -> rowCells.createCell(actualCol));
                cell.setCellValue(el);
            });
            return workbook;
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException(e);
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        }
    };
}
