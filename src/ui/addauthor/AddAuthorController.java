package ui.addauthor;

import business.Address;
import business.Author;
import dataaccess.DataAccessFactory;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class AddAuthorController {
	
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

	/**
     * add an author to a book
     * @param event
     */
    @FXML
    private void confirmAddAuthor(ActionEvent event){
    	
    	String newAuthorFirstName = addAuthorFirstName.getText();
    	String newAuthorLastName = addAuthorLastName.getText();	
    	String telephone = addAuthorTelephone.getText();
        String credentials =  addAuthorCredentials.getText();
        String bio = addAuthorBIO.getText();
        String street = addAuthorStreet.getText();
        String city = addAuthorCity.getText();
        String state = addAuthorState.getText();
        String zip = addAuthorZip.getText();
        
        Address address = new Address(street, city, state, zip);
        
        String authorId = DataAccessFactory.getInstance().getUniqueId();
        Author author = new Author(newAuthorFirstName, newAuthorLastName, telephone, address, credentials, bio, authorId);
        DataAccessFactory.getInstance().save(author);
    }
    
    @FXML
    private void cancelAddAuthor(ActionEvent event){
    	
    }
}
