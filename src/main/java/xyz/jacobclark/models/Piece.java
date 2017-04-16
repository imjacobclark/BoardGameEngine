package xyz.jacobclark.models;

public class Piece {
    private PebbleType pebbleType;
    private Integer column;
    private Integer row;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Piece piece = (Piece) o;

        if (pebbleType != piece.pebbleType) return false;
        if (column != null ? !column.equals(piece.column) : piece.column != null) return false;
        return row != null ? row.equals(piece.row) : piece.row == null;
    }

    @Override
    public int hashCode() {
        int result = pebbleType != null ? pebbleType.hashCode() : 0;
        result = 31 * result + (column != null ? column.hashCode() : 0);
        result = 31 * result + (row != null ? row.hashCode() : 0);
        return result;
    }
}
