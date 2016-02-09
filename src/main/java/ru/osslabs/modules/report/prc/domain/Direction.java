package ru.osslabs.modules.report.prc.domain;

import lombok.Data;
import ru.osslabs.modules.report.CMDBuildField;

import java.util.Date;

/**
 * Created by Serge Kozyrev on 08.02.16.
 */
@Data
public class Direction {
    @CMDBuildField(name = "Description")
    private String description;
    @CMDBuildField(name = "RegTime")
    private String regTime;
    private Date remind;
    private String ispolnitel;
    @CMDBuildField(name = "numbe")
    private String number;
    @CMDBuildField(name = "Name")
    private String name;
    private String documentType;
    private String statusCode;
}
