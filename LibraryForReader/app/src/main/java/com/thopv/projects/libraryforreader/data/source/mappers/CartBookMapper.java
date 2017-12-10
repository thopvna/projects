package com.thopv.projects.libraryforreader.data.source.mappers;

import com.thopv.projects.libraryforreader.data.source.Mapper;
import com.thopv.projects.libraryforreader.data.source.schemas.SCartBook;
import com.thopv.projects.libraryforreader.home.domain.entity.CartBook;

/**
 * Created by thopv on 12/2/2017.
 */

public class CartBookMapper implements Mapper<CartBook, SCartBook> {
    @Override
    public SCartBook map(CartBook origin) {
        return new SCartBook(origin.getBookId());
    }
}
