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
import org.json.JSONObject;

import java.net.URL;
import java.util.ResourceBundle;

public class LoginController implements Initializable {

    @FXML
    private TextField UsernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Button loginButton;

    @FXML
    private Button signUpButton;
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
        signUpButton.setOnAction(event -> handleSignUp());
        loginButton.setOnAction(event -> {handleLogin();
            source = (Node) event.getSource();
        });
    }

    @FXML
    private void handleLogin() {
        try {
            stage = (Stage) signUpButton.getScene().getWindow();
            String username = UsernameField.getText();
            String password = passwordField.getText();
            if (username.isEmpty()||password.isEmpty()){
                throw new IllegalArgumentException("Textfield is empty");
            }
            setUsername(username);
            String jsonString = "{\"DataManager\":\"LogIn\",\"Parameter1\":\"" + username + "\",\"Parameter2\":\"" + password + "\"}";
            JSONObject jsonObject=new JSONObject(jsonString);
            jsonObject.put("Class","database");
            ClientToServerConnection.uiController.SetiMessage(jsonObject.toString());

            UsernameField.clear();
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
            CommonTools.setCurrentstage(stage);
            JSONObject jsonObject=new JSONObject();
            jsonObject.put("Class","database");
            jsonObject.put("DataManager","gettingUserInfo");
            jsonObject.put("Parameter1", ClientToServerConnection.uiController.getUsername());
            ClientToServerConnection.uiController.SetiMessage(jsonObject.toString());
        }
    }


    @FXML
    private void handleSignUp() {
        stage = (Stage) signUpButton.getScene().getWindow();
        UiController.changingscene(stage,"signUp-view.fxml");
    }
}