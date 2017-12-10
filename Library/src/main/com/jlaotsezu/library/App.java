package main.com.jlaotsezu.library;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import main.com.jlaotsezu.library.support.architecture.Intent;
import main.com.jlaotsezu.library.support.fx.FXController;
import main.com.jlaotsezu.library.support.resources.URLProvider;
import main.com.jlaotsezu.library.support.utils.DialogUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.IOException;

public class App extends Application{
    private Stage container;

    private ApplicationContext context;

    public ApplicationContext getContext() {
        return context;
    }

    @Override
    public void start(Stage container) throws Exception {
        this.container = container;
        container.setTitle("System Library");
        context = new ClassPathXmlApplicationContext("/config/spring-config.xml");

        Intent intent = new Intent(URLProvider.getLoginURL());
        startController(intent);
    }
    public void startController(Intent intent){
        FXController controller = loadController(intent);
        show(controller.getViewContainer());
    }
    public <T extends FXController> T loadController(Intent intent){
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(intent.getUrl());
            fxmlLoader.setControllerFactory(context::getBean);
            Parent view = fxmlLoader.load();
            T controller = fxmlLoader.getController();
            controller.onInjectContext(this, intent, view);
            return controller;
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
    }
    public void show(Parent view){
        Scene scene = new Scene(view);
        container.setScene(scene);
        container.show();
    }

    public void showDialog(String title, Parent dialog, DialogUtils.OnConfirmEvent event) {
        DialogUtils.showDialog(title, dialog, event);
    }
}
