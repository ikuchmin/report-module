package ru.osslabs.modules.report.spu.domain;

import lombok.Data;
import ru.osslabs.modules.report.CMDBuildField;

import java.util.List;

/**
 * Created by Serge Kozyrev on 11.01.16.
 */
@Data
public class DescribeRepresentativesApplicants {
    @CMDBuildField(name = "Description")
    private String description;
    @CMDBuildField(name = "CatPerEntiRecObslu")
    private List<Representatives> catPerEntiRecObslu;
}
