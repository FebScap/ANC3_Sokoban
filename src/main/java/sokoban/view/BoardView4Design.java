package sokoban.view;

import javafx.beans.binding.Bindings;
import javafx.beans.binding.DoubleBinding;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import sokoban.model.Board4Design;
import sokoban.viewmodel.BoardViewModel4Design;

import java.io.File;
import java.util.Objects;

public class BoardView4Design extends BoardView {
    // ViewModel
    private final BoardViewModel4Design boardViewModel4Design;
    private final Stage primaryStage;

    // Constantes de mise en page
    private static final int SCENE_MIN_WIDTH = 800;
    private static final int SCENE_MIN_HEIGHT = 600;

    // Composants principaux
    private final Label headerLabel = new Label("");

    private final Label errorPlayer = new Label("A player is required");
    private final Label errorBox = new Label("A box is required");
    private final Label errorTarget = new Label("A Target is required");
    private final Label errorBoxesTargets = new Label("Number of boxes and targets must be equal");

    private final VBox headerBox = new VBox();
    private final MenuBar menuBar = new MenuBar();
    private final Menu menuFile = new Menu("File");
    private final Button playButton = new Button("Play");
    private final StackPane buttonPane = new StackPane();

    public BoardView4Design(Stage primaryStage, BoardViewModel4Design boardViewModel4Design) {
        this.boardViewModel4Design = boardViewModel4Design;
        this.primaryStage = primaryStage;
        start(primaryStage, null);
    }

    public BoardView4Design(Stage primaryStage, BoardViewModel4Design boardViewModel4Design, File file) {
        this.boardViewModel4Design = boardViewModel4Design;
        this.primaryStage = primaryStage;
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

        //creation et suppression du fichier temporaire
        boardViewModel4Design.saveActualBoard();
    }

    private void configMainComponents(Stage stage, File file) {
        stage.setTitle("Sokoban");
        this.setOnMouseClicked(e -> boardViewModel4Design.fileModified(stage, 2));
        createGrid(file);
        createHeader(stage);
        setupPlayButton();
    }

    private void setupPlayButton() {
        buttonPane.getChildren().add(playButton);
        setBottom(buttonPane);

        playButton.disableProperty().bind(
                errorPlayer.visibleProperty().or(
                        errorBox.visibleProperty()).or(
                        errorTarget.visibleProperty()).or(
                        errorBoxesTargets.visibleProperty())
        );

        playButton.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                boardViewModel4Design.playButton(primaryStage);
            }
        });
    }

    private void createHeader(Stage stage) {
        MenuItem menuItemNew = new MenuItem("New..."),
                menuItemOpen = new MenuItem("Open..."),
                menuItemSave = new MenuItem("Save As..."),
                menuItemExit = new MenuItem("Exit");
        menuFile.getItems().addAll(menuItemNew, menuItemOpen, menuItemSave, menuItemExit);

        //LISTENER DU MENU
        menuItemNew.setOnAction(e -> boardViewModel4Design.fileModified(stage, 0));
        menuItemOpen.setOnAction(e -> boardViewModel4Design.fileModified(stage, 1));
        menuItemSave.setOnAction(e -> boardViewModel4Design.save(stage));
        menuItemExit.setOnAction(e -> boardViewModel4Design.exit(stage));
        menuBar.getMenus().add(menuFile);

        headerLabel.textProperty().bind(boardViewModel4Design.filledCellsCountProperty()
                .asString("Number of filled cells: %d of " + boardViewModel4Design.maxFilledCells()));
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

        errorBoxesTargets.setTextFill(Color.INDIANRED);
        errorBoxesTargets.setVisible(false);
        errorBoxesTargets.setManaged(false);

        errorPlayer.visibleProperty().bind(Bindings.notEqual(boardViewModel4Design.filledPlayerCountProperty(), Board4Design.getNB_OF_PLAYER()));
        errorPlayer.managedProperty().bind(errorPlayer.visibleProperty());

        errorBox.visibleProperty().bind(Bindings.equal(boardViewModel4Design.filledBoxsCountProperty(), Board4Design.getMIN_OF_BOX()));
        errorBox.managedProperty().bind(errorBox.visibleProperty());

        errorTarget.visibleProperty().bind(Bindings.equal(boardViewModel4Design.filledTargetsCountProperty(), Board4Design.getMIN_OF_TARGET()));
        errorTarget.managedProperty().bind(errorTarget.visibleProperty());

        errorBoxesTargets.visibleProperty().bind(Bindings.notEqual(boardViewModel4Design.filledBoxsCountProperty(), boardViewModel4Design.filledTargetsCountProperty()));
        errorBoxesTargets.managedProperty().bind(errorBoxesTargets.visibleProperty());

        headerBox.getChildren().add(errorPlayer);
        headerBox.getChildren().add(errorBox);
        headerBox.getChildren().add(errorTarget);
        headerBox.getChildren().add(errorBoxesTargets);

    }

    private void createGrid(File file) {
        DoubleBinding menuWidth = Bindings.createDoubleBinding(
                ()-> Math.floor(Math.min((heightProperty().doubleValue() - 40),
                        (widthProperty().doubleValue() - 40) / widthProperty().doubleValue())
                ),
                heightProperty(),
                widthProperty());

        DoubleBinding gridWidth = Bindings.createDoubleBinding(
                () -> Math.floor((widthProperty().get() - menuWidth.get())
                        / boardViewModel4Design.gridWidth()) * boardViewModel4Design.gridWidth(),
                menuWidth,
                widthProperty()
        );

        DoubleBinding gridHeight = Bindings.createDoubleBinding(
                () -> Math.floor((heightProperty().get() - headerBox.heightProperty().get())
                        / boardViewModel4Design.gridHeight()) * boardViewModel4Design.gridHeight(),
                heightProperty(),
                headerBox.heightProperty());

        GridView4Design gridView4Design;
        if (file == null) {
            gridView4Design = new GridView4Design(boardViewModel4Design.getGridViewModel(),
                    gridWidth,
                    gridHeight,
                    boardViewModel4Design.gridWidth(),
                    boardViewModel4Design.gridHeight());
        } else {
            gridView4Design = new GridView4Design(boardViewModel4Design.getGridViewModel(),
                    gridWidth,
                    file);
        }

        //creation du menu
        MenuView menuView = new MenuView(boardViewModel4Design.getMenuViewModel(), menuWidth);
        menuView.maxWidthProperty().bind(widthProperty());
        menuView.maxHeightProperty().bind(heightProperty().subtract(headerBox.heightProperty()));

        menuView.setAlignment(Pos.CENTER);
        gridView4Design.setAlignment(Pos.CENTER);

        setLeft(menuView);
        setCenter(gridView4Design);
    }
}
