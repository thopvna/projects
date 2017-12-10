package main.com.jlaotsezu.library.support.communicate;

import com.google.gson.Gson;
import com.sun.istack.internal.Nullable;

/**
 * Hỗ trợ giao tiếp giữa các đối tượng, nó có thể chứa payload
 * @param <T>
 */

public class ComplexResponse<T> {
    private T payload;
    private boolean success;
    private String message;
    public ComplexResponse(boolean status, String message, @Nullable T payload){
        this.success = status;
        this.message = message;
        this.payload = payload;
    }
    public ComplexResponse(boolean status, String message){
        this.success = status;
        this.message = message;
    }

    private static <T> ComplexResponse<T> createNonPayload(boolean status, String message){
        return new ComplexResponse<>(status, message, null);
    }

    private static <T> ComplexResponse<T> createNonMessage(boolean status, T payload){
        return new ComplexResponse<T>(status, "", payload);
    }

    public static <T> ComplexResponse<T> get(boolean success){
        return new ComplexResponse<T>(success, "", null);
    }

    public static <T> ComplexResponse<T> fail(String message){
        return createNonPayload(false, message);
    }
    public static <T> ComplexResponse<T> success(T payload){
        return createNonMessage(true, payload);
    }
    public static <T> ComplexResponse<T> success(){
        return createNonMessage(true, null);
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getPayload() {
        return payload;
    }

    public void setPayload(T payload) {
        this.payload = payload;
    }

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }
}
