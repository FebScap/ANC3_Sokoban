package sokoban.view;

import javafx.beans.binding.Bindings;
import javafx.beans.binding.DoubleBinding;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import sokoban.viewmodel.BoardViewModel;

import java.util.Objects;

public class BoardView extends BorderPane {

    // ViewModel
    private final BoardViewModel boardViewModel;

    // Constantes de mise en page
    private static final int SCENE_MIN_WIDTH = 420;
    private static final int SCENE_MIN_HEIGHT = 420;

    // Composants principaux
    private final Label headerLabel = new Label("");
    private final HBox headerBox = new HBox();

    public BoardView(Stage primaryStage, BoardViewModel boardViewModel) {
        this.boardViewModel = boardViewModel;
        start(primaryStage);
    }

    private void start(Stage stage) {
        // Mise en place des composants principaux
        configMainComponents(stage);

        // Mise en place de la scène et affichage de la fenêtre
        Scene scene = new Scene(this, SCENE_MIN_WIDTH, SCENE_MIN_HEIGHT);
        String cssFile = Objects.requireNonNull(getClass().getResource("/styles.css")).toExternalForm();
        scene.getStylesheets().add(cssFile);
        stage.setScene(scene);
        stage.show();
        stage.setMinHeight(stage.getHeight());
        stage.setMinWidth(stage.getWidth());
    }

    private void configMainComponents(Stage stage) {
        stage.setTitle("Sokoban");

        createGrid();
        createHeader();
    }

    private void createHeader() {
        headerLabel.textProperty().bind(boardViewModel.filledCellsCountProperty()
                .asString("Number of filled cells: %d of " + boardViewModel.maxFilledCells()));
        headerLabel.getStyleClass().add("header");
        headerBox.getChildren().add(headerLabel);
        headerBox.setAlignment(Pos.CENTER);
        setTop(headerBox);
    }

    private void createGrid() {
        DoubleBinding gridWidth = (DoubleBinding) Bindings.min(
                widthProperty(),
                heightProperty().subtract(headerBox.heightProperty())
        );

        GridView gridView = new GridView(boardViewModel.getGridViewModel(), gridWidth);

        // Grille carrée
        gridView.minHeightProperty().bind(gridWidth);
        gridView.minWidthProperty().bind(gridWidth);
        gridView.maxHeightProperty().bind(gridWidth);
        gridView.maxWidthProperty().bind(gridWidth);

        setCenter(gridView);
    }
}
