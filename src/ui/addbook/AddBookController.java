package ui.addbook;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import ui.addauthor.AddAuthorController;

public class AddBookController {
	
//	private final AddAuthorController addAuthorController;
    /**
     * add book UI
     */
    @FXML
    private AnchorPane rootPane_AddBook;
    @FXML
    private TextField newBookTitle;
    @FXML
    private TextField newBookISBN;
    @FXML
    private TextField newBookAuthors;
    
    
//    public AddBookController(AddAuthorController addAuthorController) {
//		super();
//		this.addAuthorController = addAuthorController;
//	}

    public void addAuthors(String authorName) {
    	String curAuthors = newBookAuthors.getText();
    	newBookAuthors.setText(curAuthors + ","+ authorName);
    }
    
	/**
     * handle addBook button
     *
     * @param event
     */
    @FXML
    private void confirmAddBook(ActionEvent event) {

        String bookID = newBookISBN.getText();
        String bookAuthor = newBookAuthors.getText();
        String bookName = newBookTitle.getText();

        if (bookID.isEmpty() || bookAuthor.isEmpty() || bookName.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Please Enter in all fields");
            alert.showAndWait();
            return;
        }

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
    private void cancelAddBook(ActionEvent event) {
        Stage stage = (Stage)rootPane_AddBook.getScene().getWindow();
        stage.close();
    }
}
