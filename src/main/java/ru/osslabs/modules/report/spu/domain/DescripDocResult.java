package ru.osslabs.modules.report.spu.domain;

import lombok.Data;
import ru.osslabs.modules.report.CMDBuildField;
import ru.osslabs.modules.report.domain.CMDField;
import ru.osslabs.modules.report.domain.Lookup;

import java.util.List;

/**
 * Created by Serge Kozyrev on 21.12.15.
 */
@Data
public class DescripDocResult {
    @CMDBuildField(name = "Description")
    private String description;
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
