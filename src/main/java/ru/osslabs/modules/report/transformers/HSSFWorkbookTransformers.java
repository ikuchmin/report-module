package ru.osslabs.modules.report.transformers;

import javaslang.Tuple2;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.CellReference;
import ru.osslabs.modules.report.Matrix;
import ru.osslabs.modules.report.decorators.SourceFututeHSSFWorkBookReport;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;

import static ru.osslabs.modules.report.ReportUtils.objectNotNull;

/**
 * Created by ikuchmin on 03.11.15.
 */
public class HSSFWorkbookTransformers {

    public static <Re extends SourceFututeHSSFWorkBookReport> HSSFWorkbook fromMatrixToHSSFWorkbook(Re report, Matrix<Double> data) {
        // TODO: Возможно стоит как-то ограничить время на Future<V>::get
        HSSFWorkbook workbook = report.getHSSFWorkbookFuture().get();
        CellReference ref = new CellReference(workbook.getName(report.getDataBagCellName()).getRefersToFormula());
        HSSFSheet sheet = workbook.getSheet(ref.getSheetName());
        data.traversal((row, col, el) -> {
            int actualRow = row + ref.getRow();
            int actualCol = col + ref.getCol();
            HSSFRow rowCells = objectNotNull(actualRow, sheet::getRow, sheet::createRow);
            HSSFCell cell = objectNotNull(actualCol, rowCells::getCell, rowCells::createCell);
            cell.setCellValue(el);
        });
        return workbook;
    }

    /**
     * Первое использование для отчета Димы Дунаева
     *
     * @param report
     * @param data
     * @param <Re>
     * @return
     */
    public static <Re extends SourceFututeHSSFWorkBookReport> HSSFWorkbook fromStreamTuplesToHSSFWorkbook(Re report, Stream<Tuple2<String, Integer>> data) {
        // TODO: Возможно стоит как-то ограничить время на Future<V>::get
        HSSFWorkbook workbook = report.getHSSFWorkbookFuture().get();
        CellReference ref = new CellReference(workbook.getName(report.getDataBagCellName()).getRefersToFormula());
        HSSFSheet sheet = workbook.getSheet(ref.getSheetName());

        AtomicInteger rowIdx = new AtomicInteger(0);
        data.forEach((tupl) -> {
            int actualRow = rowIdx.getAndIncrement() + ref.getRow();
            HSSFRow rowCells = objectNotNull(actualRow, sheet::getRow, sheet::createRow);

            HSSFCell cell_1 = objectNotNull((int) ref.getCol(), rowCells::getCell, rowCells::createCell);
            cell_1.setCellValue(tupl._1);

            HSSFCell cell_2 = objectNotNull(ref.getCol() + 1, rowCells::getCell, rowCells::createCell);
            cell_2.setCellValue(tupl._2);
        });

        return workbook;

    }

    ;
}
