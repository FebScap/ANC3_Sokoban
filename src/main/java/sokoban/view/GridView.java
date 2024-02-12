package sokoban.view;

import javafx.beans.binding.DoubleBinding;
import javafx.geometry.Insets;
import javafx.scene.layout.GridPane;
import sokoban.viewmodel.BoardViewModel;
import sokoban.viewmodel.GridViewModel;

class GridView extends GridPane {
    private static final int PADDING = 20;
    private final int GRID_WIDTH = 10;
    private final int GRID_HEIGHT = 15;

    GridView(GridViewModel gridViewModel, DoubleBinding gridWidth) {
        // Pour visualiser les limites de la grille
        // setStyle("-fx-background-color: lightgrey");

        setGridLinesVisible(true);
        setPadding(new Insets(PADDING));

        DoubleBinding cellWidth = gridWidth
                .subtract(PADDING * 2)
                .divide(GRID_HEIGHT);

        // Remplissage de la grille
        for (int i = 0; i < GRID_WIDTH; ++i) {
            for (int j = 0; j < GRID_HEIGHT; ++j) {
                CellView cellView = new CellView(gridViewModel.getCellViewModel(i, j), cellWidth);
                add(cellView, j, i); // lignes/colonnes inversées dans gridpane
            }
        }
    }
}
