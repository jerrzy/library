package business;

import javafx.fxml.Initializable;

public interface ControllerInterface extends Initializable{
	public void login(String id, String password) throws LoginException;

	void addBookCopy(String isbn) throws LibrarySystemException;

    void checkoutBook(String memberId, String isbn) throws LibrarySystemException;
}
