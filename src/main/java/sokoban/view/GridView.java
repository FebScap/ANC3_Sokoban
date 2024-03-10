package sokoban.view;

import javafx.beans.binding.DoubleBinding;
import javafx.geometry.Insets;
import javafx.scene.layout.GridPane;
import sokoban.model.Board;
import sokoban.model.Cell.CellValue;
import sokoban.viewmodel.BoardViewModel;
import sokoban.viewmodel.GridViewModel;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

class GridView extends GridPane {
    private static final int PADDING = 20;

    GridView(GridViewModel gridViewModel, DoubleBinding gridWidth, int line, int col) {
        // Pour visualiser les limites de la grille
        // setStyle("-fx-background-color: lightgrey");
        setPadding(new Insets(PADDING));

        DoubleBinding cellWidth = gridWidth
                .subtract(PADDING * 2)
                .divide(col);

        // Remplissage de la grille
        for (int i = 0; i < line; ++i) {
            for (int j = 0; j < col; ++j) {
                CellView cellView = new CellView(gridViewModel.getCellViewModel(i, j), cellWidth, CellValue.GROUND);
                add(cellView, j, i); // lignes/colonnes inversées dans gridpane
            }
        }
    }
    GridView(GridViewModel gridViewModel, DoubleBinding gridWidth, File file) {
        try {
            List<String> lines = Files.readAllLines(file.toPath());
            int line = lines.size();
            int col = lines.get(0).length();
            setPadding(new Insets(PADDING));

            DoubleBinding cellWidth = gridWidth
                    .subtract(PADDING * 2)
                    .divide(col);

            // Remplissage de la grille
            for (int i = 0; i < line; ++i) {
                for (int j = 0; j < col; ++j) {
                    CellView cellView = null;
                    System.out.println(lines.get(i).split("")[j]);
                    if (lines.get(i).split("")[j].equals(" "))
                        cellView = new CellView(
                                gridViewModel.getCellViewModel(i, j),
                                cellWidth, CellValue.GROUND);
                    else if (lines.get(i).split("")[j].equals("#"))
                        cellView = new CellView(
                                gridViewModel.getCellViewModel(i, j),
                                cellWidth, CellValue.WALL);
                    else if (lines.get(i).split("")[j].equals("."))
                        cellView = new CellView(
                                gridViewModel.getCellViewModel(i, j),
                                cellWidth, CellValue.TARGET);
                    else if (lines.get(i).split("")[j].equals("$"))
                        cellView = new CellView(
                                gridViewModel.getCellViewModel(i, j),
                                cellWidth, CellValue.BOX);
                    else if (lines.get(i).split("")[j].equals("*"))
                        cellView = new CellView(
                                gridViewModel.getCellViewModel(i, j),
                                cellWidth, CellValue.BOX_TARGET);
                    else if (lines.get(i).split("")[j].equals("@"))
                        cellView = new CellView(
                                gridViewModel.getCellViewModel(i, j),
                                cellWidth, CellValue.PLAYER);
                    else if (lines.get(i).split("")[j].equals("+"))
                        cellView = new CellView(
                                gridViewModel.getCellViewModel(i, j),
                                cellWidth, CellValue.PLAYER_TARGET);
                    add(cellView, j, i); // lignes/colonnes inversées dans gridpane
                }
            }
        } catch (IOException e) {
            throw new RuntimeException("Erreur lors de la lecture du fichier");
        }
    }
}
