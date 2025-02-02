package sokoban.view;

import javafx.beans.binding.DoubleBinding;
import javafx.geometry.Insets;
import sokoban.model.cell.*;
import sokoban.viewmodel.GridViewModel4Play;

import java.util.ArrayList;
import java.util.List;

public class GridView4Play extends GridView {
    private static final int PADDING = 20;

    GridView4Play(GridViewModel4Play gridViewModel, DoubleBinding gridWidth, String s) {
        ArrayList<String> lines = new ArrayList<>(List.of(s.split("\n")));
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
                        break;
                    case "#":
                        objects.add(new Wall());
                        break;
                    case ".":
                        objects.add(new Target());
                        break;
                    case "$":
                        objects.add(new Box());
                        break;
                    case "*":
                        objects.add(new Box());
                        objects.add(new Target());
                        break;
                    case "@":
                        objects.add(new Player());
                        break;
                    case "+":
                        objects.add(new Player());
                        objects.add(new Target());
                        break;
                }
                CellView4Play cellView4Play = new CellView4Play(
                        gridViewModel.getCellViewModel(i, j),
                        cellWidth, objects);
                add(cellView4Play, j, i); // lignes/colonnes inversées dans gridpane
            }
        }
    }
}
