package main.com.jlaotsezu.library.support.communicate;

import com.google.gson.Gson;

/**
 * Class này dùng nhằm hỗ trợ việc giao tiếp giữa các đối tượng.
 */

public class BaseResponse {
    private String message;
    private boolean success;

    public BaseResponse(String message, boolean success) {
        this.message = message;
        this.success = success;
    }
    public BaseResponse(boolean success, String message) {
        this.message = message;
        this.success = success;
    }
    public BaseResponse(boolean success) {
        this.message = "";
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }
}
