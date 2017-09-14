package ui.listbook;

import business.Author;
import business.Book;
import business.BookCopy;
import dataaccess.Auth;
import dataaccess.DataAccess;
import dataaccess.DataAccessFacade;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Group;
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

import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

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

    @FXML
    private TableColumn authorCol;
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
        authorCol.setCellValueFactory(new PropertyValueFactory<>("author"));

        Callback<TableColumn<Book, String>, TableCell<Book, String>> authorCellFactory = new Callback<TableColumn<Book, String>, TableCell<Book, String>>(){
            @Override
            public TableCell call(final TableColumn<Book, String> param) {
                final TableCell<Book, String> cell = new TableCell<Book, String>(){

                    final Button btn = new Button("Authors");

                    @Override
                    public void updateItem(String item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                            setText(null);
                        } else {
                            btn.setOnAction(event -> {
                                Book book = getTableView().getItems().get(getIndex());
                                showAuthors(book.getIsbn());
                            });
                            setGraphic(btn);
                            setText(null);
                        }
                    }
                };
                return cell;
            }


        };

        authorCol.setCellFactory(authorCellFactory);

        //        publisherCol.setCellValueFactory(new PropertyValueFactory<>("publisher"));
        availabilityCol.setCellValueFactory(new PropertyValueFactory<>("availabilty"));
        actionCol.setCellValueFactory(new PropertyValueFactory<>("ggg"));

        Callback<TableColumn<Book, String>, TableCell<Book, String>> actionCellFactory = new Callback<TableColumn<Book, String>, TableCell<Book, String>>(){
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
                                //                                System.out.println(book.getIsbn() + "   " + book.getMaxCheckoutLength());
                            });
                            setGraphic(btn);
                            setText(null);
                        }
                    }
                };
                return cell;
            }
        };

        actionCol.setCellFactory(actionCellFactory);
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
        availabilityCol.setCellValueFactory(new PropertyValueFactory<>("available"));

        TableView<BookCopy> table = new TableView<>();
        ObservableList<BookCopy> data = FXCollections.observableArrayList();

        int i = 0;
        for (BookCopy bc : book.getCopies()) {
            data.add(i++, bc);
        }

        table.setItems(data);
        table.getColumns().addAll(copyNumberCol, availabilityCol);

        Scene scene = new Scene(new Group());

        String css = this.getClass().getResource("book_list.css").toExternalForm();
        scene.getStylesheets().add(css);

        ((Group)scene.getRoot()).getChildren().addAll(table);
        Stage stage = new Stage(StageStyle.DECORATED);
        stage.setTitle(book.getTitle() + " copy list");
        stage.setScene(scene);
        stage.show();
    }

    private void showAuthors(String isbn) {
        DataAccess da = new DataAccessFacade();
        Book book = da.findBookByIsbn(isbn);

        TableColumn firstNameCol = new TableColumn("First Name");
        firstNameCol.setCellValueFactory(new PropertyValueFactory<>("firstName"));

        TableColumn lastNameCol = new TableColumn("Last Name");
        lastNameCol.setCellValueFactory(new PropertyValueFactory<>("lastName"));

        TableColumn phoneCol = new TableColumn("phone");
        phoneCol.setCellValueFactory(new PropertyValueFactory<>("telephone"));

        TableColumn addressCol = new TableColumn("Address");
        addressCol.setCellValueFactory(new PropertyValueFactory<>("address"));

        TableColumn credentialsCol = new TableColumn("Credentials");
        credentialsCol.setCellValueFactory(new PropertyValueFactory<>("credentials"));

        TableColumn bioCol = new TableColumn("Bio");
        bioCol.setCellValueFactory(new PropertyValueFactory<>("bio"));

        TableView<Author> table = new TableView<>();
        table.setMinWidth(700);

        ObservableList<Author> data = FXCollections.observableArrayList();

        int i = 0;
        for (Author bc : book.getAuthors()) {
            data.add(i++, bc);
        }

        table.setItems(data);
        table.getColumns().addAll(firstNameCol, lastNameCol, phoneCol, addressCol, credentialsCol, bioCol);

        Scene scene = new Scene(new Group());

        String css = this.getClass().getResource("book_list.css").toExternalForm();
        scene.getStylesheets().add(css);

        ((Group)scene.getRoot()).getChildren().addAll(table);
        Stage stage = new Stage(StageStyle.DECORATED);
        stage.setTitle(book.getTitle() + " author list");
        stage.setScene(scene);
        stage.show();
    }
}
