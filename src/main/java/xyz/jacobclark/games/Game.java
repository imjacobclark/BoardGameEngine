package xyz.jacobclark.games;

import xyz.jacobclark.models.Board;
import xyz.jacobclark.exceptions.FullGameException;
import xyz.jacobclark.models.GameTitle;
import xyz.jacobclark.models.Player;

import java.util.ArrayList;
import java.util.UUID;

public interface Game {
    UUID getUuid();

    ArrayList<Player> getPlayers();

    Board getBoard();

    Player addPlayer() throws FullGameException;

    GameTitle getTitle();
}
