package ru.osslabs.modules.report.transformers;

import javaslang.Tuple2;
import javaslang.control.Option;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.CellReference;
import ru.osslabs.modules.report.Matrix;
import ru.osslabs.modules.report.decorators.SourceFututeHSSFWorkBookReport;
import ru.osslabs.modules.report.domain.spu.NormativeType;
import ru.osslabs.modules.report.domain.spu.OgvGovernment;
import ru.osslabs.modules.report.domain.spu.Rejection;
import ru.osslabs.modules.report.domain.spu.SubServices;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
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
     * First uses is report for SED
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

    public static <Re extends SourceFututeHSSFWorkBookReport> HSSFWorkbook fromStreamSubServicesToHSSFWorkbook(Re report, Stream<SubServices> data) {
        HSSFWorkbook workbook = report.getHSSFWorkbookFuture().get();
        CellReference ref = new CellReference(workbook.getName(report.getDataBagCellName()).getRefersToFormula());
        HSSFSheet sheet = workbook.getSheet(ref.getSheetName());

        String NO = "Нет";
        AtomicInteger rowIdx = new AtomicInteger(0);
        data.forEach((ss) -> {
            int actualRow = rowIdx.getAndIncrement() + ref.getRow();
            Row.of(Option.of(sheet.getRow(actualRow)).orElseGet(() -> sheet.createRow(actualRow)), ref.getCol())
                    .addCellWithValue(Option.of(ss.getNamesubservice())
                            .orElse(NO))
                    .addCellWithValue("1")
                    .addCellWithValue(Option.of(ss.getPeriodsubservice())
                            .map((val) -> String.format("%d %s", val, ss.getFormPeriodSubservice().getValue()))
                            .orElse(NO))
                    .addCellWithValue(Option.of(ss.getPeriodSubservice_ExTerr())
                            .map((val) -> String.format("%d %s", val, ss.getFormPeriodSubservice_ExTer().getValue()))
                            .orElse(NO))
                    .addCellWithValue(Option.of(ss.getReject_noRecept())
                            .filter(arr -> !arr.isEmpty())
                            .map(arr -> arr.stream()
                                    .map(Rejection::getDescription)
                                    .collect(Collectors.joining("\n")))
                            .orElse(NO))
                    .addCellWithValue(Option.of(ss.getRejection_noProv())
                            .filter(arr -> !arr.isEmpty())
                            .map(arr -> arr.stream()
                                    .map(Rejection::getDescription)
                                    .collect(Collectors.joining("\n")))
                            .orElse(NO))
                    .addCellWithValue(Option.of(ss.getRejection_noAct())
                            .filter(arr -> !arr.isEmpty())
                            .map(arr -> arr.stream()
                                    .map(Rejection::getDescription)
                                    .collect(Collectors.joining("\n")))
                            .orElse(NO))
                    .addCellWithValue(Option.of(ss.getSuspension_days())
                            .map((val) -> String.format("%d %s", ss.getSuspension_days(), ss.getFormSuspension_days().getValue()))
                            .orElse(NO))
                    .addCellWithValue(Option.of(ss.getSubservice_Payment2())
                            .filter(arr -> !arr.isEmpty())
                            .map(arr -> arr.stream()
                                    .map((p) ->
                                            String.format("%s. Размер государственной пошлины или иной платы: %d %s",
                                                    p.getDescription(),
                                                    p.getSizepayment(),
                                                    p.getSizepaymentUnit().getValue()))
                                    .collect(Collectors.joining("\n")))
                            .orElse(NO))
                    .addCellWithValue(Option.of(ss.getSubservice_Payment2()) // 7.2
                            .filter(arr -> !arr.isEmpty())
                            .map(arr -> arr.stream()
                                    .flatMap(p -> p.getPayment_npa().stream()
                                            .map((npa) -> String.format(
                                                    "%s от t № %s %s орган власти, утвердивший административный регламент: %s. %s",
                                                    Option.of(npa.getTYPE_NPA())
                                                            .filter(lst -> !lst.isEmpty())
                                                            .map(lst -> lst.get(0).getDescription())
                                                            .orElse(""),
//                                                    npa.getDateNPA(),
                                                    npa.getNumberNPA(),
                                                    npa.getNameNPA(),
                                                    Option.of(npa.getOgv_NPA())
                                                            .filter(lst -> !lst.isEmpty())
                                                            .map(lst -> lst.get(0).getFullName())
                                                            .orElse(""),
                                                    p.getPointForPayment())))
                                    .collect(Collectors.joining("\n")))
                            .orElse("-"))
                    .addCellWithValue(null) // 7.3
                    .addCellWithValue(null) // 8
                    .addCellWithValue(null); // 9
        });
        return workbook;
    }
}
