package sokoban.view;

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
import javafx.stage.Stage;
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
    private final VBox headerBox = new VBox();
    private final MenuBar menuBar = new MenuBar();
    private final Menu menuFile = new Menu("File");

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
        MenuItem menuItemNew = new MenuItem("New..."),
                menuItemOpen = new MenuItem("Open..."),
                menuItemSave = new MenuItem("Save As..."),
                menuItemExit = new MenuItem("Exit");
        menuFile.getItems().addAll(menuItemNew, menuItemOpen, menuItemSave, menuItemExit);

        //LISTENER DU MENU
        menuItemNew.setOnAction(e -> {
            //TODO : Nouveau fichier
        });
        menuItemOpen.setOnAction(e -> {
            //TODO : Open File
        });
        menuItemSave.setOnAction(e -> {
            //TODO : Sauvegarder
        });
        menuItemExit.setOnAction(e -> {
            //TODO : Quitter
        });
        menuBar.getMenus().add(menuFile);

        headerLabel.textProperty().bind(boardViewModel.filledCellsCountProperty()
                .asString("Number of filled cells: %d of " + boardViewModel.maxFilledCells()));
        headerLabel.getStyleClass().add("header");

        headerBox.getChildren().add(menuBar);
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
