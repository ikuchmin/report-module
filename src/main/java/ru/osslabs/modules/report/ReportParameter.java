package ru.osslabs.modules.report;

/**
 * Created by ikuchmin on 09.11.15.
 */
public class ReportParameter {
    private final Class<?> typeParameter;
    private final String code;

    /**
     * Description may be deprecated because in platform use messages
     */
    private final String description;

    public ReportParameter(String code, String description, Class<?> typeParameter) {
        this.typeParameter = typeParameter;
        this.code = code;
        this.description = description;
    }

    public String getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }

    public Class<?> getTypeParameter() {
        return typeParameter;
    }
}
