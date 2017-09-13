package ui.login;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import ui.main.MainController;

public class LoginController implements Initializable {

    @FXML
    private TextField username;
    @FXML
    private PasswordField password;
    @FXML
    private Label titleLabel;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }

    @FXML
    private void handleLoginButtonAction(ActionEvent event) {

        titleLabel.setText("Library Assistant Login");
        titleLabel.setStyle("-fx-background-color:black;-fx-text-fikll:white");

        String uname = username.getText();
        String pword = password.getText();


        if (uname.equals("admin") && pword.equals("admin")) {
            closeStage();
            loadMain();
        } else {
            titleLabel.setText("Invalid Credentails");
            titleLabel.setStyle("-fx-background-color:#d32f2f;-fx-text-fill:white");
        }
    }

    @FXML
    private void handleCancelButtonAction(ActionEvent event) {
        System.exit(0);
    }

    private void closeStage() {
        ((Stage) username.getScene().getWindow()).close();
    }

    void loadMain() {
        try {
            Parent parent = FXMLLoader.load(getClass().getResource("../main/main.fxml"));
            Stage stage = new Stage(StageStyle.DECORATED);
            stage.setTitle("Library Assistant");
            stage.setScene(new Scene(parent));
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
