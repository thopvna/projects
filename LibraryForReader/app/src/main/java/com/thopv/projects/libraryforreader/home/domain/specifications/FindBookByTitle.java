package com.thopv.projects.libraryforreader.home.domain.specifications;

import com.thopv.projects.libraryforreader.data.source.Specification;

/**
 * Created by thopv on 12/2/2017.
 */

public class FindBookByTitle implements Specification {
    private String keyword;

    public FindBookByTitle(String keyword) {
        this.keyword = keyword;
    }

    public String getKeyword() {
        return keyword;
    }
}
