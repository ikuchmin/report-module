package ru.osslabs.modules.report.spu.domain;

import lombok.Data;
import ru.osslabs.modules.report.CMDBuildField;

/**
 * Created by Serge Kozyrev on 12.01.16.
 */
@Data
public class Vid_TKMW {
    @CMDBuildField(name = "Description")
    private String description;
}
