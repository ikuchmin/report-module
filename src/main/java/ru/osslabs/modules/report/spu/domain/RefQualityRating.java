package ru.osslabs.modules.report.spu.domain;

import lombok.Data;
import ru.osslabs.modules.report.CMDBuildField;

/**
 * Created by Serge Kozyrev on 14.12.15.
 */
@Data
public class RefQualityRating {
    @CMDBuildField(name = "Description")
    private String description;
}
