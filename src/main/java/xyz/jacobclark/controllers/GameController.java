package xyz.jacobclark.controllers;

import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.*;
import xyz.jacobclark.exceptions.NotPlayersTurnException;
import xyz.jacobclark.exceptions.PlayerNotFoundException;
import xyz.jacobclark.exceptions.PositionOccupiedException;
import xyz.jacobclark.exceptions.PositionOutOfBoundsException;
import xyz.jacobclark.games.Game;
import xyz.jacobclark.games.impl.Gomoku;
import xyz.jacobclark.models.Move;
import xyz.jacobclark.models.Piece;
import xyz.jacobclark.models.Player;

import java.util.*;
import java.util.function.Predicate;

@RestController
@CrossOrigin(origins = "*")
public class GameController {
    Map<UUID, Game> games = new HashMap<>();

    @MessageMapping("/games/{uuid}/pieces")
    @SendTo("/topic/games/pieces")
    public List<Piece> placePiece(@DestinationVariable UUID uuid, Move move) throws PositionOutOfBoundsException, PositionOccupiedException, NotPlayersTurnException, PlayerNotFoundException {
        games.get(uuid).getBoard().placePiece(new Piece(getPlayerToMove(uuid, move).getPebbleType(), move.getColumn(), move.getRow()));
        return games.get(uuid).getBoard().getPieces();
    }

    private Player getPlayerToMove(@DestinationVariable UUID uuid, Move move) throws PlayerNotFoundException {
        Optional<Player> playerToMove = games
                .get(uuid)
                .getPlayers()
                .stream()
                .filter(getPlayerByUuid(move.getPlayerUuid()))
                .findAny();

        if (!playerToMove.isPresent()) throw new PlayerNotFoundException();

        return playerToMove.get();
    }

    private Predicate<Player> getPlayerByUuid(UUID playersUuid) {
        return player -> player.getUuid().equals(playersUuid);
    }

    @PostMapping("/games")
    public Game createGame() {
        Game game = new Gomoku();
        games.put(game.getUuid(), game);
        return game;
    }

    @GetMapping("/games/{uuid}")
    public Game getGame(@PathVariable UUID uuid) {
        return games.get(uuid);
    }
}