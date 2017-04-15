package xyz.jacobclark.controllers;

import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.*;
import xyz.jacobclark.exceptions.NotPlayersTurnException;
import xyz.jacobclark.exceptions.PositionOccupiedException;
import xyz.jacobclark.exceptions.PositionOutOfBoundsException;
import xyz.jacobclark.games.Game;
import xyz.jacobclark.games.impl.Gomoku;
import xyz.jacobclark.models.Piece;

import java.util.*;

@RestController
@CrossOrigin(origins = "*")
public class GameController {
    Map<UUID, Game> games = new HashMap<>();

    @MessageMapping("/games/{uuid}/pieces")
    @SendTo("/topic/games/pieces")
    public List<Piece> placePiece(@DestinationVariable UUID uuid, Piece move) throws PositionOutOfBoundsException, PositionOccupiedException, NotPlayersTurnException {
        games.get(uuid).getBoard().placePiece(move);
        return games.get(uuid).getBoard().getPieces();
    }

    @PostMapping("/games")
    public Game createGame() {
        Game game = new Gomoku();
        games.put(game.getId(), game);
        return game;
    }

    @GetMapping("/games/{uuid}")
    public Game getGame(@PathVariable UUID uuid) {
        return games.get(uuid);
    }
}