package main.com.jlaotsezu.library.support.communicate;

public class SuccessResponse<T> extends ComplexResponse<T> {
    public SuccessResponse(T payload) {
        super(true, "", payload);
    }
}
