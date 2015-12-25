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
    private CMDField<Boolean> beResult;
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

    @CMDBuildField(name = "Requirements")
    private String requirements;
    private Lookup<String> characterOfResult;
    private CMDField<Boolean> terrOrgOnPaper;
    private CMDField<Boolean> inMFConPaperFrom;
    private CMDField<Boolean> inMFCinDocFromITOrg;
    private CMDField<Boolean> fromCabinetGosUslug;
    private CMDField<Boolean> fromGosUslugInELForm;
    private CMDField<Boolean> fromCabinetOffSite;
    private String addresOffSiteResult;
    private CMDField<Boolean> fromOffSiteElDoc;
    private String addresOffSiteELDoc;
    private CMDField<Boolean> emailDocWithElSignature;
    private CMDField<Boolean> postResult;
    private List<SubserviceResult> getRezultSubServices;
    private Integer numberOfDays;
    private Lookup<String> unitOfMeasure1;
    private Integer numOfDays;
    private Lookup<String> unitOfMeas1;
    private String docForm;
    private String docExample;
}


