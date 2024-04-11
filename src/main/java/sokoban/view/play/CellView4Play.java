package sokoban.view.play;

import javafx.beans.binding.DoubleBinding;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Pos;
import javafx.scene.effect.Light;
import javafx.scene.effect.Lighting;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import sokoban.model.api.cell.*;
import sokoban.view.api.CellView;
import sokoban.view.design.MenuView;
import sokoban.viewmodel.play.CellViewModel4Play;

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

        getChildren().addAll(imageView, imageViewMid, imageViewTop);
    }

    private void configureBindings() {
        minWidthProperty().bind(widthProperty);
        minHeightProperty().bind(widthProperty);

        // adapte la largeur de l'image à celle de la cellule multipliée par l'échelle
        imageView.fitWidthProperty().bind(widthProperty);
        imageViewMid.fitWidthProperty().bind(widthProperty);
        imageViewTop.fitWidthProperty().bind(widthProperty);


        // un clic sur la cellule permet de jouer celle-ci
        this.setOnMouseClicked(this::playEvent);

        //DRAG EVENT
        this.setOnDragDetected(mouseEvent -> this.startFullDrag());
        this.setOnMouseDragOver(this::playEvent);

        // gère le survol de la cellule avec la souris
        hoverProperty().addListener(this::hoverChanged);

        // quand la cellule change de valeur, adapter l'image affichée
        cellViewModel4Play.valueProperty().addListener(this::onValueChanged);
    }

    private void playEvent(MouseEvent e) {
        if (e.getButton() == MouseButton.PRIMARY) {
            switch (MenuView.cellValue) {
                case PLAYER -> cellViewModel4Play.play(new Player());
                case BOX -> cellViewModel4Play.play(new Box());
                case WALL -> cellViewModel4Play.play(new Wall());
                case GROUND -> cellViewModel4Play.play(new Ground());
                case TARGET -> cellViewModel4Play.play(new Target());
            }
        } if (e.getButton() == MouseButton.SECONDARY){
            cellViewModel4Play.play(new Ground());
        }
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
    }

    private void clearImages() {
        imageView.setImage(null);
        imageViewMid.setImage(null);
        imageViewTop.setImage(null);
    }

    private void hoverChanged(ObservableValue<? extends Boolean> obs, Boolean oldVal, Boolean newVal) {
        if (newVal) {
            Lighting lighting = new Lighting();
            lighting.setLight(new Light.Distant(0, 100, Color.GREY));
            imageView.setEffect(lighting);
        } else {
            imageView.setEffect(null);
        }
    }
}
