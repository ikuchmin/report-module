package ru.osslabs.modules.report;

/**
 * Created by ikuchmin on 09.11.15.
 */
public class ReportParameter<T> {
    private final Class<T> typeParameter;
    private final String code;

    /**
     * Description may be deprecated because in platform use messages
     */
    private final String description;

    /**
     * This used for work view in platform
     */
    private final String itemExpr;

    /**
     * This used for work view in platform
     */
    private final T defaultValue;

    /**
     * This used for work view in platform
     */
    private final boolean optional;

    public ReportParameter(String code, String description, Class<T> typeParameter, T defaultValue, boolean optional) {
        this(code, description, typeParameter, "", defaultValue, optional);
    }

    public ReportParameter(String code, String description, Class<T> typeParameter, String itemExpr, T defaultValue, boolean optional) {
        this.typeParameter = typeParameter;
        this.code = code;
        this.description = description;
        this.itemExpr = itemExpr;
        this.defaultValue = defaultValue;
        this.optional = optional;
    }

    public String getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }

    public Class<T> getTypeParameter() {
        return typeParameter;
    }

    public String getItemExpr() {
        return itemExpr;
    }

    public T getDefaultValue() {
        return defaultValue;
    }

    public boolean isOptional() {
        return optional;
    }
}
