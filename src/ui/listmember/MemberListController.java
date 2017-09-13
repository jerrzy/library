package ui.listmember;

import java.net.URL;
import java.util.ResourceBundle;

import business.LibraryMember;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class MemberListController implements Initializable {

    ObservableList<LibraryMember> list = FXCollections.observableArrayList();

    @FXML
    private TableView<LibraryMember> tableView;
    @FXML
    private TableColumn<LibraryMember, String> nameCol;
    @FXML
    private TableColumn<LibraryMember, String> idCol;
    @FXML
    private TableColumn<LibraryMember, String> mobileCol;
    @FXML
    private TableColumn<LibraryMember, String> emailCol;

    @Override
    
    public void initialize(URL url, ResourceBundle rb) {
        initCol();
        loadData();
    }

    private void initCol() {
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        mobileCol.setCellValueFactory(new PropertyValueFactory<>("mobile"));
        emailCol.setCellValueFactory(new PropertyValueFactory<>("email"));
    }
    
    private void loadData() {
        	
    	// todo.. add members into list
        tableView.getItems().setAll(list);
    }
}
