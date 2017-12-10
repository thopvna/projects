package com.thopv.projects.libraryforreader.data.source.mappers;

import com.thopv.projects.libraryforreader.data.source.Mapper;
import com.thopv.projects.libraryforreader.data.source.schemas.SClassification;
import com.thopv.projects.libraryforreader.home.domain.entity.Classification;

/**
 * Created by thopv on 12/2/2017.
 */

public class ClassificationMapper implements Mapper<Classification, SClassification> {
    @Override
    public SClassification map(Classification origin) {
        return new SClassification(origin.getClassificationId(), origin.getClassificationName());
    }
}
