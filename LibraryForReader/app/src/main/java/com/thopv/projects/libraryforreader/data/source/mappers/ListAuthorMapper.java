package com.thopv.projects.libraryforreader.data.source.mappers;

import com.thopv.projects.libraryforreader.data.source.Mapper;
import com.thopv.projects.libraryforreader.data.source.schemas.SAuthor;
import com.thopv.projects.libraryforreader.home.domain.entity.Author;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by thopv on 12/2/2017.
 */

public class ListAuthorMapper implements Mapper<List<Author>, List<SAuthor>> {
    @Override
    public List<SAuthor> map(List<Author> origin) {
        List<SAuthor> sAuthors = new LinkedList<>();
        if(origin == null)return sAuthors;
        for(Author author : origin){
            sAuthors.add(new SAuthor(author.getAuthorId(), author.getAuthorName(), author.getBirthDay(), author.getDeathDay(), author.getAddress()));
        }
        return sAuthors;
    }
}
