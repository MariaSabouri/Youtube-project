package com.example.youtube.Client;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
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
    private static UiController uicontroller;
    public static void setUiController(UiController uiController){uicontroller=uiController;}

    static Boolean Serverresponse=null;


    public static void SetServerResponseToSignUp(JSONObject jsonObject) {
        Serverresponse=jsonObject.getBoolean("Response");
    }

    private static ActionEvent Event;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        signUpButton.setOnAction(event -> {handleSignUp();
            Event=event;
        });
        loginButton.setOnAction(event -> {handleLogin();
        Event=event;});

    }


    @FXML
    private void handleSignUp() {
        stage = (Stage) signUpButton.getScene().getWindow();
        String username = usernameField.getText();
        String email = emailField.getText();
        String password = passwordField.getText();
        String jsonString = "{\"DataManager\":\"SignUp\",\"Parameter1\":\"" + username + "\",\"Parameter2\":\"" + email + "\",\"Parameter3\":\""+password+"\"}";
        uicontroller.SetiMessage(jsonString);
        uicontroller.sendingrequest();
//        gohomeview(Event);
        signUpButton.setOnAction(event -> gohomeview(event));

    }
    @FXML
    private void gohomeview(ActionEvent event) {
        if (SignUpController.Serverresponse){
            UiController.changingscene(stage,"homePage-view.fxml");

        }else {
            usernameField.clear();
            emailField.clear();
            passwordField.clear();

        }
    }

    @FXML
    private void handleLogin(){
        stage = (Stage) signUpButton.getScene().getWindow();
        UiController.changingscene(stage,"login-view.fxml");


    }

}
