package sokoban.model.Cell;

public class Cell {
    private final ElementStack stack;
    private final Wall wall = new Wall();
    private boolean isWall;
    public Cell() {
        this.isWall = false;
        this.stack = new ElementStack(CellValue.GROUND);
    }

    public void setType(CellValue value){
        if (value.equals(CellValue.WALL)) {
            this.isWall = true;
        } else {
            stack.setElement(value);
        }
    }

    public CellValue getType() {
        if (isWall) {
            return CellValue.WALL;
        } else {
            return stack.getValue();
        }
    }

    public Object getElement() {
        if (isWall) {
            return wall;
        } else {
            return stack;
        }
    }
}
