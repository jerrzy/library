package business;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.ResourceBundle;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import dataaccess.Auth;
import dataaccess.DataAccess;
import dataaccess.DataAccessFacade;
import dataaccess.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import ui.AlertMaker;

public class SystemController implements ControllerInterface{
    public static Auth currentAuth = null;

    /*
     * main UI
     */
    @FXML
    private StackPane mainRootPane;
    @FXML
    private Button addMemberButton;
    @FXML
    private Button addBookButton;
    @FXML
    private Button loadMemberButton;
    @FXML
    private Button loadBookButton;
    @FXML
    private Button logoutButton;
    
    /**
     * login UI
     */
    @FXML
    private AnchorPane anchorPane_login;
    @FXML
    private TextField username_login;
    @FXML
    private PasswordField password_login;
    @FXML
    private Label titleLabel_login;

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
     * add book copy UI
     */
    @FXML
    private TextField isbn;
    @FXML
    private AnchorPane rootPane_AddBookCopy;

    /**
     * add member UI
     */
    @FXML
    private TextField firstName_AddMember;
    @FXML
    private TextField lastName_AddMember;
    @FXML
    private TextField telephone_AddMember;
    @FXML
    private TextField street_AddMember;
    @FXML
    private TextField city_AddMember;
    @FXML
    private TextField state_AddMember;
    @FXML
    private TextField zip_AddMember;
    @FXML
    private Button saveButton_AddMember;
    @FXML
    private Button cancelButton_AddMember;

    /**
     * check in & check out
     */
    @FXML
    private TextField checkInOutBookISBN;
    @FXML
    private TextField checkInOutMemID;
    //////////////////////////////////////////////////////

    /**
     * load window
     */
    //////////////////////////////////////////////////////
    private void loadMain() {
        loadWindow("/ui/main.fxml", "Library System");
    }

    private void loadLoginWindow() {
        loadWindow("/ui/login.fxml", "Library System");
    }

    @FXML
    private void loadLoginView(ActionEvent event) {
        loadWindow("/ui/login.fxml", "login");
    }

    @FXML
    private void loadAddMember(ActionEvent event) {
        loadWindow("/ui/addmember.fxml", "Add New Member");
    }

    @FXML
    private void loadAddBook(ActionEvent event) {
        loadWindow("/ui/addbook.fxml", "Add New Book");
    }

    @FXML
    private void loadAddBookCopy(ActionEvent event) {
        loadWindow("/ui/addbookCopy.fxml", "Add New Book Copy");
    }

    @FXML
    private void loadMemberTable(ActionEvent event) {
        loadWindow("/ui/listmember/member_list.fxml", "Member List");
    }

    @FXML
    private void loadBookTable(ActionEvent event) {
        loadWindow("../ui/listbook/book_list.fxml", "Book List");
    }

    private void closeMainWindow() {
        if (mainRootPane != null) {
            ((Stage)mainRootPane.getScene().getWindow()).close();
        }
    }

    private void closeLoginWindow() {
        if (anchorPane_login != null) {
            ((Stage)anchorPane_login.getScene().getWindow()).close();
        }
    }

