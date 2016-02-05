package ru.osslabs.modules.report.isui.domain;

import lombok.Data;
import ru.osslabs.modules.report.CMDBuildField;

/**
 * Created by Serge Kozyrev on 05.02.16.
 */
@Data
public class SouceIncome {
    @CMDBuildField(name = "Description")
    private String description;
}
