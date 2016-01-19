package ru.osslabs.modules.report.domain;

import lombok.*;

import java.util.List;

/**
 * Created by Serge Kozyrev on 18.01.16.
 */
@Data
@NoArgsConstructor
public class FileList {
    List files;

    public FileList(List files) {
        this.files = files;
    }
}
