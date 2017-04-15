package xyz.jacobclark.games.impl;

import xyz.jacobclark.exceptions.FullGameException;
import xyz.jacobclark.games.Game;
import xyz.jacobclark.models.Board;
import xyz.jacobclark.models.GameTitle;
import xyz.jacobclark.models.PebbleType;
import xyz.jacobclark.models.Player;
import xyz.jacobclark.rules.impl.GomokuRules;

import java.util.ArrayList;
import java.util.UUID;

public class Gomoku implements Game {
    private ArrayList<Player> players = new ArrayList<>();
    private Board board;
    private UUID uuid;
    private GameTitle title;

    public Gomoku() {
        this.title = GameTitle.GOMOKU;
        this.uuid = UUID.randomUUID();
        this.board = new Board(new GomokuRules());
        this.players.add(new Player(UUID.randomUUID(), PebbleType.BLACK));
    }

    @Override
    public ArrayList<Player> getPlayers() {
        return players;
    }

    @Override
    public Board getBoard() {
        return board;
    }

    @Override
    public Player addPlayer() throws FullGameException {
        if (this.players.size() > 1) throw new FullGameException();

        Player player = new Player(UUID.randomUUID(), PebbleType.WHITE);

        this.players.add(player);

        return player;
    }

    @Override
    public UUID getUuid() {
        return uuid;
    }

    @Override
    public GameTitle getTitle() {
        return title;
    }
}
