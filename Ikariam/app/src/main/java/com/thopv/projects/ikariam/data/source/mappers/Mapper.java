package com.thopv.projects.ikariam.data.source.mappers;

/**
 * Created by thopv on 11/21/2017.
 */

public interface Mapper<FROM, TO> {
    TO map(FROM source);
}
