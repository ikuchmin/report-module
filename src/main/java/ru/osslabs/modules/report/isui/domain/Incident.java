package ru.osslabs.modules.report.isui.domain;

import lombok.Data;
import ru.osslabs.modules.report.CMDBuildField;

import java.util.*;

/**
 * Created by Serge Kozyrev on 02.02.16.
 */
@Data
public class Incident {
    @CMDBuildField(name = "OST")
    private String ost;
    @CMDBuildField(name = "RNU")
    private String rnu;
    @CMDBuildField(name = "Pipeline")
    private String pipeline;
    @CMDBuildField(name = "TU")
    private String tu;
    @CMDBuildField(name = "LocationEvent")
    private String locationEvent;
    @CMDBuildField(name = "Valves")
    private String valves;
    @CMDBuildField(name = "ValvesCreate")
    private Date valvesCreate;
    @CMDBuildField(name = "ValvesStart")
    private Date valvesStart;
    @CMDBuildField(name = "ValvesKapRem")
    private Date valvesKapRem;
    @CMDBuildField(name = "LastTO")
    private String lastTO;
    @CMDBuildField(name = "LastCheck")
    private String lastCheck;
    @CMDBuildField(name = "DriverBrand")
    private String driverBrand;
    @CMDBuildField(name = "IncReason")
    private String incReason;
    @CMDBuildField(name = "NearNPS")
    private String nearNPS;
    @CMDBuildField(name = "Employee")
    private String employee;
    @CMDBuildField(name = "SDKU")
    private String sdku;
    @CMDBuildField(name = "OtherPerson")
    private String otherPerson;
    @CMDBuildField(name = "ReactionTime")
    private String reactionTime;
    @CMDBuildField(name = "ReactioTimeFact")
    private String reactionTimeFact;
    @CMDBuildField(name = "Down")
    private String down;
    @CMDBuildField(name = "DownValue")
    private String downValue;
    @CMDBuildField(name = "PlanRepair")
    private String planRepair;
    @CMDBuildField(name = "Likvidator")
    private String likvidator;
    @CMDBuildField(name = "Sostav")
    private String sostav;
    @CMDBuildField(name = "SvyazTNT")
    private String svyazTNT;
    @CMDBuildField(name = "EventTimeDate")
    private Date eventTimeDate;
    @CMDBuildField(name = "Working")
    private List<Working> working;
}
