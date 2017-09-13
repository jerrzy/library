package business;

import java.util.List;

import business.Book;
import dataaccess.DataAccess;
import dataaccess.DataAccessFacade;
import javafx.fxml.Initializable;

public interface ControllerInterface extends Initializable{
	public void login(String id, String password) throws LoginException;

	public List<String> allMemberIds();

	public List<String> allBookIds();

	void addBookCopy(String isbn) throws LibrarySystemException;

    void checkoutBook(String memberId, String isbn) throws LibrarySystemException;
	
}
