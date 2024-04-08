package sokoban.view;

import javafx.beans.binding.DoubleBinding;
import javafx.geometry.Insets;
import javafx.scene.layout.GridPane;
import sokoban.model.Cell.*;
import sokoban.viewmodel.CellViewModel;
import sokoban.viewmodel.MenuViewModel;

import java.util.ArrayList;
import java.util.List;

public class MenuView extends GridPane {
    private static final int PADDING = 20;

    public static GameObject cellValue = new Wall();

    private final GameObject[] VALUES = new GameObject[]{
            new Ground(),
            new Target(),
            new Wall(),
            new Player(),
            new Box()
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

    public static void setCellValue(GameObject cellValue) {
        MenuView.cellValue = cellValue;
    }
}
