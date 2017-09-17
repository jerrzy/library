package ui.listauthor;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

import business.*;
import dataaccess.DataAccess;
import dataaccess.DataAccessFactory;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

public class AuthorListController implements Initializable{

    @FXML
    private TableView<Author> tableView;
    @FXML
    private TableColumn<Author, String> authorIdCol;
    @FXML
    private TableColumn<Author, String> firstNameCol;
    @FXML
    private TableColumn<Author, String> lastNameCol;
    @FXML
    private TableColumn<Author, String> telephoneCol;
    @FXML
    private TableColumn<Author, String> addressCol;
    @FXML
    private TableColumn<Author, String> credentialCol;
    @FXML
    private TableColumn<Author, String> bioCol;

    @Override

    public void initialize(URL url, ResourceBundle rb) {
        initCol();
        loadData();
    }

    private void initCol() {
    	authorIdCol.setCellValueFactory(new PropertyValueFactory<>("authorId"));
        firstNameCol.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        lastNameCol.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        telephoneCol.setCellValueFactory(new PropertyValueFactory<>("telephone"));
        addressCol.setCellValueFactory(new PropertyValueFactory<>("addressStr"));
        credentialCol.setCellValueFactory(new PropertyValueFactory<>("credentials"));
        bioCol.setCellValueFactory(new PropertyValueFactory<>("bio"));
    }

    private void loadData() { //

        // add members into list
        DataAccess da = DataAccessFactory.getInstance();
        HashMap<String, Author> authorMap = da.readAUthors();
        ObservableList<Author> list = FXCollections.observableArrayList();
        int i = 0;
        for (Map.Entry<String, Author> e : authorMap.entrySet()) {
            list.add(i++, e.getValue());
        }

        tableView.getItems().setAll(list);
    }
}
