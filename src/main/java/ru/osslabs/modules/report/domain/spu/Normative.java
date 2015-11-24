package ru.osslabs.modules.report.domain.spu;

import java.util.List;

/**
 * Created by ikuchmin on 23.11.15.
 */
public class Normative {
    private String nameNPA;
    private String description;
    private java.util.Date dateNPA;
//    private String ogv_NPA_desc;
    private List<OgvGovernment> ogv_NPA;
    private String tYPE_NPA_Desc;
    private List<NormativeType> tYPE_NPA;
    private String numberNPA;
//    private java.io.File fileAdded;
}
