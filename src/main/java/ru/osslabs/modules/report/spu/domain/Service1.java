package ru.osslabs.modules.report.spu.domain;

import ru.osslabs.modules.report.CMDBuildField;

/**
 * Created by Serge Kozyrev on 14.12.15.
 */
public class Service1 {
    @CMDBuildField(name = "Description")
    private String description;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
