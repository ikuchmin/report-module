package ru.osslabs.modules.report.domain.spu;

/**
 * Created by ikuchmin on 23.11.15.
 */
public class Rejection {
    private String description;

    public Rejection(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
