package sokoban.view;

import javafx.beans.binding.DoubleBinding;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Pos;
import javafx.scene.image.ImageView;
import sokoban.model.cell.*;
import sokoban.viewmodel.CellViewModel4Play;

import java.util.List;
import java.util.Map;

public class CellView4Play extends CellView {
    private final CellViewModel4Play cellViewModel4Play;

    CellView4Play(CellViewModel4Play cellViewModel4Play, DoubleBinding cellWidthProperty, List<GameObject> elements) {
        this.cellViewModel4Play = cellViewModel4Play;
        this.widthProperty = cellWidthProperty;

        for (GameObject o : elements) {
            this.cellViewModel4Play.play(o);
        }

        setAlignment(Pos.CENTER);
        setImage(imageView, cellViewModel4Play.valueProperty());

        layoutControls();
        configureBindings();
    }

    private void layoutControls() {
        imageView.setPreserveRatio(true);
        imageViewMid.setPreserveRatio(true);
        imageViewTop.setPreserveRatio(true);

        numberText.getStyleClass().add("number");
        getChildren().addAll(imageView, imageViewMid, imageViewTop, numberText);
    }

    private void configureBindings() {
        minWidthProperty().bind(widthProperty);
        minHeightProperty().bind(widthProperty);

        // adapte la largeur de l'image à celle de la cellule multipliée par l'échelle
        imageView.fitWidthProperty().bind(widthProperty);
        imageViewMid.fitWidthProperty().bind(widthProperty);
        imageViewTop.fitWidthProperty().bind(widthProperty);

        // quand la cellule change de valeur, adapter l'image affichée
        cellViewModel4Play.valueProperty().addListener(this::onValueChanged);
    }

    private void onValueChanged(ObservableValue<? extends Map<Integer, GameObject>> observableValue, Map<Integer, GameObject> oldValue, Map<Integer, GameObject> newValue) {
        setImage(imageView, newValue);
    }

    private void setImage(ImageView imageView, Map<Integer, GameObject> cellValue) {
        clearImages();
        if (cellValue.get(0) instanceof Wall) {
            imageView.setImage(images.get(CellValue.WALL));
        } else if (cellValue.get(0) instanceof Ground) {
            imageView.setImage(images.get(CellValue.GROUND));
        }
        if (cellValue.get(1) instanceof Player) {
            imageViewMid.setImage(images.get(CellValue.PLAYER));
        } else if (cellValue.get(1) instanceof Box) {
            imageViewMid.setImage(images.get(CellValue.BOX));
        }
        if (cellValue.get(2) instanceof Target) {
            imageViewTop.setImage(images.get(CellValue.TARGET));
        }
        if (cellValue.get(3) instanceof Box) {
            numberText.setText(((Box) cellValue.get(3)).getBoxNumber());
        }
    }

    private void clearImages() {
        imageView.setImage(null);
        imageViewMid.setImage(null);
        imageViewTop.setImage(null);
        numberText.setText(null);
    }
}
