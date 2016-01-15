package ru.osslabs.modules.report.spu.domain;

import lombok.Data;
import ru.osslabs.modules.report.CMDBuildField;

import java.util.List;

/**
 * Created by Serge Kozyrev on 14.01.16.
 */
@Data
public class ProcessesSubservices {
    @CMDBuildField(name = "ListAdminProcedures")
    private List<AdministrativeProcedures> listAdminProcedures;
    @CMDBuildField(name = "NamProcedProcess")
    private List<DescriptiProcess> namProcedProcess;
}
