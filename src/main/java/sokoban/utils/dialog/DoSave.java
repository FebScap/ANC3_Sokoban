package sokoban.utils.dialog;

import javafx.scene.control.*;

import java.util.Optional;

public class DoSave {
    /**
     * Ouvre une dialog window qui demande si l'utilisateur souhaite sauvegarder
     *
     * @return
     * 0 if selected Save;
     * 1 if selected Don't Save;
     * 2 if cancelled operation;
     */
    public static int doSave() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Save");
        alert.setHeaderText("Your board has been modified.");
        alert.setContentText("Do you want to save your changes?");

        ButtonType buttonTypeYes = new ButtonType("Yes");
        ButtonType buttonTypeNo = new ButtonType("No");
        ButtonType buttonTypeCancel = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);

        alert.getButtonTypes().setAll(buttonTypeYes, buttonTypeNo, buttonTypeCancel);

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == buttonTypeYes) {
            return 0;
        } else if (result.get() == buttonTypeNo) {
            return 1;
        } else {
            return 2;
        }
    }
}
