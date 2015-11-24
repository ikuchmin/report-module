package ru.osslabs.modules.report.domain.spu;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.osslabs.modules.report.CMDBuildField;

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
    private String formPeriodSubservice;
    @CMDBuildField(name = "PeriodSubservice_ExTerr")
    private Integer periodSubservice_ExTerr;
    @CMDBuildField(name = "FormPeriodSubservice_ExTer")
    private String formPeriodSubservice_ExTer;
    //    private LOOKUP refusAccept;
    private List<Rejection> reject_noRecept;
    //    private LOOKUP denial;
    private List<Rejection> rejection_noProv;
    //    private LOOKUP suspension;
    private List<Rejection> rejection_noAct;
    private Integer suspension_days;
    @CMDBuildField(name = "FormSuspension_days")
    private String formSuspension_days;
    //    private LOOKUP havePayment;
    private List<Payment> subservice_Payment2;
    private Boolean lichnoVOrgan;
    private Boolean lichnoVTerrOrgan;
    private Boolean lichnoVMFC;
    private Boolean portalGosUslig;
    private Boolean offSiteOrganaUslugi;
    private String adressOffSite;
    private Boolean post;
    private Boolean otherMeth;
    private List<SebserviceAppeal> appealSubServices;
    private Boolean terrOrgOnPaper;
    private Boolean inMFConPaperFrom;
    private Boolean inMFCinDocFromITOrg;
    private Boolean fromCabinetGosUslug;
    private Boolean fromGosUslugInELForm;
    private Boolean fromCabinetOffSite;
    private String addresOffSiteResult;
    private Boolean fromOffSiteElDoc;
    private String addresOffSiteELDoc;
    private Boolean emailDocWithElSignature;
    private Boolean postResult;
    //    private Boolean otherMethods;
    private List<SubserviceResult> getRezultSubServices;
//    private Boolean startDetailsApplicant;
//    private SPECIFICATION fillDetailsApplicant;
//    private Boolean startDocSubservice;
//    private Boolean startResultSubservice;
//    private SPECIFICATION fillResultSubservice;
//    private Boolean startProcessSubservice;
//    private SPECIFICATION fillProcessSubservice;
//    private Boolean startElFormSubservice;
//    private Boolean officialWebsite;
//    private Boolean portalStateServices;
//    private Boolean otherWaysObtaining;
//    private SPECIFICATION otherInformaTerms;
//    private Boolean officialWebsiteAuthority;
//    private String websiteAddresAuthority;
//    private Boolean portalStateSrvices;
//    private Boolean otheRecordiMethods;
//    private SPECIFICATION methoAppointBodies;
//    private Boolean requirPapeDocuments;
//    private Boolean requireDocumenPaper;
//    private Boolean submiDocObtaiResult;
//    private Boolean wayRecoReception;
//    private SPECIFICATION otherReception;
//    private Boolean officialWebProvidiService;
//    private String websitAddrePayMethods;
//    private Boolean publServPayMeth;
//    private Boolean otheMethoPayment;
//    private SPECIFICATION otheMethoPaymStaFee;
//    private Boolean officlWebMetInform;
//    private String websitAddresInformation;
//    private Boolean servicePortaInformation;
//    private Boolean emailApplicant;
//    private Boolean othWayProgress;
//    private SPECIFICATION othMeInforAboCourse;
//    private Boolean officlWebCompla;
//    private String addresComplaint;
//    private Boolean servicPortComplaints;
//    private Boolean infoSystEnsurDecisions;
//    private Boolean wayFilinComplaint;
//    private SPECIFICATION othFlinComplaint;
//    private SPECIFICATION fillDocSubservice1;
//    private String webAddress;
}
