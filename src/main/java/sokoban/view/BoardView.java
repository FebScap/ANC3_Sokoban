package sokoban.view;

import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.DoubleBinding;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import sokoban.model.Board;
import javafx.util.Pair;
import sokoban.model.Board;
import sokoban.utils.DialogWindow;
import sokoban.viewmodel.BoardViewModel;

import java.io.File;
import java.util.Objects;
import java.util.Optional;

public class BoardView extends BorderPane {
    // ViewModel
    private final BoardViewModel boardViewModel;

    // Constantes de mise en page
    private static final int SCENE_MIN_WIDTH = 800;
    private static final int SCENE_MIN_HEIGHT = 600;

    // Composants principaux
    private final Label headerLabel = new Label("");

    private final Label errorPlayer = new Label("A player is required");
    private final Label errorBox = new Label("A box is required");
    private final Label errorTarget = new Label("A Target is required");
    private final Label errorBoxsTargets = new Label("Number of boxs and targets must be equal");

    private final VBox headerBox = new VBox();
    private final MenuBar menuBar = new MenuBar();
    private final Menu menuFile = new Menu("File");

    public BoardView(Stage primaryStage, BoardViewModel boardViewModel) {
        this.boardViewModel = boardViewModel;
        start(primaryStage, null);
    }

    public BoardView(Stage primaryStage, BoardViewModel boardViewModel, File file) {
        this.boardViewModel = boardViewModel;
        start(primaryStage, file);
    }

    private void start(Stage stage, File file) {
        // Mise en place des composants principaux
        configMainComponents(stage, file);

        // Mise en place de la scène et affichage de la fenêtre
        Scene scene = new Scene(this, SCENE_MIN_WIDTH, SCENE_MIN_HEIGHT);
        String cssFile = Objects.requireNonNull(getClass().getResource("/styles.css")).toExternalForm();
        scene.getStylesheets().add(cssFile);

        stage.setScene(scene);
        stage.show();
        stage.setMinHeight(stage.getHeight());
        stage.setMinWidth(stage.getWidth());
    }

    private void configMainComponents(Stage stage, File file) {
        stage.setTitle("Sokoban");
        createGrid(file);
        createMenu();
        createHeader(stage);
    }

    private void createHeader(Stage stage) {
        MenuItem menuItemNew = new MenuItem("New..."),
                menuItemOpen = new MenuItem("Open..."),
                menuItemSave = new MenuItem("Save As..."),
                menuItemExit = new MenuItem("Exit");
        menuFile.getItems().addAll(menuItemNew, menuItemOpen, menuItemSave, menuItemExit);

        //LISTENER DU MENU
        menuItemNew.setOnAction(e -> {
            boardViewModel.NewItem(stage);
        });
        menuItemOpen.setOnAction(e -> {
            boardViewModel.OpenFile(stage);
        });
        menuItemSave.setOnAction(e -> {
            boardViewModel.Save(stage);
        });
        menuItemExit.setOnAction(e -> {
            boardViewModel.Exit(stage);
        });
        menuBar.getMenus().add(menuFile);

        headerLabel.textProperty().bind(boardViewModel.filledCellsCountProperty()
                .asString("Number of filled cells: %d of " + boardViewModel.maxFilledCells()));
        headerLabel.getStyleClass().add("header");

        headerBox.getChildren().add(menuBar);
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

    private void createGrid(File file) {
        DoubleBinding gridWidth = (DoubleBinding) Bindings.min(
                widthProperty(),
                heightProperty().subtract(headerBox.heightProperty())
        );

        GridView gridView;
        if (file == null) {
            gridView = new GridView(boardViewModel.getGridViewModel(),
                    gridWidth,
                    boardViewModel.gridWidth(),
                    boardViewModel.gridHeight());
        } else {
            gridView = new GridView(boardViewModel.getGridViewModel(),
                    gridWidth,
                    file);
        }

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
        menuView.maxWidthProperty().bind(widthProperty());
        menuView.maxHeightProperty().bind(heightProperty().subtract(headerBox.heightProperty()));

        setLeft(menuView);
    }
}
