package sokoban.utils;

import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.util.Pair;

import java.util.Optional;

public class DialogWindow {
    /**
     * Ouvre une dialog window pour créer un nouveau fichier
     * @return width and height
     */
    public static Optional<Pair<String, String>> NewFile() {
        Dialog<Pair<String, String>> dialog = new Dialog<>();
        dialog.setHeaderText("Give new game dimensions");

        ButtonType okButtonType = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(okButtonType, ButtonType.CANCEL);

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        TextField width = new TextField();
        width.setPromptText("Width");
        TextField height = new TextField();
        height.setPromptText("Height");

        grid.add(new Label("Width"), 0, 0);
        grid.add(width, 1, 0);
        grid.add(new Label("Height"), 0, 1);
        grid.add(height, 1, 1);

        Node okButton = dialog.getDialogPane().lookupButton(okButtonType);
        okButton.setDisable(true);

        // Do some validation (using the Java 8 lambda syntax).
        width.textProperty().addListener((observable, oldValue, newValue) -> {
            okButton.setDisable(newValue.trim().isEmpty());
        });

        dialog.getDialogPane().setContent(grid);

        Platform.runLater(width::requestFocus);

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == okButtonType) {
                return new Pair<>(width.getText(), height.getText());
            }
            return null;
        });

        Optional<Pair<String, String>> result = dialog.showAndWait();


        return result;
    }
}
