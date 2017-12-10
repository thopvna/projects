package main.com.jlaotsezu.library.support.fx;

import javafx.scene.Parent;
import main.com.jlaotsezu.library.App;
import main.com.jlaotsezu.library.support.architecture.Intent;

public interface FXController {
    void onInjectContext(App app, Intent intent, Parent viewContainer);
    void onHasMessage(Intent intent);
    Parent getViewContainer();
}
