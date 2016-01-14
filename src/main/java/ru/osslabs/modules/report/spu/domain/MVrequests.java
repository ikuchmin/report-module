package ru.osslabs.modules.report.spu.domain;

import lombok.Data;
import ru.osslabs.modules.report.CMDBuildField;
import ru.osslabs.modules.report.domain.Lookup;

/**
 * Created by Serge Kozyrev on 12.01.16.
 */
@Data
public class MVrequests {
    @CMDBuildField(name = "InformationContent")
    private String informationContent;
    @CMDBuildField(name = "NamOrgGuiInteRequest2")
    private OrgGovernment namOrgGuiInteRequest;
    @CMDBuildField(name = "NamAuthSeInteReq3")
    private OrgGovernment mamAuthSeInteReq;
    @CMDBuildField(name = "SIDElectService")
    private String sidElectService;
    @CMDBuildField(name = "RequestExecutionTerm")
    private Integer requestExecutionTerm;
    private Lookup<String> unitAllrequest;
    private Integer requestingMV;
    private Lookup<String> unitrequestingMV;
    private Integer directionResponse;
    private Lookup<String> unitdirectionResponse;
    private Integer communionResponse;
    @CMDBuildField(name = "ComResponse")
    private Lookup<String> comResponse;
    private String replyFormMV;
    private String patternResponseMV;
    @CMDBuildField(name = "FileFormInReqText")
    private String fileFormInReqText;
    @CMDBuildField(name = "FileFillPatText")
    private String fileFillPatText;

}
