package sokoban.view;

import javafx.beans.binding.DoubleBinding;
import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import sokoban.model.Cell.*;
import javafx.scene.layout.StackPane;
import sokoban.viewmodel.ToolViewModel;

import java.util.HashMap;

public class ToolView extends StackPane {
    private final ToolViewModel viewModel;
    private final DoubleBinding widthProperty;
    private static final HashMap<GameObject, Image> images = new HashMap<>(){{
        put(new Box(), new Image("box.png"));
        put(new Target(), new Image("goal.png"));
        put(new Ground(), new Image("ground.png"));
        put(new Player(), new Image("player.png"));
        put(new Wall(), new Image("wall.png"));
    }};

    private final ImageView imageView = new ImageView();

    private final GameObject current;
    private static ToolView selected = null;

    ToolView(ToolViewModel toolViewModel, DoubleBinding cellWidthProperty, GameObject value) {
        this.viewModel = toolViewModel;
        this.widthProperty = cellWidthProperty;
        this.current = value;


        setAlignment(Pos.CENTER);
        imageView.setImage(images.get(value));

        layoutControls();
        configureBindings();
    }

    private void configureBindings() {
        minWidthProperty().bind(widthProperty);
        minHeightProperty().bind(widthProperty);

        // adapte la largeur de l'image à celle de la cellule multipliée par l'échelle
        imageView.fitWidthProperty().bind(widthProperty);

        // un clic sur la cellule permet de jouer celle-ci
        this.setOnMouseClicked(this::onClickEvent);
    }

    private void onClickEvent(MouseEvent mouseEvent) {
        MenuView.setCellValue(current);
        ToolView cell = (ToolView) mouseEvent.getSource();
        cell.setBorder(new Border(new BorderStroke(Color.BLUE,
                BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(5))));
        if (selected != null) {
            selected.setBorder(null);
        }
        selected = cell;
    }

    private void layoutControls() {
        imageView.setPreserveRatio(true);
        getChildren().addAll(imageView);
    }
}
