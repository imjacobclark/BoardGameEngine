package xyz.jacobclark.models;

import java.util.UUID;

public class Move {
    private UUID playerUuid;
    private int row;
    private int column;

    public Move() {
    }

    public Move(UUID playerUuid, int row, int column) {
        this.playerUuid = playerUuid;
        this.row = row;
        this.column = column;
    }

    public UUID getPlayerUuid() {
        return playerUuid;
    }

    public int getRow() {
        return row;
    }

    public int getColumn() {
        return column;
    }
}
