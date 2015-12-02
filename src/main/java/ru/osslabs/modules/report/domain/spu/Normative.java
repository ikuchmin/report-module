package ru.osslabs.modules.report.domain.spu;

import lombok.Data;
import lombok.NoArgsConstructor;
import ru.osslabs.modules.report.CMDBuildField;

import java.util.Date;
import java.util.List;

/**
 * Created by ikuchmin on 23.11.15.
 */
@Data
@NoArgsConstructor
public class Normative {
    private String nameNPA;

    @CMDBuildField(name = "Description")
    private String description;
//    private Date dateNPA;
    @CMDBuildField(name = "DateNPA")
    private Date dateNPA;
    //    private String ogv_NPA_desc;
    private List<OgvGovernment> ogv_NPA;
    @CMDBuildField(name = "TYPE_NPA_Desc")
    private String tYPE_NPA_Desc;
    @CMDBuildField(name = "TYPE_NPA")
    private List<NormativeType> tYPE_NPA;
    private String numberNPA;
//    private java.io.File fileAdded;
}
