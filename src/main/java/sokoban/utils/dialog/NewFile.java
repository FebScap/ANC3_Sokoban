package sokoban.utils.dialog;

import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.util.Pair;

import java.util.Optional;

public class NewFile {
    private static final Label errorWidthMin = new Label("Width must be at least 10");
    private static final Label errorWidthMax = new Label("Width must be at most 50");
    private static final Label errorHeightMin = new Label("Height must be at least 10");
    private static final Label errorHeightMax = new Label("Height must be at most 50");
    /**
     * Ouvre une dialog window pour créer un nouveau fichier
     *
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
        grid.setPadding(new Insets(20, 20, 20, 20));

        TextField width = new TextField();
        width.setPromptText("Width");
        TextField height = new TextField();
        height.setPromptText("Height");

        grid.add(new Label("Width"), 0, 0);
        grid.add(width, 1, 0);

        grid.add(new Label("Height"), 0, 1);
        grid.add(height, 1, 1);



        BooleanProperty widthValidMin = new SimpleBooleanProperty(true);
        BooleanProperty widthValidMax = new SimpleBooleanProperty(true);
        BooleanProperty heightValidMin = new SimpleBooleanProperty(true);
        BooleanProperty heightValidMax = new SimpleBooleanProperty(true);

        widthValidMin.bind(Bindings.createBooleanBinding(() ->
                        !width.getText().isEmpty() && (Integer.parseInt(width.getText()) < 10),
                width.textProperty()));

        widthValidMax.bind(Bindings.createBooleanBinding(() ->
                        !width.getText().isEmpty() && (Integer.parseInt(width.getText()) > 50),
                width.textProperty()));

        heightValidMin.bind(Bindings.createBooleanBinding(() ->
                        !height.getText().isEmpty() && (Integer.parseInt(height.getText()) < 10),
                height.textProperty()));

        heightValidMax.bind(Bindings.createBooleanBinding(() ->
                        !height.getText().isEmpty() && (Integer.parseInt(height.getText()) > 50),
                height.textProperty()));

// Ajout des labels d'erreur à la grille
        grid.add(errorWidthMin, 2, 0);
        grid.add(errorWidthMax, 3, 0);
        grid.add(errorHeightMin, 2, 1);
        grid.add(errorHeightMax, 3, 1);

// Liaison de la visibilité des labels d'erreur avec la validité des champs de texte
        errorWidthMin.visibleProperty().bind(widthValidMin);
        errorWidthMax.visibleProperty().bind(widthValidMax);
        errorHeightMin.visibleProperty().bind(heightValidMin);
        errorHeightMax.visibleProperty().bind(heightValidMax);

        Node okButton = dialog.getDialogPane().lookupButton(okButtonType);
        okButton.setDisable(true);

// Do some validation (using the Java 8 lambda syntax).
        width.textProperty().addListener((observable, oldValue, newValue) -> {
            okButton.setDisable(newValue.trim().isEmpty() || widthValidMin.get() || widthValidMax.get() || heightValidMin.get() || heightValidMax.get());
        });

        height.textProperty().addListener((observable, oldValue, newValue) -> {
            okButton.setDisable(newValue.trim().isEmpty() || widthValidMin.get() || widthValidMax.get() || heightValidMin.get() || heightValidMax.get());
        });




        dialog.getDialogPane().setContent(grid);

        Platform.runLater(width::requestFocus);

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == okButtonType) {
                return new Pair<>(width.getText(), height.getText());
            }
            return null;
        });

        return dialog.showAndWait();
    }
}
