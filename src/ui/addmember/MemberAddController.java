package ui.addmember;

import Domain.Member;
import dao.DataAccess;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import ui.AlertMaker;

public class MemberAddController implements Initializable {

    @FXML
    private TextField name;
    @FXML
    private TextField id;
    @FXML
    private TextField mobile;
    @FXML
    private TextField email;
    @FXML
    private Button saveButton;
    @FXML
    private Button cancelButton;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }

    @FXML
    private void cancel(ActionEvent event) {
    }

    @FXML
    private void addMember(ActionEvent event) {
        String mName = name.getText();
        String mID = id.getText();
        String mMobile = mobile.getText();
        String mEmail = email.getText();

        Boolean flag = mName.isEmpty() || mID.isEmpty() || mMobile.isEmpty() || mEmail.isEmpty();
        if (flag) {
            AlertMaker.showErrorMessage("Cant add member", "Please Enter in all fields");
            return;
        }
        DataAccess.instance().addMember(new Member(mID, mName, mMobile, mEmail));
        AlertMaker.showSimpleAlert("Member Added", "Saved");
    }

}
