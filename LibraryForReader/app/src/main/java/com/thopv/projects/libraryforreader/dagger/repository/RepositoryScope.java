package com.thopv.projects.libraryforreader.dagger.repository;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Scope;

/**
 * Created by jlaotsezu on 23/11/2017.
 */
@Scope
@Retention(value = RetentionPolicy.RUNTIME)
public @interface RepositoryScope {
}
