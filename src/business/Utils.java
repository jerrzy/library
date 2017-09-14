package business;

import javafx.scene.control.Alert;

public class Utils{

    public static void alertError(String head, String errorMsg) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText(head);
        alert.setContentText(errorMsg);
        alert.showAndWait();
    }
}
