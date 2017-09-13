package business;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import dataaccess.Auth;
import dataaccess.DataAccess;
import dataaccess.DataAccessFacade;
import dataaccess.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import ui.AlertMaker;

public class SystemController implements ControllerInterface{
    public static Auth currentAuth = null;

    /*
     * main UI
     */
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
    
    /**
     * login UI
     */
    @FXML
    private TextField username;
    @FXML
    private PasswordField password;
    @FXML
    private Label titleLabel;

    /**
     * add book UI
     */
    @FXML
    private TextField title;
    @FXML
    private TextField id;
    @FXML
    private TextField author;
    @FXML
    private TextField publisher;
    @FXML
    private Button saveButton;
    @FXML
    private Button cancelButton;
    @FXML
    private AnchorPane rootPane_AddBook;
    /**
     * add member UI
     */
    @FXML
    private TextField name_AddMember;
    @FXML
    private TextField id_AddMember;
    @FXML
    private TextField mobile_AddMember;
    @FXML
    private TextField email_AddMember;
    @FXML
    private Button saveButton_AddMember;
    @FXML
    private Button cancelButton_AddMember;
    
    //////////////////////////////////////////////////////
    /**
     * load window
     * @param event
     */
    //////////////////////////////////////////////////////
    void loadMain() {
    	loadWindow("/ui/main.fxml", "Library System");
    }
    
    @FXML
    private void loadLoginView(ActionEvent event){
    	loadWindow("/ui/login.fxml", "login");
    }
  
    @FXML
    private void loadAddMember(ActionEvent event) {
    	loadWindow("/ui/member_add.fxml", "Add New Member");
    }

    @FXML
    private void loadAddBook(ActionEvent event) {
    	loadWindow("/ui/add_book.fxml", "Add New Book");
    }

    @FXML
    private void loadMemberTable(ActionEvent event) {
    	loadWindow("/ui/listmember/member_list.fxml", "Member List");
    }

    @FXML
    private void loadBookTable(ActionEvent event) {
    	loadWindow("/listbook/book_list.fxml", "Book List");
    }

    void loadWindow(String loc, String title) {
    	try {
    		Parent parent = FXMLLoader.load(getClass().getResource(loc));
    		Stage stage = new Stage(StageStyle.DECORATED);
    		stage.setTitle(title);
    		stage.setScene(new Scene(parent));
    		stage.show();
    	} catch (IOException ex) {
    		Logger.getLogger(SystemController.class.getName()).log(Level.SEVERE, null, ex);
    	}
    }
    
    /////////////////////////////////////////////////////////////////
    /**
     * handle login
     * @param event
     */
    @FXML
    private void handleLoginButtonAction(ActionEvent event) {

        titleLabel.setText("Library System Login");
        titleLabel.setStyle("-fx-background-color:black;-fx-text-fikll:white");

        String id = username.getText();
        String pword = password.getText();

        try {
            login(id, pword);
        } catch (Exception e) {
            titleLabel.setText(e.getMessage());
            titleLabel.setStyle("-fx-background-color:#d32f2f;-fx-text-fill:white");
            e.printStackTrace();
        }
        closeStage();
        loadMain();
    }

    /**
     * handle login cancel
     * @param event
     */
    @FXML
    private void handleCancelButtonAction(ActionEvent event) {
        System.exit(0);
    }

    /**
     * handle addBook button
     * @param event
     */
    @FXML
    private void addBook(ActionEvent event) {
    	
        String bookID = id.getText();
        String bookAuthor = author.getText();
        String bookName = title.getText();
        String bookPublisher = publisher.getText();

        if (bookID.isEmpty() || bookAuthor.isEmpty() || bookName.isEmpty() || bookPublisher.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Please Enter in all fields");
            alert.showAndWait();
            return;
        }

        // todo.. addBookCopy logical code
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(null);
        alert.setContentText("Success");
        alert.showAndWait();
    }

    /**
     * handle addBook cancel
     * @param event
     */
    @FXML
    private void cancel(ActionEvent event) {
        Stage stage = (Stage) rootPane_AddBook.getScene().getWindow();
        stage.close();
    }
    
    /**
     * handle add member
     */
    @FXML
    private void cancel_AddMember(ActionEvent event) {
    }

    @FXML
    private void addMember(ActionEvent event) {
        String mName = name_AddMember.getText();
        String mID = id_AddMember.getText();
        String mMobile = mobile_AddMember.getText();
        String mEmail = email_AddMember.getText();

        Boolean flag = mName.isEmpty() || mID.isEmpty() || mMobile.isEmpty() || mEmail.isEmpty();
        if (flag) {
            AlertMaker.showErrorMessage("Cant add member", "Please Enter in all fields");
            return;
        }
        AlertMaker.showSimpleAlert("Member Added", "Saved");
    }
    ///////////////////////////////////////////////////
    private void closeStage() {
        ((Stage)username.getScene().getWindow()).close();
    }

	public void login(String id, String password) throws LoginException {
		DataAccess da = new DataAccessFacade();
		HashMap<String, User> map = da.readUserMap();
		if(!map.containsKey(id)) {
			throw new LoginException("ID " + id + " not found");
		}
		String passwordFound = map.get(id).getPassword();
		if(!passwordFound.equals(password)) {
			throw new LoginException("Password incorrect");
		}
		currentAuth = map.get(id).getAuthorization();
	}

    @Override
    public void addMember(LibraryMember member) {

    }

    @Override
    public List<String> allMemberIds() {
        DataAccess da = new DataAccessFacade();
        List<String> retval = new ArrayList<>();
        retval.addAll(da.readMemberMap().keySet());
        return retval;
    }

    @Override
    public List<String> allBookIds() {
        DataAccess da = new DataAccessFacade();
        List<String> retval = new ArrayList<>();
        retval.addAll(da.readBooksMap().keySet());
        return retval;
    }

    @Override
    public void AddBookCopy(String isbn) throws LibrarySystemException {
        if (currentAuth == null || (currentAuth != Auth.ADMIN && currentAuth != Auth.BOTH)) {
            throw new LibrarySystemException("no right!");
        }

        new BookCopyService().addBookCopy(isbn);
    }

    @Override
    public void checkoutBook(String memberId, String isbn) throws LibrarySystemException {
        if (currentAuth == null || (currentAuth != Auth.LIBRARIAN && currentAuth != Auth.BOTH)) {
            throw new LibrarySystemException("no right!");
        }
        new BookCopyService().checkoutBook(memberId, isbn);
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
