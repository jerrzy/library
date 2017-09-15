package ui.addauthor;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import ui.addbook.AddBookController;

public class AddAuthorController {
	
	private final AddBookController addBookController;
	
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
    
    
    public AddAuthorController(AddBookController addBookController) {
		super();
		this.addBookController = addBookController;
	}

	/**
     * add an author to a book
     * @param event
     */
    @FXML
    private void confirmAddAuthor(ActionEvent event){
    	
    	String newAuthorFirstName = addAuthorFirstName.getText();
    	String newAuthorLastName = addAuthorLastName.getText();
    	
    	addBookController.addAuthors(newAuthorFirstName + " " + newAuthorLastName);
    }
    
    /**
     * retrieve an author
     * @param event
     */
    @FXML
    private void confirmRetrieveAuthor(ActionEvent event){
    	
    }
    

    
    @FXML
    private void cancelAddAuthor(ActionEvent event){
    	
    }
}
