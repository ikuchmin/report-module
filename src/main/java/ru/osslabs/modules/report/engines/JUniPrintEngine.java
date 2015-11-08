package ru.osslabs.modules.report.engines;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.comsoft.juniprint.JUniPrint;
import org.comsoft.juniprint.JUniPrintException;
import ru.osslabs.modules.report.types.JUniPrintReport;
import ru.osslabs.modules.report.functions.Engine;

/**
 * Created by ikuchmin on 03.11.15.
 */
public class JUniPrintEngine<Da> implements Engine<JUniPrintReport, Da, HSSFWorkbook> {

    @Override
    public HSSFWorkbook compile(JUniPrintReport report, Da composableData) {
        try {
            JUniPrint jUniPrint = new JUniPrint(report.getWorkbook());
            jUniPrint.init(null, null, report.getNamedDataBag().getNameName());
            jUniPrint.uniPrint(false);
            return report.getWorkbook();
        } catch (JUniPrintException e) {
            throw new RuntimeException(e);
        }
    }

    public static <Da> JUniPrintEngine<Da> jUniPrintEngineFactory() {
        return new JUniPrintEngine<>();
    }
}
