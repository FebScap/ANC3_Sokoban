package sokoban.viewmodel.design;

import sokoban.model.design.Board4Design;
import sokoban.model.api.cell.CellValue;

public class MenuViewModel {
    private final Board4Design board4Design;
    private static CellValue actualValue;

    MenuViewModel(Board4Design board4Design) {
        this.board4Design = board4Design;
    }
    public ToolViewModel getToolViewModel(int line) {
        return new ToolViewModel(line, this);
    }

    public void select(CellValue value) {
        actualValue = value;
    }
}
