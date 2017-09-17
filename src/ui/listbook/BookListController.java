package ui.listbook;

import business.*;
import dataaccess.DataAccess;
import dataaccess.DataAccessFacade;
import dataaccess.DataAccessFactory;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
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

    @FXML
    private AnchorPane rootPane_bookList;
    @FXML
    private TableView<Book> bookTableView;
    @FXML
    private TableColumn<Book, String> bookIdCol;

    @FXML
    private TableColumn<Book, String> bookTitleCol;

    @FXML
    private TableColumn<Book, Integer> bookMaxCheckoutLengthCol;

    @FXML
    private TableColumn<Book, String> bookNumOfCopiesCol;

    @FXML
    private TableColumn bookAuthorCol;

    @FXML
    private TableColumn<Book, Boolean> bookAvailabilityCol;

    @FXML
    private TableColumn bookActionCol;

    @FXML
    private TextField searchBookId;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        initCol();
        loadData();
    }

    private void initCol() {
        bookIdCol.setCellValueFactory(new PropertyValueFactory<>("isbn"));
        bookTitleCol.setCellValueFactory(new PropertyValueFactory<>("title"));
        bookMaxCheckoutLengthCol.setCellValueFactory(new PropertyValueFactory<>("maxCheckoutLength"));
        bookNumOfCopiesCol.setCellValueFactory(new PropertyValueFactory<>("numOfCopies"));
        bookAuthorCol.setCellValueFactory(new PropertyValueFactory<>("author"));

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

        bookAuthorCol.setCellFactory(authorCellFactory);

        //        publisherCol.setCellValueFactory(new PropertyValueFactory<>("publisher"));
        bookAvailabilityCol.setCellValueFactory(new PropertyValueFactory<>("availabilty"));
        bookActionCol.setCellValueFactory(new PropertyValueFactory<>("ggg"));

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

        bookActionCol.setCellFactory(actionCellFactory);
    }

    private void loadData() {
        DataAccess da = DataAccessFactory.getInstance();

        ObservableList<Book> bookList = FXCollections.observableArrayList();

        HashMap<String, Book> booksMap = da.readBooksMap();
        int i = 0;
        for (Map.Entry<String, Book> e : booksMap.entrySet()) {
            bookList.add(i++, e.getValue());
        }

        bookTableView.getItems().setAll(bookList);
    }

    private void showCopies(String isbn) {
        Map<String,String> dd = null;
        DataAccess da = DataAccessFactory.getInstance();
        Book book = da.findBookByIsbn(isbn);

        TableColumn copyNumberCol = new TableColumn("Copy number");
        copyNumberCol.setCellValueFactory(new PropertyValueFactory<>("copyNum"));

        TableColumn availabilityCol = new TableColumn("availability");
        availabilityCol.setCellValueFactory(new PropertyValueFactory<>("available"));

        TableColumn borrowerIdCol = new TableColumn("borrower id");
        borrowerIdCol.setCellValueFactory(new PropertyValueFactory<>("borrowerId"));

        TableColumn borrowerNameCol = new TableColumn("borrower name");
        borrowerNameCol.setCellValueFactory(new PropertyValueFactory<>("borrowerName"));

        TableColumn checkoutDateCol = new TableColumn("checkout date");
        checkoutDateCol.setCellValueFactory(new PropertyValueFactory<>("checkoutDate"));

        TableColumn dueDateCol = new TableColumn("due date");
        dueDateCol.setCellValueFactory(new PropertyValueFactory<>("dueDate"));

        TableView<BookCopyCheckoutEntry> table = new TableView<>();
        ObservableList<BookCopyCheckoutEntry> data = FXCollections.observableArrayList();

        int i = 0;
        for (BookCopy bc : book.getCopies()) {
            if (bc.isAvailable()) {
                data.add(i++, new BookCopyCheckoutEntry(bc, null, null));
            }else{
                String bookCopyToMemberKey = Utils.getBookCopyUniqueKey(book,bc);

                if(dd == null){
                    dd = da.readBookCopyToMember();
                }

                String memberId = dd.get(bookCopyToMemberKey);

                LibraryMember member = da.findMemberById(memberId);

                CheckoutRecordEntry checkoutRecordEntry = null;
               for(CheckoutRecordEntry e: member.getCheckoutRecord().getCheckoutRecordEntries()){
                   if(bc.getCopyNum() == e.getBookCopyNum() && book.getIsbn().equals(e.getBookIsbn())){
                       checkoutRecordEntry = e;
                       break;
                   }
               }
                BookCopyCheckoutEntry b = new BookCopyCheckoutEntry(bc, member, checkoutRecordEntry);
                System.out.println(b.toString());

                data.add(i++,b);

            }
        }

        table.setItems(data);
        table.getColumns().addAll(copyNumberCol, availabilityCol,borrowerIdCol,
                borrowerNameCol,checkoutDateCol,dueDateCol);

        table.setMinWidth(700);

        Scene scene = new Scene(new Group());

        String css = this.getClass().getResource("book_list.css").toExternalForm();
        scene.getStylesheets().add(css);

        ((Group)scene.getRoot()).getChildren().addAll(table);
        Stage stage = new Stage(StageStyle.DECORATED);
        stage.setTitle(book.getTitle() + " copy bookList");
        stage.setScene(scene);
        stage.show();
    }

    private void showAuthors(String isbn) {
        DataAccess da = DataAccessFactory.getInstance();
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
        stage.setTitle(book.getTitle() + " author bookList");
        stage.setScene(scene);
        stage.show();
    }

    public void searchBook() {
        String isbn = searchBookId.getText();

        if (isbn == null || isbn.trim().equals("")) {
            Utils.alertError("Alert", "ISBN can not be empty!");
            return;
        }

        DataAccess da = DataAccessFactory.getInstance();
        Book book = da.findBookByIsbn(isbn);

        if (book == null) {
            Utils.alertError("Alert", "Book doesn't exist!");
            return;
        }

        ObservableList<Book> list = FXCollections.observableArrayList();

        list.add(0, book);

        bookTableView.getItems().clear();
        bookTableView.getItems().setAll(list);
    }
}
