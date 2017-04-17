package xyz.jacobclark.unit.controllers;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;
import xyz.jacobclark.controllers.GameController;
import xyz.jacobclark.exceptions.*;
import xyz.jacobclark.games.Game;
import xyz.jacobclark.models.Move;
import xyz.jacobclark.models.PebbleType;
import xyz.jacobclark.models.Piece;
import xyz.jacobclark.models.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.any;
import static org.hamcrest.Matchers.samePropertyValuesAs;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertTrue;

@RunWith(MockitoJUnitRunner.class)
public class GameControllerTest {

    @Test
    public void placePieceAddsPiece_AndReturnsCurrentBoardState() throws Exception, PositionOccupiedException, NotPlayersTurnException {
        GameController gameController = new GameController();
        Game game = gameController.createGame();

        UUID firstPlayersUUID = game.getPlayers().get(0).getUuid();

        List<Piece> pieces = gameController.placePiece(game.getUuid(), new Move(firstPlayersUUID, 0, 0)).getBoard().getPieces();

        List<Piece> expectedPieces = new ArrayList<>();
        expectedPieces.add(new Piece(PebbleType.BLACK, 0, 0));

        assertThat(pieces, samePropertyValuesAs(expectedPieces));
    }

    @Test(expected = PlayerNotFoundException.class)
    public void placePieceThrowsException_WhenPlayerIsNotInTheGame() throws Exception, PositionOccupiedException, NotPlayersTurnException {
        GameController gameController = new GameController();
        Game game = gameController.createGame();

        UUID uuidThatDoesNotExists = UUID.randomUUID();

        List<Piece> pieces = gameController.placePiece(game.getUuid(), new Move(uuidThatDoesNotExists, 0, 0)).getBoard().getPieces();

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

    @Test
    public void joinGame_ShouldAddANewPlayerToTheGame_AndReturnNewlyCreatedPlayer() throws Exception {
        GameController gameController = new GameController();
        Game game = gameController.createGame();

        Player player = gameController.joinGame(game.getUuid());

        assertThat(player.getPebbleType(), is(PebbleType.WHITE));
        assertTrue(player.getUuid().toString().contains("-"));

        assertThat(game.getPlayers().size(), is(2));
        assertThat(game.getPlayers().get(0).getPebbleType(), is(PebbleType.BLACK));
        assertThat(game.getPlayers().get(1).getPebbleType(), is(PebbleType.WHITE));
    }

    @Test(expected = FullGameException.class)
    public void joinGame_ShouldThrowException_WhenMoreThanTwoPlayersAreInASingleGame() throws Exception {
        GameController gameController = new GameController();
        Game game = gameController.createGame();

        gameController.joinGame(game.getUuid());
        gameController.joinGame(game.getUuid());
    }

    @Test
    public void gameWonExceptionHandler_ReturnsExpectedGame() throws Exception {
        GameController gameController = new GameController();
        Game game = gameController.createGame();

        game.getBoard().placePiece(new Piece(PebbleType.BLACK, 0, 0));

        assertThat(gameController.handleGameWonException(game.getUuid(), new GameWonException()), samePropertyValuesAs(game));
    }
}
