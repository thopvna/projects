package com.thopv.projects.ikariam;

/**
 * Created by thopv on 10/28/2017.
 */

public class ComplexResponse<T> {
    private String message;
    private boolean success;
    private T t;

    public ComplexResponse(boolean success, String message, T t) {
        this.message = message;
        this.success = success;
        this.t = t;
    }

    public ComplexResponse(boolean success, T t) {
        this.success = success;
        message = "";
        this.t = t;
    }

    /**
     * instead = get()
     * @param payload
     * @param <T>
     * @return
     */
    @Deprecated
    public static  <T> ComplexResponse<T> success(T payload){
        return new ComplexResponse<T>(true, "", payload);
    }

    /**
     * instead = get()
     * @param <T>
     * @return
     */
    @Deprecated
    public static <T> ComplexResponse<T> success(){
        return new ComplexResponse<>(true, "", null);
    }

    public static <T> ComplexResponse<T> fail(String message){
        return new ComplexResponse<>(false, message, null);
    }
    public static <T> ComplexResponse<T> get(boolean success){
        return new ComplexResponse<T>(success, "", null);
    }
    public static <T> ComplexResponse<T> get(T payload){
        return ComplexResponse.success(payload);
    }

    public ComplexResponse(boolean success, String message) {
        this.message = message;
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
    public T getPayload(){
        return t;
    }
}
