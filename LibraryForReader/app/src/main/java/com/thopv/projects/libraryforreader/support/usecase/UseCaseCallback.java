package com.thopv.projects.libraryforreader.support.usecase;

import com.thopv.projects.libraryforreader.support.communicate.ComplexResponse;

/**
 * Created by jlaotsezu on 23/11/2017.
 */
public interface UseCaseCallback<R> {
    void onCompleted(ComplexResponse<R> response);
}
