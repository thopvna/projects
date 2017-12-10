package com.thopv.projects.libraryforreader.support.communicate;

/**
 * Created by thopv on 11/10/2017.
 */

public class ComplexResponse<T> extends BaseResponse {
    private T payload;
    public ComplexResponse(boolean success, String message, T payload){
        super(success, message);
        this.payload = payload;
    }

    public static <T> ComplexResponse<T> fail(String message){
        return new ComplexResponse<>(false, message, null);
    }
    public static <T> ComplexResponse<T> get(boolean isSuccess){
        return new ComplexResponse<>(isSuccess, "", null);
    }
    public static <T> ComplexResponse<T> success(T payload){
        return new ComplexResponse<>(true , "", payload);
    }
    public static <T> ComplexResponse<T> success(){
        return new ComplexResponse<>(true , "", null);
    }

    public T getPayload() {
        return payload;
    }
}
