package com.thopv.projects.ikariam.config;

import com.thopv.projects.ikariam.data.source.repositories.AttackTroopRepository;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Scope;

/**
 * Created by thopv on 11/20/2017.
 */
@Scope
@Retention(value = RetentionPolicy.RUNTIME)
public @interface RepositoryScope {

}
