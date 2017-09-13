package business;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import business.Book;
import dataaccess.Auth;
import dataaccess.DataAccess;
import dataaccess.DataAccessFacade;
import dataaccess.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import ui.main.MainController;

public class SystemController implements ControllerInterface {
	public static Auth currentAuth = null;

	@FXML
	private TextField username;
	@FXML
	private PasswordField password;
	@FXML
	private Label titleLabel;

	@FXML
	private void handleLoginButtonAction(ActionEvent event) {

		titleLabel.setText("Library Assistant Login");
		titleLabel.setStyle("-fx-background-color:black;-fx-text-fikll:white");

		String id = username.getText();
		String pword = password.getText();

		DataAccess da = new DataAccessFacade();
		HashMap<String, User> map = da.readUserMap();
		if(!map.containsKey(id)) {
//			throw new LoginException("ID " + id + " not found");
			titleLabel.setText("ID " + id + " not found");
			titleLabel.setStyle("-fx-background-color:#d32f2f;-fx-text-fill:white");
			return;
		}
		String passwordFound = map.get(id).getPassword();
		if(!passwordFound.equals(pword)) {
//			throw new LoginException("Password incorrect");
			titleLabel.setText("Password incorrect");
			titleLabel.setStyle("-fx-background-color:#d32f2f;-fx-text-fill:white");
			return;
		}
		currentAuth = map.get(id).getAuthorization();

		closeStage();
		loadMain();
//		if (uname.equals("admin") && pword.equals("admin")) {
//			closeStage();
//			loadMain();
//		} else {
//			titleLabel.setText("Invalid Credentails");
//			titleLabel.setStyle("-fx-background-color:#d32f2f;-fx-text-fill:white");
//		}
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
		if(currentAuth == null || (currentAuth != Auth.ADMIN && currentAuth != Auth.BOTH)){
			throw new LibrarySystemException("no right!");
		}

		new BookCopyService().addBookCopy(isbn);
	}

	@Override
	public void checkoutBook(String memberId, String isbn) throws LibrarySystemException{
		if(currentAuth == null || (currentAuth != Auth.LIBRARIAN && currentAuth != Auth.BOTH)){
			throw new LibrarySystemException("no right!");
		}
		new BookCopyService().checkoutBook(memberId,isbn);
	}


	@Override
	public void initialize(URL location, ResourceBundle resources) {

	}
}
