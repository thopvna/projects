package com.thopv.projects.libraryforreader.data.source.mappers;

import com.thopv.projects.libraryforreader.data.source.Mapper;
import com.thopv.projects.libraryforreader.data.source.schemas.SAuthor;
import com.thopv.projects.libraryforreader.home.domain.entity.Author;

/**
 * Created by thopv on 12/2/2017.
 */

public class AuthorMapper implements Mapper<Author, SAuthor> {
    @Override
    public SAuthor map(Author origin) {
        return new SAuthor(origin.getAuthorId(), origin.getAuthorName(), origin.getBirthDay(), origin.getDeathDay(), origin.getAddress());
    }
}
