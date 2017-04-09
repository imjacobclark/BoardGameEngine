package xyz.jacobclark.models;

public class Piece {
    private Player player;
    private Integer column;
    private Integer row;

    public Piece(Player player, int column, int row) {
        this.player = player;
        this.column = column;
        this.row = row;
    }

    public Integer getColumn() {
        return column;
    }

    public Integer getRow() {
        return row;
    }

    public Player getPlayer() {
        return player;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Piece piece = (Piece) o;

        if (player != piece.player) return false;
        if (column != null ? !column.equals(piece.column) : piece.column != null) return false;
        return row != null ? row.equals(piece.row) : piece.row == null;
    }
}
