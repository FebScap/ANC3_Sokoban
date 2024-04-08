package sokoban.view;

import javafx.beans.binding.DoubleBinding;
import javafx.scene.layout.GridPane;
import sokoban.model.Cell.CellValue;
import sokoban.viewmodel.MenuViewModel;

public class MenuView extends GridPane {
    private static final int PADDING = 20;

    public static CellValue cellValue = CellValue.GROUND;

    final CellValue[] VALUES = new CellValue[]{
            CellValue.GROUND,
            CellValue.TARGET,
            CellValue.WALL,
            CellValue.PLAYER,
            CellValue.BOX
    };

    MenuView(MenuViewModel menuViewModel, DoubleBinding menuWidth) {
        // Pour visualiser les limites de la grille
        // setStyle("-fx-background-color: lightgrey");
        setHgap(PADDING);

        // Remplissage de la grille
        for (int i = 0; i < VALUES.length; i++) {
            ToolView toolView = new ToolView(menuViewModel.getToolViewModel(i), menuWidth, VALUES[i]);
            add(toolView, 1, i); // lignes/colonnes inversées dans gridpane
        }
    }

    public static void setCellValue(CellValue cellValue) {
        MenuView.cellValue = cellValue;
    }
}
