package ru.osslabs.modules.report.spu.domain;

import lombok.Data;
import ru.osslabs.modules.report.CMDBuildField;

/**
 * Created by Serge Kozyrev on 24.12.15.
 */
@Data
public class MethObtProgresInfo {
    @CMDBuildField(name = "Description")
    private String description;
    @CMDBuildField(name = "ObtaInfoProgRequest")
    private String obtaInfoProgRequest;
}
