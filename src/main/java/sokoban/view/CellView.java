package sokoban.view;

import javafx.beans.binding.DoubleBinding;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import sokoban.model.cell.CellValue;

import java.util.HashMap;

public abstract class CellView extends StackPane {
    protected static final HashMap<CellValue, Image> images = new HashMap<>(){{
        put(CellValue.BOX, new Image("box.png"));
        put(CellValue.TARGET, new Image("goal.png"));
        put(CellValue.GROUND, new Image("ground.png"));
        put(CellValue.PLAYER, new Image("player.png"));
        put(CellValue.WALL, new Image("wall.png"));
    }};
    protected DoubleBinding widthProperty;
    protected final ImageView imageView = new ImageView();
    protected final ImageView imageViewMid = new ImageView();
    protected final ImageView imageViewTop = new ImageView();
    protected final Label numberText = new Label();
}
