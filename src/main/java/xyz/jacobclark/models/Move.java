package xyz.jacobclark.models;

public class Move {
    private final Player player;
    private final int column;
    private final int row;

    public Move(Player player, int column, int row) {
        this.player = player;
        this.column = column;
        this.row = row;
    }

    public Player getPlayer() {
        return player;
    }

    public int getColumn() {
        return column;
    }

    public int getRow() {
        return row;
    }
}
