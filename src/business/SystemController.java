package business;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import dataaccess.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
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
    private Button addBookCopyButton;
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

    /**
     * add author UI
     */
    @FXML
    private TextField addAuthorFirstName;
    @FXML
    private TextField addAuthorLastName;
    @FXML
    private TextField addAuthorTelephone;
    @FXML
    private TextField addAuthorCredentials;
    @FXML
    private TextField addAuthorBIO;
    @FXML
    private TextField addAuthorStreet;
    @FXML
    private TextField addAuthorCity;
    @FXML
    private TextField addAuthorState;
    @FXML
    private TextField addAuthorZip;
    
    //////////////////////////////////////////////////////
    /*
     * checkoutRecordMemId
     */
    @FXML
    private TextField checkoutRecordMemId;

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
    private void loadAddAuthorWindow(ActionEvent event) {
        loadWindow("/ui/addauthor.fxml", "Add New Author");
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

    private void applyPermission(Scene scene) {

        if (currentAuth != null) {
            List<String> functionList = new ArrayList<>();
            if (currentAuth == Auth.ADMIN) {
                functionList.add("#checkout");
                functionList.add("#checkin");

                for (String function : functionList) {
                    Node node = scene.lookup(function);
                    if (node != null) {
                        node.setDisable(true);
                    }
                }
            } else if (currentAuth == Auth.LIBRARIAN) {
                functionList.add("#addMemberButton");
                functionList.add("#addBookCopyButton");

                for (String function : functionList) {
                    Node node = scene.lookup(function);
                    if (node != null) {
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

    @FXML
    private void handleAddAuthorButton(ActionEvent event){
    	loadAddAuthorWindow(event);
    }
    
    @FXML
    private void handleAddBookButton(ActionEvent event){
    	
    }
    
    @FXML
    private void handleCancelAddBookButton(ActionEvent event){
    	
    }
    
    //////////////////////////////////////////////////////////////////
    /**
     * handle login cancel
     *
     * @param event
     */
    @FXML
    private void handleCancelButtonAction(ActionEvent event) {
        System.exit(0);
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
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(null);
        alert.setContentText("Success");
        alert.showAndWait();
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
        DataAccess c = DataAccessFactory.getInstance();

        //        String b = UUID.randomUUID().toString();
        String b = c.getUniqueId();

        Address add = new Address(street, city, state, zip);
        LibraryMember a = new LibraryMember(b, firstName, lastName, telephone, add);

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

    /*
    search records
     */
    @FXML
    private void handleSearchRecords(ActionEvent event) {
        String memberId = checkoutRecordMemId.getText();
        if (isEmpty(memberId)) {
            Utils.alertError("Alert", "member id can not be empty!");
            return;
        }
        try {
            new CheckoutRecordService().showCheckoutRecords(memberId);
        } catch (LibrarySystemException e) {
            Utils.alertError("ALert", e.getMessage());
            e.printStackTrace();
        }

    }

    public void login(String id, String password) throws LoginException {
        DataAccess da = DataAccessFactory.getInstance();
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
        DataAccess da = DataAccessFactory.getInstance();
        List<String> retval = new ArrayList<>();
        retval.addAll(da.readMemberMap().keySet());
        return retval;
    }

    @Override
    public List<String> allBookIds() {
        DataAccess da = DataAccessFactory.getInstance();
        List<String> retval = new ArrayList<>();
        retval.addAll(da.readBooksMap().keySet());
        return retval;
    }

    @Override
    public void addBookCopy(String isbn) throws LibrarySystemException {
        //       check the auth
        if (currentAuth == null || (currentAuth != Auth.ADMIN && currentAuth != Auth.BOTH)) {
            throw new LibrarySystemException("no right!");
        }
        if (isEmpty(isbn)) {
            throw new LibrarySystemException("isbn can not be empty!");
        }
        DataAccess da = DataAccessFactory.getInstance();

        Book book = da.findBookByIsbn(isbn);

        if (book == null) {
            throw new LibrarySystemException("The book whose isbn is " + isbn + " doesn't exist!");
        }

        book.addCopy();

        da.saveBook(book);
    }

    @Override
    public void checkoutBook(String memberId, String isbn) throws LibrarySystemException {
        if (currentAuth == null || (currentAuth != Auth.LIBRARIAN && currentAuth != Auth.BOTH)) {
            throw new LibrarySystemException("no right!");
        }
        if (isEmpty(memberId)) {
            throw new LibrarySystemException("memberId can not be empty!");
        }

        if (isEmpty(isbn)) {
            throw new LibrarySystemException("isbn can not be empty!");
        }

        DataAccess da = DataAccessFactory.getInstance();

        LibraryMember member = da.findMemberById(memberId);
        if (member == null) {
            throw new LibrarySystemException("Member doesn't exist! memberId=" + memberId);
        }

        Book book = da.findBookByIsbn(isbn);
        if (book == null) {
            throw new LibrarySystemException("Book doesn't exist! isbn=" + isbn);
        }

        if (!book.isAvailable()) {
            throw new LibrarySystemException("Book is not available! isbn=" + isbn);
        }

        BookCopy bookCopy = book.getNextAvailableCopy();

        bookCopy.changeAvailability();

        LocalDate dateOfCheckout = LocalDate.now();

        LocalDate dueDate = dateOfCheckout.plus(book.getMaxCheckoutLength(), ChronoUnit.DAYS);

        member.getCheckoutRecord().addCheckoutRecordEntry(bookCopy, dateOfCheckout, dueDate);

        da.saveNewMember(member);

        da.saveBook(book);
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    private boolean isEmpty(String content) {
        if (content == null || content.trim().equals("")) {
            return true;
        }

        return false;
    }

}
