package business;

import dataaccess.DataAccess;
import dataaccess.DataAccessFacade;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class CheckoutRecordService{
    public void showCheckoutRecords(String memberId) throws LibrarySystemException {
        if(isEmpty(memberId)){
            throw new LibrarySystemException("member id can not be empty!" );
        }
        DataAccess da = new DataAccessFacade();

        LibraryMember member = da.findMemberById(memberId);

        if (member == null) {
            throw new LibrarySystemException("member doesn't exist! memberId=" + memberId);
        }

        CheckoutRecord checkoutRecord = member.getCheckoutRecord();

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

        table.setMinWidth(650);

        ObservableList<CheckoutRecordEntry> data = FXCollections.observableArrayList();

        int i = 0;
        for (CheckoutRecordEntry bc : checkoutRecord.getCheckoutRecordEntries()) {
            data.add(i++, bc);
        }

        table.setItems(data);
        table.getColumns().addAll(bookTitleCol, bookIsbnCol, bookCopyNumCol, checkoutDateCol, dueDateCol);

        Scene scene = new Scene(new Group());

        String css = this.getClass().getResource("../ui/listmember/member_list.css").toExternalForm();
        scene.getStylesheets().add(css);

        ((Group)scene.getRoot()).getChildren().addAll(table);
        Stage stage = new Stage(StageStyle.DECORATED);
        stage.setTitle(member.getFirstName() + " checkout records");
        stage.setScene(scene);
        stage.show();
    }

    private boolean isEmpty(String content) {
        if (content == null || content.trim().equals("")) {
            return true;
        }

        return false;
    }
}
