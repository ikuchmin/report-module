package ru.osslabs.modules.report.spu.domain;

import lombok.Data;
import lombok.NoArgsConstructor;
import ru.osslabs.modules.report.CMDBuildField;

import java.util.List;

/**
 * Created by ikuchmin on 23.11.15.
 */
@Data
@NoArgsConstructor
public class Payment {
    @CMDBuildField(name = "Description")
    private String description;
    private Integer sizepayment;
    private List<Normative> payment_npa;
    private String kbk_MFC;
    private String kbk_OGV;
    private String pointForPayment;
    private String message;
//    private String payment_npa_type;
//    private String payment_ogv_npa;
//    private String payment_date_npa;
//    private String payment_number_npa;
//    private String payment_name_npa;
}
