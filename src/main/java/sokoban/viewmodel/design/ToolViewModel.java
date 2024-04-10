package sokoban.viewmodel.design;

import sokoban.model.api.cell.CellValue;

public class ToolViewModel {
    private final int line;
    private final MenuViewModel menu;

    ToolViewModel(int line, MenuViewModel menuViewModel) {
        this.line = line;
        this.menu = menuViewModel;
    }

    public void select(CellValue value) {
        menu.select(value);
    }
}
