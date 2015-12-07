package ru.osslabs.modules.report.spu.domain;

import lombok.Data;
import lombok.NoArgsConstructor;
import ru.osslabs.modules.report.CMDBuildField;

/**
 * Created by ikuchmin on 23.11.15.
 */
@Data
@NoArgsConstructor
public class SebserviceAppeal {
    @CMDBuildField(name = "Description")
    private String description;
}
