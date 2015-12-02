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
import ru.osslabs.modules.report.domain.CMDField;
import ru.osslabs.modules.report.domain.spu.*;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;

import static javaslang.collection.Stream.ofAll;
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
        data.forEach((ss) ->
                Row.of(objectNotNull(rowIdx.getAndIncrement() + ref.getRow(), sheet::getRow, sheet::createRow), ref.getCol())
                        .addCellWithValue(Option.of(ss.getNamesubservice())
                                .orElse(NO))
                        .addCellWithValue("1")
                        .addCellWithValue(Option.of(ss.getPeriodsubservice())
                                .map((val) -> String.format("%d %s", val, ss.getFormPeriodSubservice().getValue()))
                                .orElse(NO))
                        .addCellWithValue(Option.of(ss.getPeriodSubservice_ExTerr())
                                .map((val) -> String.format("%d %s", val, ss.getFormPeriodSubservice_ExTer().getValue()))
                                .orElse(NO))
                        .addCellWithValue(ofAll(ss.getReject_noRecept())
                                .map(Rejection::getDescription)
                                .reduceLeftOption(HSSFWorkbookTransformers::joiningNewLine)
                                .orElse(NO))
                        .addCellWithValue(ofAll(ss.getRejection_noProv())
                                .map(Rejection::getDescription)
                                .reduceLeftOption(HSSFWorkbookTransformers::joiningNewLine)
                                .orElse(NO))
                        .addCellWithValue(ofAll(ss.getRejection_noAct())
                                .map(Rejection::getDescription)
                                .reduceLeftOption(HSSFWorkbookTransformers::joiningNewLine)
                                .orElse(NO))
                        .addCellWithValue(Option.of(ss.getSuspension_days())
                                .map((val) -> String.format("%d %s", ss.getSuspension_days(), ss.getFormSuspension_days().getValue()))
                                .orElse(NO))
                        .addCellWithValue(ofAll(ss.getSubservice_Payment2())
                                .map((p) ->
                                        String.format("%s. Размер государственной пошлины или иной платы: %d %s",
                                                p.getDescription(),
                                                p.getSizepayment(),
                                                p.getSizepaymentUnit().getValue()))
                                .reduceLeftOption(HSSFWorkbookTransformers::joiningNewLine)
                                .orElse(NO))
                        .addCellWithValue(ofAll(ss.getSubservice_Payment2()) // 7.2
                                .flatMap(p -> ofAll(p.getPayment_npa())
                                        .map((npa) -> String.format(
                                                "%s от %s № %s %s орган власти, утвердивший административный регламент: %s. %s",
                                                ofAll(npa.getTYPE_NPA())
                                                        .headOption()
                                                        .map(NormativeType::getDescription)
                                                        .orElse(""),
                                                npa.getDateNPA(),
                                                npa.getNumberNPA(),
                                                npa.getNameNPA(),
                                                ofAll(npa.getOgv_NPA())
                                                        .headOption()
                                                        .map(OgvGovernment::getFullName)
                                                        .orElse(""),
                                                p.getPointForPayment())))
                                .reduceLeftOption(HSSFWorkbookTransformers::joiningNewLine)
                                .orElse("-"))
                        .addCellWithValue(ofAll(ss.getSubservice_Payment2()) // 7.3
                                .map(pa -> String.format(
                                        "КБК при обращении в орган власти: %s. КБК при обращении в МФЦ: %s",
                                        pa.getKbk_OGV(), pa.getKbk_MFC()))
                                .reduceLeftOption((acc, ps) -> acc.concat("\n").concat(ps))
                                .orElse("-"))
                        .addCellWithValue(ofAll(
                                ofAll(ss.getLichnoVOrgan(),
                                        ss.getLichnoVTerrOrgan(),
                                        ss.getLichnoVMFC(),
                                        ss.getPortalGosUslig(),
                                        ss.getPost())
                                        .filter(cm -> cm.getValue().filter(v -> v.equals(false)).isDefined())
                                        .map(CMDField::getDescription)
                                        .reduceLeftOption(HSSFWorkbookTransformers::joiningNewLine),
                                Option.of(ss.getOffSiteOrganaUslugi())
                                        .filter(cm -> cm.getValue().filter(v -> v.equals(false)).isDefined())
                                        .map(cm -> String.format("%s %s", cm.getDescription(), ss.getAdressOffSite())),
                                ofAll(ss.getAppealSubServices()).map(SebserviceAppeal::getDescription)
                                        .reduceLeftOption(HSSFWorkbookTransformers::joiningNewLine))
                                .filter(Option::isDefined)
                                .flatMap(ignored -> ignored)
                                .reduceLeftOption(HSSFWorkbookTransformers::joiningNewLine).orElse("-"))
                        .addCellWithValue(ofAll(  // 9
                                ofAll(ss.getTerrOrgOnPaper(),
                                        ss.getInMFConPaperFrom(),
                                        ss.getInMFCinDocFromITOrg(),
                                        ss.getFromCabinetGosUslug(),
                                        ss.getFromGosUslugInELForm(),
                                        ss.getEmailDocWithElSignature(),
                                        ss.getPostResult())
                                        .filter(cm -> cm.getValue().filter(v -> v.equals(false)).isDefined())
                                        .map(CMDField::getDescription)
                                        .reduceLeftOption(HSSFWorkbookTransformers::joiningNewLine),
                                Option.of(ss.getFromCabinetOffSite())
                                        .filter(cm -> cm.getValue().filter(v -> v.equals(false)).isDefined())
                                        .map(cm -> String.format("%s %s", cm.getDescription(), ss.getAddresOffSiteResult())),
                                Option.of(ss.getFromOffSiteElDoc())
                                        .filter(cm -> cm.getValue().filter(v -> v.equals(false)).isDefined())
                                        .map(cm -> String.format("%s %s", cm.getDescription(), ss.getAddresOffSiteELDoc())),
                                ofAll(ss.getAppealSubServices()).map(SebserviceAppeal::getDescription)
                                        .reduceLeftOption(HSSFWorkbookTransformers::joiningNewLine))
                                .filter(Option::isDefined)
                                .flatMap(ignored -> ignored)
                                .reduceLeftOption(HSSFWorkbookTransformers::joiningNewLine).orElse("-")));
    return workbook;
}

    public static String joiningNewLine(String acc, String ps) {
        return acc.concat("\n").concat(ps);
    }
}
