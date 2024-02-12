package sokoban.view;

import javafx.beans.binding.DoubleBinding;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
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

        // quand la cellule change de valeur, adapter l'image affichée
        viewModel.valueProperty().addListener((obs, old, newVal) -> setImage(imageView, newVal));
    }

    private void setImage(ImageView imageView, CellValue cellValue) {
        imageView.setImage(images.get(cellValue));
    }
}
