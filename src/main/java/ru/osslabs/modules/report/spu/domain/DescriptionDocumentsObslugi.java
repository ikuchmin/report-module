package ru.osslabs.modules.report.spu.domain;

import lombok.Data;
import ru.osslabs.modules.report.CMDBuildField;
import ru.osslabs.modules.report.domain.CMDField;
import ru.osslabs.modules.report.domain.Lookup;

import java.util.List;

/**
 * Created by Serge Kozyrev on 15.12.15.
 */
@Data
public class DescriptionDocumentsObslugi {
    @CMDBuildField(name = "Description")
    private String description;
    @CMDBuildField(name = "Documents3")
    private Document document;
    @CMDBuildField(name = "ParentDocument1")
    private Document parentDocument;
    @CMDBuildField(name = "InstancesNumber")
    private Integer instancesNumber;
    @CMDBuildField(name = "TypeDocument")
    Lookup<String> typeDocument;
    @CMDBuildField(name = "IdentificationApplicant")
    private CMDField<Boolean> identificationApplicant;
    @CMDBuildField(name = "VerificatOriginal")
    private CMDField<Boolean> verificatOriginal;
    @CMDBuildField(name = "RemovingCopy")
    private CMDField<Boolean> removingCopy;
    @CMDBuildField(name = "FormatCase")
    private CMDField<Boolean> formatCase;
    @CMDBuildField(name = "ActionsDocument")
    private List<ActionsDocument> actionsDocument;
    @CMDBuildField(name = "DocumAvailaCondition")
    private Boolean documAvailaCondition;
    @CMDBuildField(name = "RefCondition")
    private List<RefCondit> refCondition;
    @CMDBuildField(name = "RequirementsDocument")
    private String requirementsDocument;
    @CMDBuildField(name = "FormDocument")
    private String formDocument;
    @CMDBuildField(name = "SampleDocument")
    private String sampleDocument;


}