    void loadWindow(String loc, String title) {
        try {
            Parent parent = FXMLLoader.load(getClass().getResource(loc));
            
            Stage stage = new Stage(StageStyle.DECORATED);            
            stage.setTitle(title);
            Scene scene = new Scene(parent);
            stage.setScene(scene);
            applyPermission(scene);
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(SystemController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /////////////////////////////////////////////////////////////////

    /**
     * handle login
     *
     * @param event
     */
    @FXML
    private void handleLoginButtonAction(ActionEvent event) {

        titleLabel_login.setText("Library System Login");
        titleLabel_login.setStyle("-fx-background-color:black;-fx-text-fikll:white");

        String id = username_login.getText();
        String pword = password_login.getText();

        try {
            login(id, pword);
        } catch (Exception e) {
            titleLabel_login.setText(e.getMessage());
            titleLabel_login.setStyle("-fx-background-color:#d32f2f;-fx-text-fill:white");
            e.printStackTrace();
            return;
        }
        closeLoginWindow();
        loadMain();
    }

    private void applyPermission(Scene scene){
    	
    	if(currentAuth != null){
    		List<String> functionList = new ArrayList<>();
    		if(currentAuth == Auth.ADMIN){
    			functionList.add("#checkout");
    			functionList.add("#checkin");
    			
    			for(String function : functionList){
    				Node node = scene.lookup(function);
    				if(node != null){
    					node.setDisable(true);
    				}
    			}
    		} else if (currentAuth == Auth.LIBRARIAN){
    			functionList.add("#addMemberButton");
    			functionList.add("#addBookButton");
    			
    			for(String function : functionList){
    				Node node = scene.lookup(function);
    				if(node != null){
    					node.setDisable(true);
    				}
    			}
    		}
    		
    	}
    }
    
    @FXML
    private void handleLogoutButtonAction(ActionEvent event) {

        //    	titleLabel_login.setText("Library System Login");
        //    	titleLabel_login.setStyle("-fx-background-color:black;-fx-text-fikll:white");
        logout();

        closeMainWindow();
        loadLoginWindow();
    }

    /**
     * handle login cancel
     *
     * @param event
     */
    @FXML
    private void handleCancelButtonAction(ActionEvent event) {
        System.exit(0);
    }

    /**
     * handle addBook button
     *
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

    @FXML
    private void addBookCopy(ActionEvent event) {

        String isbnStr = isbn.getText();
        try {
            addBookCopy(isbnStr);
        } catch (LibrarySystemException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText(e.getMessage());
            alert.showAndWait();
            e.printStackTrace();
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
     *
     * @param event
     */
    @FXML
    private void cancel(ActionEvent event) {
        Stage stage = (Stage)rootPane_AddBook.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void cancelAddBookCopy(ActionEvent event) {
        Stage stage = (Stage)rootPane_AddBookCopy.getScene().getWindow();
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
        String firstName = firstName_AddMember.getText();
        String lastName = lastName_AddMember.getText();
        String telephone = telephone_AddMember.getText();
        String street = street_AddMember.getText();
        String city = city_AddMember.getText();
        String state = state_AddMember.getText();
        String zip = zip_AddMember.getText();

        Boolean flag = firstName.isEmpty() || lastName.isEmpty() || telephone.isEmpty() || street.isEmpty() || city.isEmpty() || state.isEmpty() || zip.isEmpty();
        if (flag) {
            AlertMaker.showErrorMessage("Cant add member", "Please Enter in all fields");
            return;
        }
        String b = UUID.randomUUID().toString();
        Address add = new Address(street, city, state, zip);
        LibraryMember a = new LibraryMember(b, firstName, lastName, telephone, add);

        DataAccessFacade c = new DataAccessFacade();
        c.saveNewMember(a);

        AlertMaker.showSimpleAlert("Member Added", "Saved");
    }

    ///////////////////////////////////////////////////

    /**
     * check out
     */
    @FXML
    private void handleCheckOut(ActionEvent event) {
        String checkInOutBookISBNS = checkInOutBookISBN.getText();
        String checkInOutMemIDS = checkInOutMemID.getText();
        try {
            checkoutBook(checkInOutMemIDS, checkInOutBookISBNS);
        } catch (LibrarySystemException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("checkout book copy");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
            e.printStackTrace();
            return;
        }

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText("checkout book copy");
        alert.setContentText("Success");
        alert.showAndWait();
    }

    /**
     * check in
     *
     * @param event
     */
    @FXML
    private void handleCheckIn(ActionEvent event) {
        String checkInOutBookISBNS = checkInOutBookISBN.getText();
        String checkInOutMemIDS = checkInOutMemID.getText();
        AlertMaker.showSimpleAlert("Book checked in", "Book ISBN:" + checkInOutBookISBNS + ". Member ID:" + checkInOutMemIDS);
    }
    ///////////////////////////////////////////////////


    public void login(String id, String password) throws LoginException {
        DataAccess da = new DataAccessFacade();
        HashMap<String, User> map = da.readUserMap();
        if (!map.containsKey(id)) {
            throw new LoginException("ID " + id + " not found");
        }
        String passwordFound = map.get(id).getPassword();
        if (!passwordFound.equals(password)) {
            throw new LoginException("Password incorrect");
        }
        currentAuth = map.get(id).getAuthorization();
    }

    private void logout() {
        currentAuth = null;
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
    public void addBookCopy(String isbn) throws LibrarySystemException {
        //TODO check the auth
        //        if (currentAuth == null || (currentAuth != Auth.ADMIN && currentAuth != Auth.BOTH)) {
        //            throw new LibrarySystemException("no right!");
        //        }

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
