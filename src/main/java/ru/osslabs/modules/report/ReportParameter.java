package ru.osslabs.modules.report;

/**
 * Created by ikuchmin on 09.11.15.
 */
public class ReportParameter {
    private final Class<?> typeParameter;
    private final String name;
    private final String description;

    //    private final

    public ReportParameter(String name, String description, Class<?> typeParameter) {
        this.typeParameter = typeParameter;
        this.name = name;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Class<?> getTypeParameter() {
        return typeParameter;
    }
}
