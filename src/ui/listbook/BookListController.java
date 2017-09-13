package ui.listbook;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

import business.Book;
import dataaccess.DataAccess;
import dataaccess.DataAccessFacade;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;

public class BookListController implements Initializable{

    ObservableList<Book> list = FXCollections.observableArrayList();

    @FXML
    private AnchorPane rootPane;
    @FXML
    private TableView<Book> tableView;
    @FXML
    private TableColumn<Book, String> idCol;

    @FXML
    private TableColumn<Book, String> titleCol;

    @FXML
    private TableColumn<Book, String> maxCheckoutLengthCol;

    //    @FXML
    //    private TableColumn<Book, String> authorCol;
    //    @FXML
    //    private TableColumn<Book, String> publisherCol;
    //    @FXML
    //    private TableColumn<Book, Boolean> availabilityCol;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        initCol();
        loadData();
    }

    private void initCol() {
        idCol.setCellValueFactory(new PropertyValueFactory<>("isbn"));
        titleCol.setCellValueFactory(new PropertyValueFactory<>("title"));
        maxCheckoutLengthCol.setCellValueFactory(new PropertyValueFactory<>("maxCheckoutLength"));
        //        authorCol.setCellValueFactory(new PropertyValueFactory<>("author"));
        //        publisherCol.setCellValueFactory(new PropertyValueFactory<>("publisher"));
        //        availabilityCol.setCellValueFactory(new PropertyValueFactory<>("availabilty"));
    }

    private void loadData() {
        // TODO .. add books into list

        DataAccess da = new DataAccessFacade();

        HashMap<String, Book> booksMap = da.readBooksMap();
        int i = 0;
        for (Map.Entry<String, Book> e : booksMap.entrySet()) {
            list.add(i++, e.getValue());
        }

        tableView.getItems().setAll(list);

    }
}
