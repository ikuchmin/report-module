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
import ru.osslabs.modules.report.spu.domain.*;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static javaslang.collection.Stream.ofAll;
import static ru.osslabs.modules.report.ReportUtils.objectNotNull;

/**
 * Created by ikuchmin on 03.11.15.
 */
public class HSSFWorkbookTransformers {
    private static Locale RUSSIAN = new Locale("ru", "RU");

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

    public static <Re extends SourceFututeHSSFWorkBookReport> HSSFWorkbook fromListMatrixToHSSFWorkbook(Re report, List<List<Double>> data) {
        return fromMatrixToHSSFWorkbook(report, Matrix.of(data));
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
                        .addCellWithValue(String.format("%d", rowIdx.get())) // If you confuse, which number was row. Here we see rowIndx + 1 because in Row.of we saw rowIdx.getAndIncrement()
                        .addCellWithValue(Option.of(ss.getNamesubservice())
                                .orElse(NO))
                        .addCellWithValue(Option.of(ss.getPeriodsubservice())
                                .map((val) -> String.format(RUSSIAN, "%d %s", val, ss.getFormPeriodSubservice().getValue()))
                                .orElse(NO))
                        .addCellWithValue(Option.of(ss.getPeriodSubservice_ExTerr())
                                .map((val) -> String.format(RUSSIAN, "%d %s", val, ss.getFormPeriodSubservice_ExTer().getValue()))
                                .orElse(NO))
                        .addCellWithValue(ofAll(ss.getReject_noRecept())
                                .map(Rejection::getDescription)
                                .map("- "::concat)
                                .reduceLeftOption(HSSFWorkbookTransformers::joiningNewLine)
                                .orElse(NO))
                        .addCellWithValue(ofAll(ss.getRejection_noProv())
                                .map(Rejection::getDescription)
                                .map("- "::concat)
                                .reduceLeftOption(HSSFWorkbookTransformers::joiningNewLine)
                                .orElse(NO))
                        .addCellWithValue(ofAll(ss.getRejection_noAct())
                                .map(Rejection::getDescription)
                                .map("- "::concat)
                                .reduceLeftOption(HSSFWorkbookTransformers::joiningNewLine)
                                .orElse(NO))
                        .addCellWithValue(Option.of(ss.getSuspension_days())
                                .map((val) -> String.format(RUSSIAN, "%d %s", ss.getSuspension_days(), ss.getFormSuspension_days().getValue()))
                                .orElse(NO))
                        .addCellWithValue(ofAll(ss.getSubservice_Payment2())
                                .map((p) ->
                                        String.format(RUSSIAN, "%s. Размер государственной пошлины или иной платы: %d руб.",
                                                p.getDescription(),
                                                p.getSizepayment()))
                                .map("- "::concat)
                                .reduceLeftOption(HSSFWorkbookTransformers::joiningNewLine)
                                .orElse(NO))
                        .addCellWithValue(ofAll(ss.getSubservice_Payment2()) // 7.2
                                .flatMap(p -> ofAll(p.getPayment_npa())
                                        .map((npa) -> String.format(RUSSIAN,
                                                "%1$s от %2$td.%2$tm.%2$tY № %3$s %4$s орган власти, утвердивший административный регламент: %5$s. Пункт: %6$s",
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
                                .toSet()
                                .map("- "::concat)
                                .reduceLeftOption(HSSFWorkbookTransformers::joiningNewLine)
                                .orElse("-"))
                        .addCellWithValue(ofAll(ss.getSubservice_Payment2()) // 7.3
                                .map(pa -> String.format(RUSSIAN,
                                        "КБК при обращении в орган власти: %s. КБК при обращении в МФЦ: %s",
                                        pa.getKbk_OGV(), pa.getKbk_MFC()))
                                .toSet()
                                .map("- "::concat)
                                .reduceLeftOption((acc, ps) -> acc.concat("\n").concat(ps))
                                .orElse("-"))
                        .addCellWithValue(ofAll(
                                ofAll(ss.getLichnoVOrgan(),
                                        ss.getLichnoVTerrOrgan(),
                                        ss.getLichnoVMFC(),
                                        ss.getPortalGosUslig(),
                                        ss.getPost())
                                        .filter(cm -> cm.getValue().filter(v -> v.equals(true)).isDefined())
                                        .map(CMDField::getDescription),
                                Option.of(ss.getOffSiteOrganaUslugi())
                                        .filter(cm -> cm.getValue().filter(v -> v.equals(true)).isDefined())
                                        .map(cm -> String.format(RUSSIAN, "%s %s", cm.getDescription(), ss.getAdressOffSite())),
                                ofAll(ss.getAppealSubServices()).map(SebserviceAppeal::getDescription)
                                        .reduceLeftOption(HSSFWorkbookTransformers::joiningNewLine))
                                .flatMap(ignored -> ignored)
                                .toSet()
                                .map("- "::concat)
                                .reduceLeftOption(HSSFWorkbookTransformers::joiningNewLine)
                                .orElse("-"))
                        .addCellWithValue(ofAll(  // 9
                                ofAll(ss.getTerrOrgOnPaper(),
                                        ss.getInMFConPaperFrom(),
                                        ss.getInMFCinDocFromITOrg(),
                                        ss.getFromCabinetGosUslug(),
                                        ss.getFromGosUslugInELForm(),
                                        ss.getEmailDocWithElSignature(),
                                        ss.getPostResult())
                                        .filter(cm -> cm.getValue().filter(v -> v.equals(true)).isDefined()) // Replace true on false
                                        .map(CMDField::getDescription),
                                Option.of(ss.getFromCabinetOffSite())
                                        .filter(cm -> cm.getValue().filter(v -> v.equals(true)).isDefined())
                                        .map(cm -> String.format(RUSSIAN, "%s %s", cm.getDescription(), ss.getAddresOffSiteResult())),
                                Option.of(ss.getFromOffSiteElDoc())
                                        .filter(cm -> cm.getValue().filter(v -> v.equals(true)).isDefined())
                                        .map(cm -> String.format(RUSSIAN, "%s %s", cm.getDescription(), ss.getAddresOffSiteELDoc())),
                                ofAll(ss.getAppealSubServices()).map(SebserviceAppeal::getDescription)
                                        .reduceLeftOption(HSSFWorkbookTransformers::joiningNewLine))
                                .flatMap(ignored -> ignored)
                                .map("- "::concat)
                                .reduceLeftOption(HSSFWorkbookTransformers::joiningNewLine).orElse("-"))
                        .addCellWithValue(ZonedDateTime.now(ZoneId.of("Europe/Moscow")).format(DateTimeFormatter.ofPattern("dd.MM.uuuu", RUSSIAN)))
                        .addCellWithValue(ss.getService().getDescription()));


        return workbook;
    }

    public static <Re extends SourceFututeHSSFWorkBookReport> HSSFWorkbook fromStreamServiceToHSSFWorkbook(Re report, Option<Service> serviceOption) {
        HSSFWorkbook workbook = report.getHSSFWorkbookFuture().get();
        CellReference ref = new CellReference(workbook.getName(report.getDataBagCellName()).getRefersToFormula());
        HSSFSheet sheet = workbook.getSheet(ref.getSheetName());
        String NO = "Нет";
        AtomicInteger rowIdx = new AtomicInteger(0);
        if (serviceOption.isDefined()) {
            Service service = serviceOption.get();
            //1
            Row.of(objectNotNull(rowIdx.getAndIncrement() + ref.getRow(), sheet::getRow, sheet::createRow), ref.getCol())
                    .addCellWithValue(String.format("%d", rowIdx.get()))
                    .addCellWithValue("Наименование органа, предоставляющего услугу")
                    .addCellWithValue(String.format("%s",
                            ofAll(service.getRefOrgGovemment())
                                    .headOption()
                                    .map(RefOrgGovemment::getFullname)
                                    .orElse(NO)))
                    .addCellWithValue(ZonedDateTime.now(ZoneId.of("Europe/Moscow")).format(DateTimeFormatter.ofPattern("dd.MM.uuuu", RUSSIAN)));
            //2
            Row.of(objectNotNull(rowIdx.getAndIncrement() + ref.getRow(), sheet::getRow, sheet::createRow), ref.getCol())
                    .addCellWithValue(String.format("%d", rowIdx.get()))
                    .addCellWithValue("Номер услуги в федеральном реестре")
                    .addCellWithValue(String.format("%s", service.getFederalNumberOfService()));
            //3
            Row.of(objectNotNull(rowIdx.getAndIncrement() + ref.getRow(), sheet::getRow, sheet::createRow), ref.getCol())
                    .addCellWithValue(String.format("%d", rowIdx.get()))
                    .addCellWithValue("Полное наименование услуги")
                    .addCellWithValue(String.format("%s", service.getNameService()));
            //4
            Row.of(objectNotNull(rowIdx.getAndIncrement() + ref.getRow(), sheet::getRow, sheet::createRow), ref.getCol())
                    .addCellWithValue(String.format("%d", rowIdx.get()))
                    .addCellWithValue("Краткое наименование услуги")
                    .addCellWithValue(String.format("%s", service.getDescription()));
            //5
            Row.of(objectNotNull(rowIdx.getAndIncrement() + ref.getRow(), sheet::getRow, sheet::createRow), ref.getCol())
                    .addCellWithValue(String.format("%d", rowIdx.get()))
                    .addCellWithValue("Административный регламент предоставления государственной услуги")
                    .addCellWithValue(ofAll(service.getARegl())
                            .map(npa -> String.format(RUSSIAN,
                                    "%1$s от %2$td.%2$tm.%2$tY № %3$s %4$s орган власти, утвердивший административный регламент: %5$s.",
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
                                            .orElse("")))
                            .map("- "::concat)
                            .reduceLeftOption(HSSFWorkbookTransformers::joiningNewLine)
                            .orElse("-"));
            //6
            Row.of(objectNotNull(rowIdx.getAndIncrement() + ref.getRow(), sheet::getRow, sheet::createRow), ref.getCol())
                    .addCellWithValue(String.format("%d", rowIdx.get()))
                    .addCellWithValue("Перечень «подуслуг»")
                    .addCellWithValue(ofAll(service.getSubServices())
                            .map(SubServices::getNamesubservice)
                            .map("- "::concat)
                            .reduceLeftOption(HSSFWorkbookTransformers::joiningNewLine)
                            .orElse("-"));
            //7
            Row.of(objectNotNull(rowIdx.getAndIncrement() + ref.getRow(), sheet::getRow, sheet::createRow), ref.getCol())
                    .addCellWithValue(String.format("%d", rowIdx.get()))
                    .addCellWithValue("Способы оценки качества предоставления государственной услуги")
                    .addCellWithValue(ofAll(  // 9
                            ofAll(service.getRadiotelephone(),
                                    service.getTerminal(),
                                    service.getPortalPublicServices(),
                                    service.getSiteVashControl())
                                    .filter(cm -> cm.getValue().filter(v -> v.equals(true)).isDefined()) // Replace true on false
                                    .map(CMDField::getDescription),
                            Option.of(service.getOfficialSite())
                                    .filter(cm -> cm.getValue().filter(v -> v.equals(true)).isDefined())
                                    .map(cm -> String.format(RUSSIAN, "%s %s", cm.getDescription(), service.getWebsiteAddress())),
                            ofAll(service.getRefQualityRating()).map(RefQualityRating::getDescription)
                                    .reduceLeftOption(HSSFWorkbookTransformers::joiningNewLine))
                            .flatMap(ignored -> ignored)
                            .map("- "::concat)
                            .reduceLeftOption(HSSFWorkbookTransformers::joiningNewLine).orElse("-"));
        }
        return workbook;
    }

    public static String joiningNewLine(String acc, String ps) {
        return acc.concat("\n").concat(ps);
    }
}
