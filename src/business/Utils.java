package business;

import javafx.scene.control.Alert;

public class Utils{

    public static void alertError(String head, String errorMsg) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText(head);
        alert.setContentText(errorMsg);
        alert.showAndWait();
    }

    public static String getBookCopyUniqueKey(Book book, BookCopy bookCopy){
        return String.format("%s_%d", book.getIsbn(), bookCopy.getCopyNum());
    }
}
