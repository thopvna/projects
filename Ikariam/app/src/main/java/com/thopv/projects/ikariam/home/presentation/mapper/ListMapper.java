package com.thopv.projects.ikariam.home.presentation.mapper;

import java.util.List;

/**
 * Created by thopv on 11/21/2017.
 */

public interface ListMapper<FROM, TO> {
    List<TO> map(List<FROM> entities);
}
