package sokoban.view;

import javafx.beans.binding.Bindings;
import javafx.beans.binding.DoubleBinding;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import sokoban.viewmodel.BoardViewModel4Play;

import java.util.Objects;

public class BoardView4Play extends BoardView {
    // ViewModel
    private final BoardViewModel4Play boardViewModel4Play;
    private final Stage primaryStage;

    // Constantes de mise en page
    private static int SCENE_MIN_WIDTH;
    private static int SCENE_MIN_HEIGHT;

    // Composants principaux
    private final Label headerLabel = new Label("Score");

    private final Label movesPlayedCount = new Label();
    private final Label goalsReachedCount = new Label();
    private final Label victory = new Label();


    private final VBox headerBox = new VBox();
    private final Button finishButton = new Button("Finish");
    private final StackPane buttonPane = new StackPane();

    public BoardView4Play(Stage primaryStage, BoardViewModel4Play boardViewModel4Play) {
        this.boardViewModel4Play = boardViewModel4Play;
        this.primaryStage = primaryStage;
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
    }

    private void configMainComponents(Stage stage) {
        stage.setTitle("Sokoban");
        createGrid();
        createHeader(stage);
        setupFinishButton();

        // Keyboard events
        this.setOnKeyReleased(this::onKeyPressedEvent);
    }

    private void onKeyPressedEvent(KeyEvent keyEvent) {
        switch (keyEvent.getCode()) {
            case Z, UP -> boardViewModel4Play.movePlayer(0);
            case D, RIGHT -> boardViewModel4Play.movePlayer(1);
            case S, DOWN -> boardViewModel4Play.movePlayer(2);
            case Q, LEFT -> boardViewModel4Play.movePlayer(3);
        }
    }

    private void setupFinishButton() {
        buttonPane.getChildren().add(finishButton);
        setBottom(buttonPane);
        finishButton.addEventHandler(MouseEvent.MOUSE_CLICKED, mouseEvent -> {
            boardViewModel4Play.finishButton(primaryStage);
        });
    }

    private void createHeader(Stage stage) {
        headerLabel.getStyleClass().add("header");
        headerBox.getChildren().add(headerLabel);
        headerBox.setAlignment(Pos.CENTER_LEFT);
        DoubleBinding headerPadding = Bindings.createDoubleBinding(
                ()-> stage.widthProperty().doubleValue() / 5,
                stage.widthProperty());
        headerBox.setPadding(new Insets(0, 0, 0, headerPadding.getValue()));
        setTop(headerBox);

        movesPlayedCount.textProperty().bind(boardViewModel4Play.getMoveCount().asString("Number of moves played: %d"));
        headerBox.getChildren().add(movesPlayedCount);

        goalsReachedCount.textProperty().bind(boardViewModel4Play.getGoalsReachedCount().asString("Number of goals reached: %d of " + boardViewModel4Play.getFilledBoxsCountProperty().getValue()));
        headerBox.getChildren().add(goalsReachedCount);

        headerBox.getChildren().add(victory);
        victory.setVisible(false);
        victory.setManaged(false);

        victory.getStyleClass().add("header");
        victory.textProperty().bind(boardViewModel4Play.getMoveCount().asString("You won in %d moves, congratulations !!"));
        victory.visibleProperty().bind(boardViewModel4Play.getVictoryProperty());
        victory.managedProperty().bind(victory.visibleProperty());
    }

    private void createGrid() {
        DoubleBinding menuWidth = Bindings.createDoubleBinding(
                ()-> Math.floor(Math.min((heightProperty().doubleValue() - 40),
                        (widthProperty().doubleValue() - 40) / widthProperty().doubleValue())
                ),
                heightProperty(),
                widthProperty());

        DoubleBinding gridWidth = Bindings.createDoubleBinding(
                () -> Math.floor((widthProperty().get() - menuWidth.get())
                        / boardViewModel4Play.gridWidth()) * boardViewModel4Play.gridWidth(),
                menuWidth,
                widthProperty()
        );

        SCENE_MIN_HEIGHT = (int) (400 + (boardViewModel4Play.gridWidth()*30) + buttonPane.getHeight() + headerBox.getHeight());
        SCENE_MIN_WIDTH = 400 + (boardViewModel4Play.gridHeight()*30);
        GridView4Play gridView4Play;
        gridView4Play = new GridView4Play(boardViewModel4Play.getGridViewModel4Play(),
                    gridWidth, boardViewModel4Play.getActualBoard().getValue());
        setCenter(gridView4Play);
    }
}
