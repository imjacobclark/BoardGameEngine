package xyz.jacobclark.unit.controllers;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;
import xyz.jacobclark.controllers.GameController;
import xyz.jacobclark.exceptions.NotPlayersTurnException;
import xyz.jacobclark.exceptions.PlayerNotFoundException;
import xyz.jacobclark.exceptions.PositionOccupiedException;
import xyz.jacobclark.games.Game;
import xyz.jacobclark.models.Move;
import xyz.jacobclark.models.PebbleType;
import xyz.jacobclark.models.Piece;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.any;
import static org.hamcrest.core.Is.is;

@RunWith(MockitoJUnitRunner.class)
public class GameControllerTest {

    @Test
    public void placePieceAddsPiece_AndReturnsCurrentBoardState() throws Exception, PositionOccupiedException, NotPlayersTurnException {
        GameController gameController = new GameController();
        Game game = gameController.createGame();

        UUID firstPlayersUUID = game.getPlayers().get(0).getUuid();

        List<Piece> pieces = gameController.placePiece(game.getUuid(), new Move(firstPlayersUUID, 0, 0));

        List<Piece> expectedPieces = new ArrayList<>();
        expectedPieces.add(new Piece(PebbleType.BLACK, 0, 0));

        assertThat(pieces, is(expectedPieces));
    }

    @Test(expected = PlayerNotFoundException.class)
    public void placePieceThrowsException_WhenPlayerIsNotInTheGame() throws Exception, PositionOccupiedException, NotPlayersTurnException {
        GameController gameController = new GameController();
        Game game = gameController.createGame();

        UUID uuidThatDoesNotExists = UUID.randomUUID();

        List<Piece> pieces = gameController.placePiece(game.getUuid(), new Move(uuidThatDoesNotExists, 0, 0));

        List<Piece> expectedPieces = new ArrayList<>();
        expectedPieces.add(new Piece(PebbleType.BLACK, 0, 0));

        assertThat(pieces, is(expectedPieces));
    }

    @Test
    public void createGame_ShouldReturnNewlyCreatedGame() throws Exception {
        GameController gameController = new GameController();

        Game expectedGame = gameController.createGame();

        assertThat(expectedGame, is(any(Game.class)));
    }

    @Test
    public void getGame_ShouldReturnExpectedGame() throws Exception {
        GameController gameController = new GameController();

        Game newGame = gameController.createGame();
        Game expectedGame = gameController.getGame(newGame.getUuid());

        assertThat(expectedGame.getUuid(), is(newGame.getUuid()));
    }
}
