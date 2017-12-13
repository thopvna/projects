package com.thopv.projects.ikariam.supports.async;

import com.thopv.projects.ikariam.ComplexResponse;

/**
 * Created by thopv on 10/28/2017.
 */

public interface Callback<T> {
    void onResult(ComplexResponse<T> result);
}
