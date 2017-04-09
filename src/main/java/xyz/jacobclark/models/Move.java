package xyz.jacobclark.models;

public class Move {
    private Player player = null;
    private int column = -1;
    private int row = -1;

    public Move() {
    }

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
