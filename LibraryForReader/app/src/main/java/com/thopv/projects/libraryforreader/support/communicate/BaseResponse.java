package com.thopv.projects.libraryforreader.support.communicate;

/**
 * Created by thopv on 11/9/2017.
 */

public class BaseResponse {
    private boolean success;
    private String message;

    public BaseResponse(boolean success, String message) {
        this.success = success;
        this.message = message;
    }
    public boolean isSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }
}
