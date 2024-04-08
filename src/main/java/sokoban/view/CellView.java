package sokoban.view;

import javafx.beans.binding.DoubleBinding;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Pos;
import javafx.scene.effect.Light;
import javafx.scene.effect.Lighting;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import sokoban.model.Cell.*;
import sokoban.viewmodel.CellViewModel;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

class CellView extends StackPane {

    private static final HashMap<GameObject, Image> images = new HashMap<>(){{
        put(new Box(), new Image("box.png"));
        put(new Target(), new Image("goal.png"));
        put(new Ground(), new Image("ground.png"));
        put(new Player(), new Image("player.png"));
        put(new Wall(), new Image("wall.png"));
    }};

    private final CellViewModel viewModel;
    private final DoubleBinding widthProperty;

    private final ImageView imageView = new ImageView();
    private final ImageView imageViewMid = new ImageView();
    private final ImageView imageViewTop = new ImageView();

    CellView(CellViewModel cellViewModel, DoubleBinding cellWidthProperty, List<GameObject> elements) {
        this.viewModel = cellViewModel;
        this.widthProperty = cellWidthProperty;

        for (GameObject o : elements) {
            this.viewModel.play(o);
        }

        setAlignment(Pos.CENTER);
        setImage(imageView, viewModel.valueProperty());

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
        viewModel.valueProperty().addListener(this::onValueChanged);
    }

    private void playEvent(MouseEvent e) {
        if (e.getButton() == MouseButton.PRIMARY) {
            viewModel.play(MenuView.cellValue);
        } if (e.getButton() == MouseButton.SECONDARY){
            viewModel.play(new Ground());
        }
    }


    private void onValueChanged(ObservableValue<? extends Map<Integer, GameObject>> observableValue, Map<Integer, GameObject> oldValue, Map<Integer, GameObject> newValue) {
        setImage(imageView, newValue);
    }

    private void setImage(ImageView imageView, Map<Integer, GameObject> cellValue) {
        imageView.setImage(images.get(cellValue.get(0)));
        imageViewMid.setImage(images.get(cellValue.get(1)));
        imageViewTop.setImage(images.get(cellValue.get(2)));
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
