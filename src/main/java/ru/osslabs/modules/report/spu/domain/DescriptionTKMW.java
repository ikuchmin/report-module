package ru.osslabs.modules.report.spu.domain;

import lombok.Data;
import ru.osslabs.modules.report.CMDBuildField;

import java.util.List;

/**
 * Created by Serge Kozyrev on 12.01.16.
 */
@Data
public class DescriptionTKMW {
    @CMDBuildField(name = "NameTKMW")
    private String nameTKMW;
    @CMDBuildField(name = "TypDocApprovKMW1")
    private List<DirectTKMW> typDocApprovKMW1;
}
