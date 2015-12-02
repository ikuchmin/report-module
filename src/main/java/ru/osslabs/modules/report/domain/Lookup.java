package ru.osslabs.modules.report.domain;

import lombok.Data;

/**
 * On the moment is support only for Lookup<String>
 * Created by ikuchmin on 25.11.15.
 */
@Data
public class Lookup<T> {
    T value;

    public Lookup() {
    }

    public Lookup(T value) {
        this.value = value;
    }
}
