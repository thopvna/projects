package com.thopv.projects.libraryforreader.home.domain.specifications;

import com.thopv.projects.libraryforreader.data.source.Specification;

/**
 * Created by thopv on 12/2/2017.
 */

public class FindBookByClassificationName implements Specification {
    private String keyword;

    public FindBookByClassificationName(String keyword) {
        this.keyword = keyword;
    }

    public String getKeyword() {
        return keyword;
    }
}
