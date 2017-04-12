package xyz.jacobclark.models;

import java.util.UUID;


public class Player {
    private UUID uuid = null;
    private PebbleType pebbleType = null;

    public Player() {
    }

    public Player(UUID uuid, PebbleType pebbleType) {
        this.uuid = uuid;
        this.pebbleType = pebbleType;
    }

    public UUID getUuid() {
        return uuid;
    }

    public PebbleType getPebbleType() {
        return pebbleType;
    }
}
