package ru.osslabs.modules.report.isui.domain;

import lombok.Data;
import ru.osslabs.modules.report.CMDBuildField;

import java.util.Date;

/**
 * Created by Serge Kozyrev on 03.02.16.
 */
@Data
public class Working {
    @CMDBuildField(name = "FullDescription")
    private String fullDescription;
    @CMDBuildField(name = "ControlDate2")
    private Date controlDate;
    private String ispolnitel;
}
