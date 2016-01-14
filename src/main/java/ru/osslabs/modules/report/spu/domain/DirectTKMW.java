package ru.osslabs.modules.report.spu.domain;

import lombok.Data;
import ru.osslabs.modules.report.CMDBuildField;

import java.util.Date;

/**
 * Created by Serge Kozyrev on 12.01.16.
 */
@Data
public class DirectTKMW {
    @CMDBuildField(name = "VidTKMW")
    private Vid_TKMW vidTKMW;
    @CMDBuildField(name = "Data")
    private Date data;
    private String number;
    private String name;
}
