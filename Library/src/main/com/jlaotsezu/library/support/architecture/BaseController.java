package main.com.jlaotsezu.library.support.architecture;


import main.com.jlaotsezu.library.support.fx.FXController;

public interface BaseController extends FXController {
    void showMessage(String message);
    void showError(String error);
}
