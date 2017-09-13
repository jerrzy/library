package ui.listmember;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

import business.*;
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
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Callback;

public class MemberListController implements Initializable{

    ObservableList<LibraryMember> list = FXCollections.observableArrayList();

    @FXML
    private TableView<LibraryMember> tableView;
    @FXML
    private TableColumn<LibraryMember, String> idCol;
    @FXML
    private TableColumn<LibraryMember, String> firstNameCol;
    @FXML
    private TableColumn<LibraryMember, String> lastNameCol;
    @FXML
    private TableColumn<LibraryMember, String> mobileCol;
    @FXML
    private TableColumn<LibraryMember, String> addressCol;

    @FXML
    private TableColumn actionCol;

    @Override

    public void initialize(URL url, ResourceBundle rb) {
        initCol();
        loadData();
    }

    private void initCol() {
        idCol.setCellValueFactory(new PropertyValueFactory<>("memberId"));
        firstNameCol.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        lastNameCol.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        mobileCol.setCellValueFactory(new PropertyValueFactory<>("telephone"));
        addressCol.setCellValueFactory(new PropertyValueFactory<>("email"));

        actionCol.setCellValueFactory(new PropertyValueFactory<>("ggg"));

        Callback<TableColumn<LibraryMember, String>, TableCell<LibraryMember, String>> cellFactory = new Callback<TableColumn<LibraryMember, String>, TableCell<LibraryMember, String>>(){
            @Override
            public TableCell call(final TableColumn<LibraryMember, String> param) {
                final TableCell<LibraryMember, String> cell = new TableCell<LibraryMember, String>(){

                    final Button btn = new Button("copies");

                    @Override
                    public void updateItem(String item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                            setText(null);
                        } else {
                            btn.setOnAction(event -> {
                                LibraryMember book = getTableView().getItems().get(getIndex());
                                showCheckoutRecords(book);
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

    private void loadData() { //

        // todo.. add members into list
        DataAccess da = new DataAccessFacade();
        HashMap<String, LibraryMember> libraryMemberMap = da.readMemberMap();
        int i = 0;
        for (Map.Entry<String, LibraryMember> e : libraryMemberMap.entrySet()) {
            list.add(i++, e.getValue());
        }

        tableView.getItems().setAll(list);
    }

    private void showCheckoutRecords(LibraryMember book) {
        CheckoutRecord checkoutRecord = book.getCheckoutRecord();

        TableColumn bookTitleCol = new TableColumn("Book Title");
        bookTitleCol.setCellValueFactory(new PropertyValueFactory<>("bookTile"));

        TableColumn bookIsbnCol = new TableColumn("ISBN");
        bookIsbnCol.setCellValueFactory(new PropertyValueFactory<>("bookIsbn"));

        TableColumn bookCopyNumCol = new TableColumn("Book Copy number");
        bookCopyNumCol.setCellValueFactory(new PropertyValueFactory<>("bookCopyNum"));

        TableColumn checkoutDateCol = new TableColumn("Checkout Date");
        checkoutDateCol.setCellValueFactory(new PropertyValueFactory<>("dateOfCheckout"));

        TableColumn dueDateCol = new TableColumn("Due Date");
        dueDateCol.setCellValueFactory(new PropertyValueFactory<>("dueDate"));


        TableView<CheckoutRecordEntry> table = new TableView<>();
        ObservableList<CheckoutRecordEntry> data = FXCollections.observableArrayList();

        int i = 0;
        for (CheckoutRecordEntry bc : checkoutRecord.getCheckoutRecordEntries()) {
            data.add(i++, bc);
        }

        table.setItems(data);
        table.getColumns().addAll(bookTitleCol, bookIsbnCol, bookCopyNumCol, checkoutDateCol, dueDateCol);

        Scene scene = new Scene(new Group());

        String css = this.getClass().getResource("member_list.css").toExternalForm();
        scene.getStylesheets().add(css);

        ((Group)scene.getRoot()).getChildren().addAll(table);
        Stage stage = new Stage(StageStyle.DECORATED);
        stage.setTitle("checkout records");
        stage.setScene(scene);
        stage.show();
    }
}
