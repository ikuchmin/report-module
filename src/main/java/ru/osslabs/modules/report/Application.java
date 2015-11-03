package ru.osslabs.modules.report;

import ru.osslabs.modules.report.domain.Service;

import java.util.stream.Stream;

/**
 * Created by ikuchmin on 02.11.15.
 */
public class Application {

    public static void main(String[] args) {
//        Stream<Service> stringStream = ;
//        stringStream.map()

//        ReportOnPreFetchDataStage<Report> pfs;
//        pfs.compose()
//                .transform()
//                .transform()
//                .transform()
        new ReportBuilder(new Report());
        Object rntg = new ReportBuilder.PreFetchDataStage<>(new Report())
                .compose(ServiceDataProvider.fromStatic())
                .transform(ServiceDataProvider.addCommonSubService())
                .transform(ServiceDataProvider.addCommonSubService())
                .transform(ServiceDataProvider.addCommonSubService())
                .transform(ServiceDataProvider.addCommonSubService())
                .transform(ServiceDataProvider.addCommonSubService())
                .transform(ServiceDataProvider.addCommonSubService())
                .compile((report, composableData) -> null)
                .convert((report, compiledReport) -> null)
                .convert((report, compiledReport) -> null)
                .publish((report, compiledReport) -> "Hello");

        reportFactory(
                ServiceDataProvider.fromStatic(),

                );
    }

    public static <T> void reportFactory(DataProvider<T> provider,
                                         ) {

    }
}

