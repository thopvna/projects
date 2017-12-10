package com.thopv.projects.libraryforreader.dagger.usecase;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Scope;

/**
 * Created by thopv on 12/3/2017.
 */
@Scope
@Retention(value = RetentionPolicy.RUNTIME)
public @interface UseCaseScope {
}
