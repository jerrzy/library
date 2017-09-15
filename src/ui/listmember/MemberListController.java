package ui.listmember;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

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
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Callback;

public class MemberListController implements Initializable{

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
    private TableColumn memActionCol;

    @FXML
    private TextField searchMemberId;

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
        addressCol.setCellValueFactory(new PropertyValueFactory<>("addressStr"));

        memActionCol.setCellValueFactory(new PropertyValueFactory<>("ggg"));

        Callback<TableColumn<LibraryMember, String>, TableCell<LibraryMember, String>> cellFactory = new Callback<TableColumn<LibraryMember, String>, TableCell<LibraryMember, String>>(){
            @Override
            public TableCell call(final TableColumn<LibraryMember, String> param) {
                final TableCell<LibraryMember, String> cell = new TableCell<LibraryMember, String>(){

                    final Button btn = new Button("records");

                    @Override
                    public void updateItem(String item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                            setText(null);
                        } else {
                            btn.setOnAction(event -> {
                                LibraryMember member = getTableView().getItems().get(getIndex());
                                try {
                                    new CheckoutRecordService().showCheckoutRecords(member.getMemberId());
                                }catch (LibrarySystemException e){
                                    //ignore
                                }
                            });
                            setGraphic(btn);
                            setText(null);
                        }
                    }
                };
                return cell;
            }
        };

        memActionCol.setCellFactory(cellFactory);
    }

    private void loadData() { //

        // add members into list
        DataAccess da = DataAccessFactory.getInstance();
        HashMap<String, LibraryMember> libraryMemberMap = da.readMemberMap();
        ObservableList<LibraryMember> list = FXCollections.observableArrayList();
        int i = 0;
        for (Map.Entry<String, LibraryMember> e : libraryMemberMap.entrySet()) {
            list.add(i++, e.getValue());
        }

        tableView.getItems().setAll(list);
    }


    public void searchMember(){
        String memberId = searchMemberId.getText();
        if(memberId == null || memberId.trim().equals("")){
            Utils.alertError("Alert","member id can not be empty!");
            return;
        }

        DataAccess da = DataAccessFactory.getInstance();
        LibraryMember member = da.findMemberById(memberId);

        if(member == null){
            Utils.alertError("Alert","member doesn't exist!");
            return;
        }

        ObservableList<LibraryMember> list = FXCollections.observableArrayList();

        list.add(0,member);

        tableView.getItems().clear();
        tableView.getItems().setAll(list);
    }

}
