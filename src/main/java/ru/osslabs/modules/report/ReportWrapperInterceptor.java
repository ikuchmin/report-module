    //package ru.osslabs.modules.report;
    //
    //import javax.annotation.PostConstruct;
    //import javax.inject.Inject;
    //import javax.interceptor.Interceptor;
    //import javax.interceptor.InvocationContext;
    //import java.util.logging.Level;
    //import java.util.logging.Logger;
    //
    ///**
    // * Created by ikuchmin on 10.11.15.
    // */
    //
    ////@Interceptor
    ////@ReportWrapperAnnotation(discovery = ReportRegistryImplOld.class)
    //public class ReportWrapperInterceptor {
    //    @Inject
    //    private Logger log;
    //
    //    @Inject // Use qualifier for separate implementation
    //    private ReportRegistry reportRegistry;
    //
    //    /**
    //     * Используется для создания врапера и регистрации его в Discovery
    //     * Будет крайне активно использоваться в момент когда для преобразования
    //     * из Map -> Object будет использоваться Codec. Посмотреть на реализацию Codec
    //     * можно Jackson или SpringWS
    //     * @param ic
    //     * @throws Exception
    //     */
    //    @PostConstruct
    //    public void register(InvocationContext ic) throws Exception {
    ////        reportRegistry.registerWrapper((ReportWrapper) ic.getTarget());
    ////        log.log(Level.INFO, "ReportWrapper registered: " + ((ReportWrapper)ic.getTarget()).getReportName());
    //        ic.proceed();
    //    }
    //}
