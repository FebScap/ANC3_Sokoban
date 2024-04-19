package sokoban.model;

import sokoban.model.cell.Box;

public class Box4Play extends Box {

    private final int number;

    public Box4Play(int number) {
        super();
        this.number = number;
    }

    public int getNumber() {
        return number;
    }
}
