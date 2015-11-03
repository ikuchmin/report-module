package ru.osslabs.modules.report.domain;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by ikuchmin on 02.11.15.
 */
public class Service {
    Integer id;

    Set<String> subServices;
    Set<String> qualityRatings;

    public Service(Integer id) {
        this.id = id;
        subServices = new HashSet<>();
        qualityRatings = new HashSet<>();
    }

    public Integer getId() {
        return id;
    }

    public Set<String> getSubServices() {
        return subServices;
    }

    public Set<String> getQualityRatings() {
        return qualityRatings;
    }

    public Service addSubService(String subService) {
        subServices.add(subService);
        return this;
    }

    public Service addQualityRating(String qualityRating) {
        qualityRatings.add(qualityRating);
        return this;
    }

}
