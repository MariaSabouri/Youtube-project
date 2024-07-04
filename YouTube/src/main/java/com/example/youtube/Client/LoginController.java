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

public class LoginController implements Initializable {

    @FXML
    private TextField emailField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Button loginButton;

    @FXML
    private Button signUpButton;
    private Socket clientSocket;
    private static UiController uiController;
    private static Stage stage;
    static Boolean Serverresponse=null;

    public static void SetServerResponseToLogin(JSONObject jsonObject){
        Serverresponse=jsonObject.getBoolean("Response");

        };


    private static ActionEvent Event;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            // Connecting Client to the server
            this.clientSocket = new Socket("localhost", 6669);
            uiController=new UiController(clientSocket);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        signUpButton.setOnAction(event -> {handleSignUp();
            Event=event;
        });
        loginButton.setOnAction(event -> {handleLogin();
            Event=event;});
    }

    @FXML
    private void handleLogin() {
        stage = (Stage) signUpButton.getScene().getWindow();
        String email = emailField.getText();
        String password = passwordField.getText();
        String jsonString = "{\"DataManager\":\"LogIn\",\"Parameter1\":\"" + email + "\",\"Parameter2\":\"" + password + "\"}";
        uiController.SetiMessage(jsonString);
        uiController.sendingrequest();
        System.out.println("Hello");
        //        gohomeview(Event);
// there is a bug here!!
        loginButton.setOnAction(event -> gohomeview(event));

    }
    @FXML
    private void gohomeview(ActionEvent event) {
        if (LoginController.Serverresponse){
            UiController.changingscene(stage,"homePage-view.fxml");

        }else {
            emailField.clear();
            passwordField.clear();

        }
    }

    @FXML
    private void handleSignUp() {
//        SignUpController.setClientSocket(clientSocket);
        SignUpController.setUiController(uiController);
        stage = (Stage) signUpButton.getScene().getWindow();
        UiController.changingscene(stage,"signUp-view.fxml");
    }
}