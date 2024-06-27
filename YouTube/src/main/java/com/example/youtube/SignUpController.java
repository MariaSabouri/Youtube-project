package com.example.youtube;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

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

    private Stage stage;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        signUpButton.setOnAction(event -> handleSignUp());
        loginButton.setOnAction(event -> handleLogin());
    }


    @FXML
    private void handleSignUp() {
        String username = usernameField.getText();
        String email = emailField.getText();
        String password = passwordField.getText();

    }

    @FXML
    private void handleLogin() {

    }

}
