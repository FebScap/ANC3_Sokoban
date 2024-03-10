package sokoban.view;

import javafx.beans.binding.Bindings;
import javafx.beans.binding.DoubleBinding;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import sokoban.model.Board;
import sokoban.viewmodel.BoardViewModel;
import sokoban.viewmodel.MenuViewModel;

import java.util.Objects;

public class BoardView extends BorderPane {

    // ViewModel
    private final BoardViewModel boardViewModel;

    // Constantes de mise en page
    private static final int SCENE_MIN_WIDTH = 420;
    private static final int SCENE_MIN_HEIGHT = 420;

    // Composants principaux
    private final Label headerLabel = new Label("");

    private final Label errorPlayer = new Label("A player is required");
    private final Label errorBox = new Label("A box is required");
    private final Label errorTarget = new Label("A Target is required");
    private final Label errorBoxsTargets = new Label("Number of boxs and targets must be equal");


    private final VBox headerBox = new VBox();

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
        createMenu();
        createHeader();
    }

    private void createHeader() {
        headerLabel.textProperty().bind(boardViewModel.filledCellsCountProperty()
                .asString("Number of filled cells: %d of " + boardViewModel.maxFilledCells()));
        headerLabel.getStyleClass().add("header");
        headerBox.getChildren().add(headerLabel);
        headerBox.setAlignment(Pos.CENTER);
        setTop(headerBox);
        

        //errorPlayer.textProperty().bind(boardViewModel.filledPlayerCountProperty().asString("A player is required"));

        errorPlayer.setTextFill(Color.INDIANRED);
        errorPlayer.setVisible(false);
        errorPlayer.setManaged(false);

        errorBox.setTextFill(Color.INDIANRED);
        errorBox.setVisible(false);
        errorBox.setManaged(false);

        errorTarget.setTextFill(Color.INDIANRED);
        errorTarget.setVisible(false);
        errorTarget.setManaged(false);

        errorBoxsTargets.setTextFill(Color.INDIANRED);
        errorBoxsTargets.setVisible(false);
        errorBoxsTargets.setManaged(false);

        errorPlayer.visibleProperty().bind(Bindings.notEqual(boardViewModel.filledPlayerCountProperty(), Board.getNB_OF_PLAYER()));
        errorPlayer.managedProperty().bind(errorPlayer.visibleProperty());

        errorBox.visibleProperty().bind(Bindings.equal(boardViewModel.filledBoxsCountProperty(), Board.getMIN_OF_BOX()));
        errorBox.managedProperty().bind(errorBox.visibleProperty());

        errorTarget.visibleProperty().bind(Bindings.equal(boardViewModel.filledTargetsCountProperty(), Board.getMIN_OF_TARGET()));
        errorTarget.managedProperty().bind(errorTarget.visibleProperty());

        errorBoxsTargets.visibleProperty().bind(Bindings.notEqual(boardViewModel.filledBoxsCountProperty(), boardViewModel.filledTargetsCountProperty()));
        errorBoxsTargets.managedProperty().bind(errorBoxsTargets.visibleProperty());

        headerBox.getChildren().add(errorPlayer);
        headerBox.getChildren().add(errorBox);
        headerBox.getChildren().add(errorTarget);
        headerBox.getChildren().add(errorBoxsTargets);

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

    private void createMenu() {
        DoubleBinding menuWidth = (DoubleBinding) Bindings.min(
                widthProperty().subtract(700),
                heightProperty().subtract(headerBox.heightProperty())
        );

        MenuView menuView = new MenuView(boardViewModel.getMenuViewModel(), menuWidth);

        // Grille carrée
        menuView.minHeightProperty().bind(menuWidth);
        menuView.minWidthProperty().bind(menuWidth);
        menuView.maxHeightProperty().bind(menuWidth);
        menuView.maxWidthProperty().bind(menuWidth);

        setLeft(menuView);
    }
}
