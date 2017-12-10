package com.thopv.projects.libraryforreader.data.source.mappers;

import com.thopv.projects.libraryforreader.data.source.Mapper;
import com.thopv.projects.libraryforreader.data.source.schemas.SFavoriteBook;
import com.thopv.projects.libraryforreader.home.domain.entity.FavoriteBook;

/**
 * Created by thopv on 12/2/2017.
 */

public class FavoriteBookMapper implements Mapper<FavoriteBook, SFavoriteBook> {
    @Override
    public SFavoriteBook map(FavoriteBook origin) {
        return new SFavoriteBook(origin.getBookId());
    }
}
