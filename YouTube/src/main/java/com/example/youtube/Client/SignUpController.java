package com.example.youtube.Client;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.json.JSONObject;

import java.io.IOException;
import java.net.Socket;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.locks.ReentrantLock;

public class SignUpController implements Initializable {
    @FXML
    private TextField usernameField;

    @FXML
    private TextField emailField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Button signUpButton;

    @FXML
    private Button loginButton;
    private static Stage stage;
    private static Node source;




    @Override
    public void initialize(URL location, ResourceBundle resources) {
        signUpButton.setOnAction(event -> {handleSignUp();
            source = (Node) event.getSource();});
        loginButton.setOnAction(event -> {handleLogin();});

    }


    @FXML
    private void handleSignUp() {
        stage = (Stage) signUpButton.getScene().getWindow();
        String username = usernameField.getText();
        String email = emailField.getText();
        String password = passwordField.getText();
        String jsonString = "{\"DataManager\":\"SignUp\",\"Parameter1\":\"" + username + "\",\"Parameter2\":\"" + email + "\",\"Parameter3\":\""+password+"\"}";
        ClientToServerConnection.uiController.SetiMessage(jsonString);
        usernameField.clear();
        emailField.clear();
        passwordField.clear();


    }
    public static void goHomeView() {
        Stage stage = (Stage) source.getScene().getWindow();
        UiController.changingscene(stage,"homePage-view.fxml");
    }


    @FXML
    private void handleLogin(){
        stage = (Stage) signUpButton.getScene().getWindow();
        UiController.changingscene(stage,"login-view.fxml");


    }

}
