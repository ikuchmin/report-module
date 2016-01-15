package ru.osslabs.modules.report.spu.domain;

import lombok.Data;
import ru.osslabs.modules.report.CMDBuildField;
import ru.osslabs.modules.report.domain.Lookup;

/**
 * Created by Serge Kozyrev on 14.01.16.
 */
@Data
public class DescriptiProcess {
    @CMDBuildField(name = "Description")
    private String description;
    @CMDBuildField(name = "ispProcedur2")
    private PerformersProcedure ispProcedur;
    @CMDBuildField(name = "DetailsProcProcess")
    private String detailsProcProcess;
    @CMDBuildField(name = "PeriodApplyDays")
    private Integer periodApplyDays;
    @CMDBuildField(name = "LOOKUPclock")
    private Lookup<String> lookupClock;
    @CMDBuildField(name = "LookupTimePeriod")
    private Lookup<String> lookupTimePeriod;
    @CMDBuildField(name = "Resources")
    private String resources;
    @CMDBuildField(name = "BelongAdminProced2")
    private AdministrativeProcedures belongAdminProced2;
}
