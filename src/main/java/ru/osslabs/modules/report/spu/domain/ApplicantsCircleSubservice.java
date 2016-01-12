package ru.osslabs.modules.report.spu.domain;

import lombok.Data;
import ru.osslabs.modules.report.CMDBuildField;
import ru.osslabs.modules.report.domain.CMDField;

import java.util.List;

/**
 * Created by Serge Kozyrev on 25.12.15.
 */
@Data
public class ApplicantsCircleSubservice {
    @CMDBuildField(name = "Description")
    private String description;
    @CMDBuildField(name = "ApplicantsCategorySubservice")
    private List<PersonsEntitledReceivePodology> applicantsCategorySubservice;
    @CMDBuildField(name = "ClaimRepreApplicant")
    private CMDField<Boolean> claimRepreApplicant;
    @CMDBuildField(name = "PerStatemBehApplicant")
    private List<DescribeRepresentativesApplicants> perStatemBehApplicant;
}
