package business;

import java.io.IOException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

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
