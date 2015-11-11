package ru.osslabs.modules.report;

/**
 * Created by ikuchmin on 09.11.15.
 */
public enum ExportType {
    image("Image"),
    txt("Text"),
    html("HTML"),
    pdf("PDF"),
    rtf("RTF"),
    csv("CSV"),
    odt("ODT"),
    docx("DOCX"),
    xls("XLS"),
    xlsx("XLSX"),
    pptx("PPTX"),
    json("JSON"),
    xml("XML");

    private String longName;

    private ExportType(String longName) {
        this.longName = longName;
    }

    public String getLongName() {
        return this.longName;
    }
}
