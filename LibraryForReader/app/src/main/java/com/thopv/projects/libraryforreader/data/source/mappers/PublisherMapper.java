package com.thopv.projects.libraryforreader.data.source.mappers;

import com.thopv.projects.libraryforreader.data.source.Mapper;
import com.thopv.projects.libraryforreader.data.source.schemas.SPublisher;
import com.thopv.projects.libraryforreader.home.domain.entity.Publisher;

/**
 * Created by thopv on 12/2/2017.
 */

public class PublisherMapper implements Mapper<Publisher, SPublisher> {
    @Override
    public SPublisher map(Publisher origin) {
        return new SPublisher(origin.getPublisherId(), origin.getPublisherName(), origin.getAddress());
    }
}
