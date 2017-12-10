package main.com.jlaotsezu.library.support.communicate;

public class FailedResponse<T> extends ComplexResponse<T> {

    public FailedResponse(String message) {
        super(false, message,null);
    }
}
