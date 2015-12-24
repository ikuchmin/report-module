package ru.osslabs.modules.report.spu.domain;

import lombok.Data;
import ru.osslabs.modules.report.CMDBuildField;

/**
 * Created by Serge Kozyrev on 24.12.15.
 */
@Data
public class OtheMethoAppointm {
    @CMDBuildField(name = "Description")
    private String description;
    @CMDBuildField(name = "OtherMethodAppointment")
    private String otherMethodAppointment;

}
