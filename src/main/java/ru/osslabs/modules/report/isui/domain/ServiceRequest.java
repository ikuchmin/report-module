package ru.osslabs.modules.report.isui.domain;

import lombok.Data;
import ru.osslabs.modules.report.CMDBuildField;

/**
 * Created by Serge Kozyrev on 05.02.16.
 */
@Data
public class ServiceRequest {
    @CMDBuildField(name = "Code")
    private String code;
    @CMDBuildField(name = "Description")
    private String description;
    @CMDBuildField(name = "UrgencyREF")
    private Urgency urgency;
    @CMDBuildField(name = "ImpactSelectREF")
    private ImpactSelect impactSelect;
    @CMDBuildField(name = "SouceIncomeREF")
    private SouceIncome souceIncome;
    @CMDBuildField(name = "OST")
    private String ost;
    @CMDBuildField(name = "TU")
    private String tu;
    @CMDBuildField(name = "LinePart_NPS")
    private String linePart_NPS;

}
