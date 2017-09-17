package ui.addbook;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import business.Author;
import business.Book;
import business.SystemController;
import dataaccess.DataAccessFactory;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class AddBookController {
	
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

    @FXML
    private void handleAddAuthorButton(ActionEvent event){
    	SystemController.loadWindow("/ui/listauthor/author_list.fxml", "List Authors", this.getClass());
    }
    
	/**
     * handle addBook button
     *
     * @param event
     */
    @FXML
    private void confirmAddBook(ActionEvent event) {

        String bookID = newBookISBN.getText();
        String bookAuthors = newBookAuthors.getText();
        String bookName = newBookTitle.getText();

        if (bookID.isEmpty() || bookAuthors.isEmpty() || bookName.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Please Enter in all fields");
            alert.showAndWait();
            return;
        }
        
        List<Author> authorList = new ArrayList<>();
        HashMap<String, Author> allAuthorMap = DataAccessFactory.getInstance().readAUthors();
        
        String[] authorIDs = bookAuthors.split(",");
        for(String authorID : authorIDs){
        	if(allAuthorMap.containsKey(authorID)){
        		authorList.add(allAuthorMap.get(authorID));
        	}
        }
        Book book = new Book(bookID, bookName, 21, authorList);
        DataAccessFactory.getInstance().saveBook(book);

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
