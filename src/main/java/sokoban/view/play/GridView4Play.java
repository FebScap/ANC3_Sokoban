package sokoban.view.play;

import javafx.beans.binding.DoubleBinding;
import javafx.geometry.Insets;
import sokoban.model.api.cell.*;
import sokoban.view.api.GridView;
import sokoban.viewmodel.play.GridViewModel4Play;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

public class GridView4Play extends GridView {
    private static final int PADDING = 20;
    GridView4Play(GridViewModel4Play gridViewModel, DoubleBinding gridWidth, File file) {
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
                    CellView4Play cellView4Play = new CellView4Play(
                            gridViewModel.getCellViewModel(i, j),
                            cellWidth, objects);
                    add(cellView4Play, j, i); // lignes/colonnes inversées dans gridpane
                }
            }
        } catch (IOException e) {
            throw new RuntimeException("Erreur lors de la lecture du fichier");
        }
    }
}
