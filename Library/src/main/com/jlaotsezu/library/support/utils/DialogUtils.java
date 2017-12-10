package main.com.jlaotsezu.library.support.utils;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class DialogUtils {
    public static void showMessage(String title, String message){
        Stage window = new Stage();
        window.initModality(Modality.WINDOW_MODAL);
        window.setTitle(title);
        window.setMinWidth(250);

        Label label = new Label();
        label.setText(message);
        Button closeButton = new Button("Đóng");
        closeButton.setOnAction(event -> {
            window.close();
        });
        VBox layout = new VBox(10);
        layout.getChildren().addAll(label, closeButton);
        layout.setAlignment(Pos.CENTER);

        layout.setPadding(new Insets(10, 10, 10, 10));

        layout.setOnKeyPressed(event -> {
            if(event.getCode() == KeyCode.ENTER){
                window.close();
            }
        });

        Scene scene = new Scene(layout);
        window.setScene(scene);
        window.showAndWait();
    }
    public interface OnConfirmEvent{
        void onConfirm();
    }
    public static void showConfirm(String title, String message, OnConfirmEvent confirmEvent){
        Stage window = new Stage();
        window.initModality(Modality.WINDOW_MODAL);
        window.setTitle(title);
        window.setMinWidth(250);

        Label messageLabel = new Label(message);

        Button positiveButton = new Button("Chấp nhận");
        Button negativeButton = new Button("Hủy bỏ");

        positiveButton.setOnAction(event -> {
            confirmEvent.onConfirm();
            window.close();
        });

        negativeButton.setOnAction(event -> {
            window.close();
        });

        HBox confirmLayout = new HBox(10);
        confirmLayout.getChildren().addAll(positiveButton, negativeButton);

        VBox layout = new VBox(10);
        layout.getChildren().addAll(messageLabel, confirmLayout);
        layout.setAlignment(Pos.CENTER);

        layout.setPadding(new Insets(10, 10, 10, 10));

        Scene scene = new Scene(layout);
        window.setScene(scene);
        window.showAndWait();
    }
    public interface NumberInputConfirmEvent{
        void onConfirm(long value);
    }
    public static void showNumberInputDialog(String title, String message, long defaultValue, NumberInputConfirmEvent confirmEvent){
        Stage window = new Stage();
        window.initModality(Modality.WINDOW_MODAL);
        window.setTitle(title);
        window.setMinWidth(250);

        Label messageLabel = new Label(message);

        Button positiveButton = new Button("Chấp nhận");
        Button negativeButton = new Button("Hủy bỏ");

        TextField textField = new TextField(String.valueOf(defaultValue));
        textField.setText(String.valueOf(defaultValue));

        positiveButton.setOnAction(event -> {
            try {
                confirmEvent.onConfirm(Long.parseLong(textField.getText()));
                window.close();
            }
            catch (Exception e){
                showMessage("Lỗi đầu vào", "Chỉ chấp nhận đầu vào là Số");
            }
        });

        negativeButton.setOnAction(event -> {
            window.close();
        });

        HBox confirmLayout = new HBox(10);
        confirmLayout.getChildren().addAll(positiveButton, negativeButton);

        VBox layout = new VBox(10);
        layout.getChildren().addAll(messageLabel, textField, confirmLayout);
        layout.setAlignment(Pos.CENTER);

        layout.setPadding(new Insets(10, 10, 10, 10));

        Scene scene = new Scene(layout);
        window.setScene(scene);
        window.showAndWait();
    }
    public static void showDialog(String title, Parent root, OnConfirmEvent confirmEvent){
        Stage window = new Stage();
        window.initModality(Modality.WINDOW_MODAL);
        window.setTitle(title);
        window.setMinWidth(250);

        Button positiveButton = new Button("Chấp nhận");
        Button negativeButton = new Button("Hủy bỏ");

        positiveButton.setOnAction(event -> {
            confirmEvent.onConfirm();
            window.close();
        });

        negativeButton.setOnAction(event -> {
            window.close();
        });

        HBox confirmLayout = new HBox(10);
        confirmLayout.getChildren().addAll(positiveButton, negativeButton);
        confirmLayout.setAlignment(Pos.CENTER);

        VBox layout = new VBox(10);
        layout.getChildren().addAll(root, confirmLayout);
        layout.setAlignment(Pos.CENTER);

        layout.setPadding(new Insets(10, 10, 10, 10));

        Scene scene = new Scene(layout);
        window.setScene(scene);
        window.showAndWait();
    }
}
