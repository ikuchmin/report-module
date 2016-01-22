package ru.osslabs.modules.report.spu.domain;

import lombok.Data;
import lombok.NoArgsConstructor;
import ru.osslabs.modules.report.CMDBuildField;
import ru.osslabs.modules.report.domain.Lookup;

/**
 * Created by ikuchmin on 23.11.15.
 */
@Data
@NoArgsConstructor
public class Rejection {
    @CMDBuildField(name = "Description")
    private String description;

    @CMDBuildField(name = "PeriodSuspension")
    private String periodSuspension;
    @CMDBuildField(name = "UnitTerms")
    Lookup<String> unitTerms;

    public Rejection(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
