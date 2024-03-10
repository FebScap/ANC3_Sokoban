package sokoban.view;

import javafx.beans.binding.DoubleBinding;
import javafx.geometry.Insets;
import javafx.scene.layout.GridPane;
import sokoban.viewmodel.BoardViewModel;
import sokoban.viewmodel.GridViewModel;

class GridView extends GridPane {
    private static final int PADDING = 20;
    private final int DEFAULT_GRID_LINE = 10;
    private final int DEFAULT_GRID_COL = 15;

    GridView(GridViewModel gridViewModel, DoubleBinding gridWidth, int line, int col) {
        // Pour visualiser les limites de la grille
        // setStyle("-fx-background-color: lightgrey");
        setPadding(new Insets(PADDING));

        DoubleBinding cellWidth = gridWidth
                .subtract(PADDING * 2)
                .divide(DEFAULT_GRID_COL);

        // Remplissage de la grille
        for (int i = 0; i < DEFAULT_GRID_LINE; ++i) {
            for (int j = 0; j < DEFAULT_GRID_COL; ++j) {
                CellView cellView = new CellView(gridViewModel.getCellViewModel(i, j), cellWidth);
                add(cellView, j, i); // lignes/colonnes inversées dans gridpane
            }
        }
    }
}
