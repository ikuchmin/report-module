package ru.osslabs.modules.report.domain;

import javaslang.control.Option;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by ikuchmin on 01.12.15.
 */
@Data
public class CMDField<T> {
    private final Option<T> value;
    private final String description;

    /**
     * @param value       can be true/false/null
     * @param description
     */
    public CMDField(T value, String description) {
        this.value = Option.of(value);
        this.description = description;
    }

    public CMDField() {
        this(null, "Поле не определено");
    }

    public static <T> CMDField<T> of(T value, String description) {
        return new CMDField<>(value, description);
    }
}
