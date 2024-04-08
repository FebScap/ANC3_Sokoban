package sokoban.view;

import javafx.beans.binding.Bindings;
import javafx.beans.binding.DoubleBinding;
import javafx.geometry.Insets;
import javafx.scene.layout.GridPane;
import sokoban.model.Cell.*;
import sokoban.viewmodel.GridViewModel;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

class GridView extends GridPane {
    private static final int PADDING = 20;

    GridView(GridViewModel gridViewModel, DoubleBinding gridWidth, DoubleBinding gridHeight, int line, int col) {
        // Pour visualiser les limites de la grille
        // setStyle("-fx-background-color: lightgrey");
        setPadding(new Insets(PADDING));

        DoubleBinding cellWidth = Bindings.createDoubleBinding(
                ()-> Math.floor(Math.min((gridHeight.doubleValue() - PADDING*2),
                        (gridWidth.doubleValue() - PADDING * 2) / gridWidth.doubleValue())
                ),
                gridHeight,
                gridWidth);

        // Remplissage de la grille
        for (int i = 0; i < line; ++i) {
            for (int j = 0; j < col; ++j) {
                CellView cellView = new CellView(gridViewModel.getCellViewModel(i, j), cellWidth, Collections.singletonList(new Ground()));
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
                    List<GameObject> objects = new ArrayList<>();
                    switch (lines.get(i).split("")[j]) {
                        case " ":
                            objects.add(new Ground());
                        case "#":
                            objects.add(new Wall());
                        case ".":
                            objects.add(new Target());
                        case "$":
                            objects.add(new Box());
                        case "*":
                            objects.add(new Box());
                            objects.add(new Target());
                        case "@":
                            objects.add(new Player());
                        case "+":
                            objects.add(new Player());
                            objects.add(new Target());
                    }
                    CellView cellView = new CellView(
                            gridViewModel.getCellViewModel(i, j),
                            cellWidth, objects);
                    add(cellView, j, i); // lignes/colonnes inversées dans gridpane
                }
            }
        } catch (IOException e) {
            throw new RuntimeException("Erreur lors de la lecture du fichier");
        }
    }
}
