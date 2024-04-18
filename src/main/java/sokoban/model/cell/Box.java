package sokoban.model.cell;

public class Box extends GameObject {

    private static int boxCount = 1;
    private final int boxNumber;

    public Box() {
        super();
        this.boxNumber = boxCount;
        Box.boxCount++;
    }

    public String getBoxNumber() {
        return String.valueOf(boxNumber);
    }
    public static void resetBoxNumberWhenStart() {
        Box.boxCount=1;
    }
}
