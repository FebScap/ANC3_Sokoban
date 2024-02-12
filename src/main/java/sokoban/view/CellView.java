package sokoban.view;

import javafx.beans.Observable;
import javafx.beans.binding.DoubleBinding;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Pos;
import javafx.scene.effect.Effect;
import javafx.scene.effect.Light;
import javafx.scene.effect.Lighting;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import sokoban.model.Cell.CellValue;
import sokoban.viewmodel.CellViewModel;

import java.util.HashMap;

class CellView extends StackPane {

    private static final HashMap<CellValue, Image> images = new HashMap<>(){{
        put(CellValue.BOX, new Image("box.png"));
        put(CellValue.TARGET, new Image("goal.png"));
        put(CellValue.GROUND, new Image("ground.png"));
        put(CellValue.PLAYER, new Image("player.png"));
        put(CellValue.WALL, new Image("wall.png"));
    }};

    private final CellViewModel viewModel;
    private final DoubleBinding widthProperty;

    private final ImageView imageView = new ImageView();

    CellView(CellViewModel cellViewModel, DoubleBinding cellWidthProperty) {
        this.viewModel = cellViewModel;
        this.widthProperty = cellWidthProperty;

        setAlignment(Pos.CENTER);
        setImage(imageView, CellValue.GROUND);

        layoutControls();
        configureBindings();
    }

    private void layoutControls() {
        imageView.setPreserveRatio(true);

        getChildren().addAll(imageView);
    }

    private void configureBindings() {
        minWidthProperty().bind(widthProperty);
        minHeightProperty().bind(widthProperty);

        // adapte la largeur de l'image à celle de la cellule multipliée par l'échelle
        imageView.fitWidthProperty().bind(widthProperty);

        // un clic sur la cellule permet de jouer celle-ci
        this.setOnMouseClicked(e -> viewModel.play(CellValue.WALL));

        // gère le survol de la cellule avec la souris
        hoverProperty().addListener(this::hoverChanged);

        // quand la cellule change de valeur, adapter l'image affichée
        viewModel.valueProperty().addListener(this::onClickEvent);
    }

    private void onClickEvent(ObservableValue<? extends CellValue> observableValue, CellValue oldValue, CellValue newValue) {
        imageView.setImage(images.get(newValue));
    }

    private void setImage(ImageView imageView, CellValue cellValue) {
        imageView.setImage(images.get(cellValue));
    }

    private void hoverChanged(ObservableValue<? extends Boolean> obs, Boolean oldVal, Boolean newVal) {
        // si on arrête le survol de la cellule, on remet l'échelle à sa valeur par défaut
        if (newVal) {
            Lighting lighting = new Lighting();
            lighting.setLight(new Light.Distant(0, 100, Color.GREY));
            imageView.setEffect(lighting);
        } else {
            imageView.setEffect(null);
        }
    }

}
