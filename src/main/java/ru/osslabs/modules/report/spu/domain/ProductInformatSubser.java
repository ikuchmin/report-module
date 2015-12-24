package ru.osslabs.modules.report.spu.domain;

import lombok.Data;
import ru.osslabs.modules.report.CMDBuildField;

/**
 * Created by Serge Kozyrev on 23.12.15.
 */
@Data
public class ProductInformatSubser {
    @CMDBuildField(name = "Description")
    private String description;
    @CMDBuildField(name = "OtherWays")
    private String otherWays;
}

