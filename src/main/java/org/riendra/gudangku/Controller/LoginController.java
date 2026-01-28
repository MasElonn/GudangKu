package org.riendra.gudangku.Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;


public class LoginController {
    private String userCred = "";
    private String passCred = "";
    @FXML
    private TextField username;
    @FXML
    private PasswordField password;

    Stage dashStage = new Stage();

    @FXML
    private void checkCredentials(ActionEvent event) throws IOException{
        if(username.getText().equals(userCred) && password.getText().equals(passCred)){ //Test Checking
            switchToDashboard(event);
        } else{
            Alert alert = new Alert(Alert.AlertType.WARNING, "Please Use the Correct Credential");
            alert.showAndWait();
        }
    }
    @FXML
    private void switchToDashboard(ActionEvent event) throws IOException {

        Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        currentStage.close();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/riendra/gudangku/views/dashboard.fxml"));
        Parent root = loader.load();

        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.setTitle("Dashboard");
        stage.show();
    }
}
