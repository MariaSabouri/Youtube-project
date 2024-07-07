package com.example.youtube.Client.Controllers.SignUpLoginHomeControllers;

import com.example.youtube.Client.ClientToServerConnection;
import com.example.youtube.Client.Controllers.CommonTools;
import com.example.youtube.Client.UiController;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class SignUpController implements Initializable {
    @FXML
    private TextField usernameField;


    @FXML
    private PasswordField passwordField;

    @FXML
    private Button signUpButton;

    @FXML
    private Button loginButton;
    private static Stage stage;
    private static Node source;
    private static String Username;
    public static String getUsername() {
        return Username;
    }

    public static void setUsername(String username) {
        Username = username;
        UiController.setUsername(username);
    }





    @Override
    public void initialize(URL location, ResourceBundle resources) {
        signUpButton.setOnAction(event -> {handleSignUp();
            source = (Node) event.getSource();});
        loginButton.setOnAction(event -> {handleLogin();});

    }


    @FXML
    private void handleSignUp() {
        try {
            stage = (Stage) signUpButton.getScene().getWindow();
            String username = usernameField.getText();
            String password = passwordField.getText();
            if (username.isEmpty()|| password.isEmpty()){
                throw new IllegalArgumentException("Textfield is empty");
            }
            setUsername(username);
            String jsonString = "{\"DataManager\":\"SignUp\",\"Parameter1\":\"" + username + "\",\"Parameter2\":\"" + password + "\"}";
            ClientToServerConnection.uiController.SetiMessage(jsonString);
            usernameField.clear();
            passwordField.clear();

        }catch (IllegalArgumentException e){
            CommonTools.showingError();
        }


    }
    public static void goHomeView(boolean b) {
        if (!b){
            Platform.runLater(()->{
                CommonTools.showingError();
            });
        }else {
//            Stage stage = (Stage) source.getScene().getWindow();
            UiController.changingscene(stage,"homePage-view.fxml");
        }
    }


    @FXML
    private void handleLogin(){
        stage = (Stage) loginButton.getScene().getWindow();
        UiController.changingscene(stage,"login-view.fxml");


    }

}
