package xyz.jacobclark.models;

public class Piece {
    private PebbleType pebbleType;
    private Integer column;
    private Integer row;

    public Piece(){}

    public Piece(PebbleType pebbleType, int column, int row) {
        this.pebbleType = pebbleType;
        this.column = column;
        this.row = row;
    }

    public Integer getColumn() {
        return column;
    }

    public Integer getRow() {
        return row;
    }

    public PebbleType getPebbleType() {
        return pebbleType;
    }
}
