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
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseDragEvent;
import javafx.scene.input.MouseEvent;
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

    CellView(CellViewModel cellViewModel, DoubleBinding cellWidthProperty, CellValue value) {
        this.viewModel = cellViewModel;
        this.widthProperty = cellWidthProperty;

        setAlignment(Pos.CENTER);
        setImage(imageView, value);

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
        this.setOnMouseClicked(this::onClickEvent);
        // TODO : startFullDrag();
        //this.setOnMouseDragged(this::onDragEvent);
        //this.setOnMouseDragReleased(this::onDragEvent);
        //this.setOnMouseExited(this::onDragEvent);
        //this.setOnMouseReleased(this::onDragEvent);


        // gère le survol de la cellule avec la souris
        hoverProperty().addListener(this::hoverChanged);

        // quand la cellule change de valeur, adapter l'image affichée
        viewModel.valueProperty().addListener(this::onValueChanged);
    }

    private void onClickEvent(MouseEvent e) {
        if (e.getButton() == MouseButton.PRIMARY) {
            viewModel.play(MenuView.cellValue);
        }
        if (e.getButton() == MouseButton.SECONDARY){
            viewModel.play(CellValue.GROUND);
        }
    }

    private void onDragEvent(MouseEvent e) {
        //System.out.println("drag");
        //System.out.println(e.getButton());
        //System.out.println(e.getButton() == MouseButton.PRIMARY);
        //System.out.println(viewModel.getLine());
        //System.out.println(viewModel.getCol());
        if (e.getButton() == MouseButton.PRIMARY) {
            viewModel.play(MenuView.cellValue);
        }
    }


    private void onValueChanged(ObservableValue<? extends CellValue> observableValue, CellValue oldValue, CellValue newValue) {
        setImage(imageView, newValue);
    }

    private void setImage(ImageView imageView, CellValue cellValue) {
        imageView.setImage(images.get(cellValue));
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
