package sokoban.view.play;

import javafx.beans.binding.Bindings;
import javafx.beans.binding.DoubleBinding;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import sokoban.view.api.BoardView;
import sokoban.viewmodel.play.BoardViewModel4Play;

import java.io.File;
import java.util.Objects;

public class BoardView4Play extends BoardView {
    // ViewModel
    private final BoardViewModel4Play boardViewModel4Play;
    private final Stage primaryStage;

    // Constantes de mise en page
    private static final int SCENE_MIN_WIDTH = 800;
    private static final int SCENE_MIN_HEIGHT = 600;

    // Composants principaux
    private final Label headerLabel = new Label("Score");

    private final Label movesPlayedText = new Label("Number of moves played: ");
    private final Label movesPlayedCount = new Label();
    private final Label goalsReachedText = new Label("Number of goals reached: ");
    private final Label goalsReachedCount = new Label();


    private final VBox headerBox = new VBox();
    private final Button finishButton = new Button("Finish");
    private final StackPane buttonPane = new StackPane();

    public BoardView4Play(Stage primaryStage, BoardViewModel4Play boardViewModel4Play, File file) {
        this.boardViewModel4Play = boardViewModel4Play;
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
    }

    private void configMainComponents(Stage stage, File file) {
        stage.setTitle("Sokoban");
        createGrid(file);
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
        headerBox.getChildren().add(headerLabel);
        headerBox.setAlignment(Pos.CENTER);
        setTop(headerBox);

        headerBox.getChildren().add(movesPlayedText);
        movesPlayedCount.textProperty().bind(boardViewModel4Play.getMoveCount().asString("%d"));
        headerBox.getChildren().add(movesPlayedCount);

        headerBox.getChildren().add(goalsReachedText);
        goalsReachedCount.textProperty().bind(boardViewModel4Play.getGoalsReachedCount().asString("%d of "));
        headerBox.getChildren().add(goalsReachedCount);

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
                        / boardViewModel4Play.gridWidth()) * boardViewModel4Play.gridWidth(),
                menuWidth,
                widthProperty()
        );

        DoubleBinding gridHeight = Bindings.createDoubleBinding(
                () -> Math.floor((heightProperty().get() - headerBox.heightProperty().get())
                        / boardViewModel4Play.gridHeight()) * boardViewModel4Play.gridHeight(),
                heightProperty(),
                headerBox.heightProperty());

        GridView4Play gridView4Play;
        gridView4Play = new GridView4Play(boardViewModel4Play.getGridViewModel4Play(),
                    gridWidth,
                    file);
        setCenter(gridView4Play);
    }
}
