package com.thopv.projects.libraryforreader.data.source;

/**
 * Created by thopv on 12/2/2017.
 */

public interface Mapper<FROM, TO> {
    TO map(FROM origin);
}
