package ru.osslabs.modules.report.domain.spu;

import java.util.List;

/**
 * Created by ikuchmin on 19.11.15.
 */
public class SubService {

    private String name;
    private PeriodSubService period;
    private List<String> reasonRejectDocuments;
    private List<String> reasonReject;
    private List<String> reasonSuspend;
    private Unit<Integer, String> suspendCount;
    private PaymentSubService payment;
    private List<String> methodsGet;
    private List<String> methodsGetResult;

    public SubService(String name, PeriodSubService period,
                      List<String> reasonRejectDocuments,
                      List<String> reasonReject,
                      List<String> reasonSuspend,
                      Unit<Integer, String> suspendCount,
                      Unit<Integer, String> unitSuspend,
                      PaymentSubService payment,
                      List<String> methodsGet,
                      List<String> methodsGetResult) {
        this.name = name;
        this.period = period;
        this.reasonRejectDocuments = reasonRejectDocuments;
        this.reasonReject = reasonReject;
        this.reasonSuspend = reasonSuspend;
        this.suspendCount = suspendCount;
        this.payment = payment;
        this.methodsGet = methodsGet;
        this.methodsGetResult = methodsGetResult;
    }

    public static class Rejection {
        private String description;

        public Rejection(String description) {
            this.description = description;
        }

        public String getDescription() {
            return description;
        }
    }

    public static class PaymentSubService {
        private String name;
        private List<String> npa;
        private String KBKMFC;
        private String KBKOGV;

        public PaymentSubService(String name, List<String> npa, String KBKMFC, String KBKOGV) {
            this.name = name;
            this.npa = npa;
            this.KBKMFC = KBKMFC;
            this.KBKOGV = KBKOGV;
        }
    }

    public static class PeriodSubService {
        private Unit<Integer, String> period;
        private Unit<Integer, String> periodExTerr;

        public PeriodSubService(Unit<Integer, String> period, Unit<Integer, String> periodExTerr) {
            this.period = period;
            this.periodExTerr = periodExTerr;
        }
    }

    public static class Unit<T, U> {
        private T value;
        private U unit;

        public Unit(T value, U unit) {
            this.value = value;
            this.unit = unit;
        }

        public T getValue() {
            return value;
        }

        public U getUnit() {
            return unit;
        }
    }
}
