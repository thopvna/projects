package com.thopv.projects.libraryforreader.data.source.mappers;

import com.thopv.projects.libraryforreader.data.source.Mapper;
import com.thopv.projects.libraryforreader.data.source.schemas.SUser;
import com.thopv.projects.libraryforreader.welcome.domain.entity.User;

/**
 * Created by thopv on 12/2/2017.
 */

public class UserMapper implements Mapper<User, SUser> {
    @Override
    public SUser map(User origin) {
        return new SUser(origin.getUserName(), origin.getPassword(), origin.getFullName(), origin.getEmail(), origin.getPhone(), origin.isActive());
    }
}
