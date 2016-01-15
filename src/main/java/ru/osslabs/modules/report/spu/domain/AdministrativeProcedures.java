package ru.osslabs.modules.report.spu.domain;

import lombok.Data;
import ru.osslabs.modules.report.CMDBuildField;

/**
 * Created by Serge Kozyrev on 14.01.16.
 */
@Data
public class AdministrativeProcedures {
    private Integer id;
    @CMDBuildField(name = "Description")
    private String description;
}
