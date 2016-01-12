package ru.osslabs.modules.report.spu.domain;

import lombok.Data;
import ru.osslabs.modules.report.CMDBuildField;
import ru.osslabs.modules.report.domain.CMDField;

/**
 * Created by Serge Kozyrev on 25.12.15.
 */
@Data
public class PersonsEntitledReceivePodology {
    private Integer id;
    @CMDBuildField(name = "Description")
    private String description;
}
