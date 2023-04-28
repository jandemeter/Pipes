package sk.stuba.fei.uim.oop.board;

public enum DirectionOfPipe {

    TOP,
    RIGHT,
    BOTTOM,
    LEFT;

    public DirectionOfPipe rotate() {
        switch (this) {
            case TOP:
                return RIGHT;
            case RIGHT:
                return BOTTOM;
            case BOTTOM:
                return LEFT;
            case LEFT:
                return TOP;
        }
        return null;
    }
}
