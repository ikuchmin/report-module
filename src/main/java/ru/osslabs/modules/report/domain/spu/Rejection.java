package ru.osslabs.modules.report.domain.spu;

import ru.osslabs.modules.report.CMDBuildField;

/**
 * Created by ikuchmin on 23.11.15.
 */
public class Rejection {
    @CMDBuildField(name = "Description")
    private String description;

    public Rejection(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
