package sokoban.view;

import javafx.beans.binding.DoubleBinding;
import javafx.geometry.Insets;
import javafx.scene.layout.GridPane;
import sokoban.model.Cell.CellValue;
import sokoban.viewmodel.CellViewModel;
import sokoban.viewmodel.MenuViewModel;

import java.util.ArrayList;
import java.util.List;

public class MenuView extends GridPane {
    private static final int PADDING = 20;

    private final CellValue[] VALUES = new CellValue[]{
            CellValue.GROUND,
            CellValue.TARGET,
            CellValue.WALL,
            CellValue.PLAYER,
            CellValue.BOX
    };

    MenuView(MenuViewModel menuViewModel, DoubleBinding menuWidth) {
        // Pour visualiser les limites de la grille
        // setStyle("-fx-background-color: lightgrey");
        setPadding(new Insets(PADDING));

        DoubleBinding cellWidth = menuWidth
                .subtract(PADDING * 2);

        // Remplissage de la grille
        for (int i = 0; i < VALUES.length; i++) {
            ToolView toolView = new ToolView(menuViewModel.getToolViewModel(i), cellWidth, VALUES[i]);
            add(toolView, 1, i); // lignes/colonnes inversées dans gridpane
        }
    }
}
