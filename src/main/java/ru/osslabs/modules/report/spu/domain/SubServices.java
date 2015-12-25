package ru.osslabs.modules.report.spu.domain;

import lombok.Data;
import lombok.NoArgsConstructor;
import ru.osslabs.modules.report.CMDBuildField;
import ru.osslabs.modules.report.domain.CMDField;
import ru.osslabs.modules.report.domain.Lookup;

import java.util.List;

/**
 * Created by ikuchmin on 23.11.15.
 */

//@Builder
@Data
@NoArgsConstructor
public class SubServices {
    //    private REFERENCE obj;
//    private SPECIFICATION service;
//    private SPECIFICATION objAppeal;
    private String namesubservice;
    //    private String description;
//    private String desc2;
    private Integer periodsubservice;
    @CMDBuildField(name = "FormPeriodSubservice")
    private Lookup<String> formPeriodSubservice;
    @CMDBuildField(name = "PeriodSubservice_ExTerr")
    private Integer periodSubservice_ExTerr;
    @CMDBuildField(name = "FormPeriodSubservice_ExTer")
    private Lookup<String> formPeriodSubservice_ExTer;
    //    private LOOKUP refusAccept;
    private List<Rejection> reject_noRecept;
    //    private LOOKUP denial;
    private List<Rejection> rejection_noProv;
    //    private LOOKUP suspension;
    private List<Rejection> rejection_noAct;
    private Integer suspension_days;
    @CMDBuildField(name = "FormSuspension_days")
    private Lookup<String> formSuspension_days;
    //    private LOOKUP havePayment;
    private List<Payment> subservice_Payment2;
    private CMDField<Boolean> lichnoVOrgan;
    private CMDField<Boolean> lichnoVTerrOrgan;
    private CMDField<Boolean> lichnoVMFC;
    private CMDField<Boolean> portalGosUslig;
    private CMDField<Boolean> offSiteOrganaUslugi;
    private String adressOffSite;
    private CMDField<Boolean> post;
    private Boolean otherMeth;
    private List<SebserviceAppeal> appealSubServices;
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
    //    private Boolean otherMethods;
    private List<SubserviceResult> getRezultSubServices;
    @CMDBuildField(name = "FillDocSubservice1")
    private List<DescriptionDocumentsObslugi> fillDocSubservice1;
    @CMDBuildField(name = "FillResultSubservice2")
    private List<DescriptionDocumentsObslugi> fillResultSubservice2;
    @CMDBuildField(name = "OfficialWebsite")
    private CMDField<Boolean> officialWebsite;
    @CMDBuildField(name = "WebAddress")
    private String webAddress;
    @CMDBuildField(name = "PortalStateServices")
    private CMDField<Boolean> portalStateServices;
    @CMDBuildField(name = "OtherWaysObtaining")
    private CMDField<Boolean> otherWaysObtaining;
    @CMDBuildField(name = "OtherInformaTerms")
    private List<ProductInformatSubser> otherInformaTerms;
    @CMDBuildField(name = "OfficialWebsiteAuthorit")
    private CMDField<Boolean> officialWebsiteAuthority;
    @CMDBuildField(name = "WebsiteAddresAuthority")
    private String websiteAddresAuthority;
    @CMDBuildField(name = "PortalStateSrvices")
    private CMDField<Boolean> portalStateSrvices;
    @CMDBuildField(name = "MethoAppointBodies")
    private List<OtheMethoAppointm> methoAppointBodies;
    @CMDBuildField(name = "RequirPapeDocuments")
    private CMDField<Boolean> requirPapeDocuments;
    @CMDBuildField(name = "RequireDocumenPaper")
    private CMDField<Boolean> requireDocumenPaper;
    @CMDBuildField(name = "SubmiDocObtaiResult")
    private CMDField<Boolean> submiDocObtaiResult;
    @CMDBuildField(name = "WayRecoReception")
    private CMDField<Boolean> wayRecoReception;
    @CMDBuildField(name = "OtherReception")
    private List<WayReceptRequest> otherReception;
    @CMDBuildField(name = "OfficialWebProvidiService")
    private CMDField<Boolean> officialWebProvidiService;
    @CMDBuildField(name = "WebsitAddrePayMethods")
    private String websitAddrePayMethods;
    @CMDBuildField(name = "PublServPayMeth")
    private CMDField<Boolean> publServPayMeth;
    @CMDBuildField(name = "OtheMethoPayment")
    private CMDField<Boolean> otheMethoPayment;
    @CMDBuildField(name = "OtheMethoPaymStaFee")
    private List<OtheMethodPaymen> otheMethoPaymStaFee;
    @CMDBuildField(name = "OfficlWebMetInform")
    private CMDField<Boolean> officlWebMetInform;
    @CMDBuildField(name = "WebsitAddresInformation")
    private String websitAddresInformation;
    @CMDBuildField(name = "ServicePortaInformation")
    private CMDField<Boolean> servicePortaInformation;
    @CMDBuildField(name = "EmailApplicant")
    private CMDField<Boolean> emailApplicant;
    @CMDBuildField(name = "OthMeInforAboCourse")
    private List<MethObtProgresInfo> othMeInforAboCourse;
    @CMDBuildField(name = "OfficlWebCompla")
    private CMDField<Boolean> officlWebCompla;
    @CMDBuildField(name = "AddresComplaint")
    private String addresComplaint;
    @CMDBuildField(name = "ServicPortComplaints")
    private CMDField<Boolean> servicPortComplaints;
    @CMDBuildField(name = "InfoSystEnsurDecisions")
    private CMDField<Boolean> infoSystEnsurDecisions;
    @CMDBuildField(name = "WayFilinComplaint")
    private CMDField<Boolean> wayFilinComplaint;
    @CMDBuildField(name = "OthFlinComplaint")
    private List<OthWayFiliComplaint> othFlinComplaint;

//    private Boolean startDetailsApplicant;
//    private SPECIFICATION fillDetailsApplicant;
//    private Boolean startDocSubservice;
//    private Boolean startResultSubservice;
//    private Boolean startProcessSubservice;
//    private SPECIFICATION fillProcessSubservice;
//    private Boolean startElFormSubservice;
//    private Boolean otheRecordiMethods;
//    private Boolean othWayProgress;
}
