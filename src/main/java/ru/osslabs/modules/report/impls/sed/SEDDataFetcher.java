package ru.osslabs.modules.report.impls.sed;

import ru.osslabs.datasources.cmdbuild.CMDBuildWrapperWS;
import ru.osslabs.modules.report.decorators.BetweenDateReport;
import ru.osslabs.modules.report.functions.Fetcher;
import ru.osslabs.modules.report.types.JUniPrintReport;

import javax.inject.Inject;
import java.time.LocalDate;
import java.time.OffsetDateTime;

/**
 * Created by ikuchmin on 08.11.15.
 */
public class SEDDataFetcher implements Fetcher<BetweenDateReport, Object>{

    @Override
    public Object compose(BetweenDateReport report) {
        return null;
    }
}
