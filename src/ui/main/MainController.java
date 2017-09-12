package ui.main;

import Domain.Book;
import Domain.Member;
import Domain.RecordEntry;
import dao.DataAccess;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class MainController implements Initializable {

    @FXML
    private HBox book_info;
    @FXML
    private HBox member_info;
    @FXML
    private TextField bookIDInput;
    @FXML
    private Text bookName;
    @FXML
    private Text bookAuthor;
    @FXML
    private Text bookStatus;
    @FXML
    private TextField memberIDInput;
    @FXML
    private Text memberName;
    @FXML
    private Text memberMobile;
    @FXML
    private ImageView issueButton;
    @FXML
    private TextField bookID;
    @FXML
    private ListView<String> issueDataList;

    Boolean isReadyForSubmission = false;
    @FXML
    private StackPane rootPane;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }

    @FXML
    private void loadAddMember(ActionEvent event) {
        loadWindow("../addmember/member_add.fxml", "Add New Member");
    }

    @FXML
    private void loadAddBook(ActionEvent event) {
        loadWindow("../addbook/add_book.fxml", "Add New Book");
    }

    @FXML
    private void loadMemberTable(ActionEvent event) {
        loadWindow("../listmember/member_list.fxml", "Member List");
    }

    @FXML
    private void loadBookTable(ActionEvent event) {
        loadWindow("../listbook/book_list.fxml", "Book List");
    }

    @FXML
    private void loadSettings(ActionEvent event) {
        loadWindow("settings/settings.fxml", "Settings");
    }

    void loadWindow(String loc, String title) {
        try {
            Parent parent = FXMLLoader.load(getClass().getResource(loc));
            Stage stage = new Stage(StageStyle.DECORATED);
            stage.setTitle(title);
            stage.setScene(new Scene(parent));
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void loadBookInfo(ActionEvent event) {
        clearBookCache();

        String id = bookIDInput.getText();
        Book book = DataAccess.instance().loadBook(id);
        if(book != null){
        	bookName.setText(book.name);
            bookAuthor.setText(book.author);
            String status = (book.isAviable) ? "Available" : "Not Available";
            bookStatus.setText(status);
        } else {
        	bookName.setText("No Such Book Available");
        }
    }

    void clearBookCache() {
        bookName.setText("");
        bookAuthor.setText("");
        bookStatus.setText("");
    }

    void clearMemberCache() {
        memberName.setText("");
        memberMobile.setText("");
    }

    @FXML
    private void loadMemberInfo(ActionEvent event) {
        clearMemberCache();

        String id = memberIDInput.getText();
        Member member = DataAccess.instance().loadMember(id);
        if(member != null){
        	memberName.setText(member.name);
            memberMobile.setText(member.mobile);
        } else {
        	memberName.setText("No Such Member Available");
        }    
    }

    @FXML
    private void loadIssueOperation(ActionEvent event) {
        String memberID = memberIDInput.getText();
        String bookID = bookIDInput.getText();

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirm Issue Operation");
        alert.setHeaderText(null);
        alert.setContentText("Are you sure want to issue the book " + bookName.getText() + "\n to " + memberName.getText() + " ?");

        Optional<ButtonType> response = alert.showAndWait();
        if (response.get() == ButtonType.OK) {
        	
            if (DataAccess.instance().issue(bookID, memberID)) {
                Alert alert1 = new Alert(Alert.AlertType.INFORMATION);
                alert1.setTitle("Success");
                alert1.setHeaderText(null);
                alert1.setContentText("Book Issue Complete");

                alert1.showAndWait();
            } else {
                Alert alert1 = new Alert(Alert.AlertType.ERROR);
                alert1.setTitle("Failed");
                alert1.setHeaderText(null);
                alert1.setContentText("Issue Operation Failed");
                alert1.showAndWait();
            }
        } else {
            Alert alert1 = new Alert(Alert.AlertType.INFORMATION);
            alert1.setTitle("Cancelled");
            alert1.setHeaderText(null);
            alert1.setContentText("Issue Operation cancelled");
            alert1.showAndWait();
        }
    }

    @FXML
    private void loadBookInfo2(ActionEvent event) {
        ObservableList<String> issueData = FXCollections.observableArrayList();
        isReadyForSubmission = false;

        String id = bookID.getText();
        
        RecordEntry record = DataAccess.instance().getIssueRecord(id);
        Book book = DataAccess.instance().loadBook(id);
        Member member = DataAccess.instance().loadMember(record.menId);
        
        String mIssueTime = record.date;
        int mRenewCount = record.reNewCount;

        issueData.add("Issue Date and Time :" + mIssueTime);
        issueData.add("Renew Count :" + mRenewCount);

        issueData.add("Book Information:-");
        
        issueData.add("\tBook Name :" + book.name);
        issueData.add("\tBook ID :" + book.id);
        issueData.add("\tBook Author :" + book.author);
        issueData.add("\tBook Publisher :" + book.publisher);
        issueData.add("Member Information:-");

        issueData.add("\tName :" + member.name);
        issueData.add("\tMobile :" + member.mobile);
        issueData.add("\tEmail :" + member.email);

        isReadyForSubmission = true;
        issueDataList.getItems().setAll(issueData);
    }

    @FXML
    private void loadSubmissionOp(ActionEvent event) {
        if (!isReadyForSubmission) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Failed");
            alert.setHeaderText(null);
            alert.setContentText("Please select a book to submit");
            alert.showAndWait();
            return;
        }

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirm Submission Operation");
        alert.setHeaderText(null);
        alert.setContentText("Are you sure want to return the book ?");

        Optional<ButtonType> response = alert.showAndWait();
        if (response.get() == ButtonType.OK) {
            String id = bookID.getText();
            
            DataAccess.instance().returnBook(id);
            
            Alert alert1 = new Alert(Alert.AlertType.INFORMATION);
            alert1.setTitle("Success");
            alert1.setHeaderText(null);
            alert1.setContentText("Book Has Been Submitted");
            alert1.showAndWait();
            
        } else {
            Alert alert1 = new Alert(Alert.AlertType.INFORMATION);
            alert1.setTitle("Cancelled");
            alert1.setHeaderText(null);
            alert1.setContentText("Submission Operation cancelled");
            alert1.showAndWait();
        }
    }

    @FXML
    private void loadRenewOp(ActionEvent event) {
//        if (!isReadyForSubmission) {
//            Alert alert = new Alert(Alert.AlertType.ERROR);
//            alert.setTitle("Failed");
//            alert.setHeaderText(null);
//            alert.setContentText("Please select a book to renew");
//            alert.showAndWait();
//            return;
//        }
//
//        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
//        alert.setTitle("Confirm Renew Operation");
//        alert.setHeaderText(null);
//        alert.setContentText("Are you sure want to renew the book ?");
//
//        Optional<ButtonType> response = alert.showAndWait();
//        if (response.get() == ButtonType.OK) {
//            String ac = "UPDATE ISSUE SET issueTime = CURRENT_TIMESTAMP, renew_count = renew_count+1 WHERE BOOKID = '" + bookID.getText() + "'";
//            System.out.println(ac);
//            if (dataAccess.execAction(ac)) {
//                Alert alert1 = new Alert(Alert.AlertType.INFORMATION);
//                alert.setTitle("Success");
//                alert.setHeaderText(null);
//                alert.setContentText("Book Has Been Renewed");
//                alert.showAndWait();
//            } else {
//                Alert alert1 = new Alert(Alert.AlertType.ERROR);
//                alert.setTitle("Failed");
//                alert.setHeaderText(null);
//                alert.setContentText("Renew Has Been Failed");
//                alert.showAndWait();
//            }
//        } else {
//            Alert alert1 = new Alert(Alert.AlertType.INFORMATION);
//            alert1.setTitle("Cancelled");
//            alert1.setHeaderText(null);
//            alert1.setContentText("Renew Operation cancelled");
//            alert1.showAndWait();
//        }
    }

    @FXML
    private void handleMenuClose(ActionEvent event) {
        ((Stage) rootPane.getScene().getWindow()).close();
    }

    @FXML
    private void handleMenuAddBook(ActionEvent event) {
        loadWindow("ui/addbook/add_book.fxml", "Add New Book");
    }

    @FXML
    private void handleMenuAddMember(ActionEvent event) {
        loadWindow("ui/addmember/member_add.fxml", "Add New Member");
    }

    @FXML
    private void handleMenuViewBook(ActionEvent event) {
        loadWindow("ui/listbook/book_list.fxml", "Book List");
    }

    private void handleMenuViewMember(ActionEvent event) {
        loadWindow("ui/listmember/member_list.fxml", "Member List");
    }    

    @FXML
    private void handleMenuFullScreen(ActionEvent event) {
        Stage stage = ((Stage) rootPane.getScene().getWindow());
        stage.setFullScreen(!stage.isFullScreen());
    }
}
