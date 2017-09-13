package ui.listbook;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import business.Book;
import business.BookCopy;
import business.SystemController;
import dataaccess.DataAccess;
import dataaccess.DataAccessFacade;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Callback;

public class BookListController implements Initializable{

    ObservableList<Book> list = FXCollections.observableArrayList();

    @FXML
    private AnchorPane rootPane_bookList;
    @FXML
    private TableView<Book> tableView;
    @FXML
    private TableColumn<Book, String> idCol;

    @FXML
    private TableColumn<Book, String> titleCol;

    @FXML
    private TableColumn<Book, Integer> maxCheckoutLengthCol;

    @FXML
    private TableColumn<Book, String> numOfCopiesCol;

    //    @FXML
    //    private TableColumn<Book, String> authorCol;
    //    @FXML
    //    private TableColumn<Book, String> publisherCol;
    @FXML
    private TableColumn<Book, Boolean> availabilityCol;

    @FXML
    private TableColumn actionCol;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        initCol();
        loadData();
    }

    private void initCol() {
        idCol.setCellValueFactory(new PropertyValueFactory<>("isbn"));
        titleCol.setCellValueFactory(new PropertyValueFactory<>("title"));
        maxCheckoutLengthCol.setCellValueFactory(new PropertyValueFactory<>("maxCheckoutLength"));
        numOfCopiesCol.setCellValueFactory(new PropertyValueFactory<>("numOfCopies"));
        //        authorCol.setCellValueFactory(new PropertyValueFactory<>("author"));
        //        publisherCol.setCellValueFactory(new PropertyValueFactory<>("publisher"));
        availabilityCol.setCellValueFactory(new PropertyValueFactory<>("availabilty"));
        actionCol.setCellValueFactory(new PropertyValueFactory<>("ggg"));

        Callback<TableColumn<Book, String>, TableCell<Book, String>> cellFactory = new Callback<TableColumn<Book, String>, TableCell<Book, String>>(){
            @Override
            public TableCell call(final TableColumn<Book, String> param) {
                final TableCell<Book, String> cell = new TableCell<Book, String>(){

                    final Button btn = new Button("copies");

                    @Override
                    public void updateItem(String item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                            setText(null);
                        } else {
                            btn.setOnAction(event -> {
                                Book book = getTableView().getItems().get(getIndex());
                                showCopies(book.getIsbn());
                                System.out.println(book.getIsbn() + "   " + book.getMaxCheckoutLength());
                            });
                            setGraphic(btn);
                            setText(null);
                        }
                    }
                };
                return cell;
            }
        };

        actionCol.setCellFactory(cellFactory);
    }

    private void loadData() {
        DataAccess da = new DataAccessFacade();

        HashMap<String, Book> booksMap = da.readBooksMap();
        int i = 0;
        for (Map.Entry<String, Book> e : booksMap.entrySet()) {
            list.add(i++, e.getValue());
        }

        tableView.getItems().setAll(list);
    }

    private void showCopies(String isbn) {
        DataAccess da = new DataAccessFacade();
        Book book = da.findBookByIsbn(isbn);

        TableColumn copyNumberCol = new TableColumn("Book Copy number");
        copyNumberCol.setCellValueFactory(new PropertyValueFactory<>("copyNum"));

        TableColumn availabilityCol = new TableColumn("availability");
        availabilityCol.setCellValueFactory(new PropertyValueFactory<>("Available"));

//        TableColumn actionCol = new TableColumn("Action");
//        actionCol.setCellValueFactory(new PropertyValueFactory<>("DUMMY"));

//        Callback<TableColumn<Person, String>, TableCell<Person, String>> cellFactory
//                = //
//                new Callback<TableColumn<Person, String>, TableCell<Person, String>>() {
//                    @Override
//                    public TableCell call(final TableColumn<Person, String> param) {
//                        final TableCell<Person, String> cell = new TableCell<Person, String>() {
//
//                            final Button btn = new Button("Just Do It");
//
//                            @Override
//                            public void updateItem(String item, boolean empty) {
//                                super.updateItem(item, empty);
//                                if (empty) {
//                                    setGraphic(null);
//                                    setText(null);
//                                } else {
//                                    btn.setOnAction(event -> {
//                                        Person person = getTableView().getItems().get(getIndex());
//                                        System.out.println(person.getFirstName()
//                                                + "   " + person.getLastName());
//                                    });
//                                    setGraphic(btn);
//                                    setText(null);
//                                }
//                            }
//                        };
//                        return cell;
//                    }
//                };
//
//        actionCol.setCellFactory(cellFactory);

        TableView<BookCopy> table = new TableView<>();
      ObservableList<BookCopy> data
                = FXCollections.observableArrayList();

        int i = 0;
        for(BookCopy bc:book.getCopies()){
            data.add(i++,bc);
        }

        table.setItems(data);
        table.getColumns().addAll(copyNumberCol, availabilityCol);

        Scene scene = new Scene(new Group());

        ((Group) scene.getRoot()).getChildren().addAll(table);
        Stage stage = new Stage(StageStyle.DECORATED);
        stage.setTitle("book copy list");
        stage.setScene(scene);
        stage.show();
    }
}
