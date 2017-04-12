package xyz.jacobclark.unit.controllers;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;
import xyz.jacobclark.controllers.GameController;
import xyz.jacobclark.exceptions.NotPlayersTurnException;
import xyz.jacobclark.exceptions.PositionOccupiedException;
import xyz.jacobclark.games.Game;
import xyz.jacobclark.models.Piece;
import xyz.jacobclark.models.PebbleType;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.any;
import static org.hamcrest.core.Is.is;

@RunWith(MockitoJUnitRunner.class)
public class GameControllerTest {

    @Test
    public void placePieceAddsPiece_AndReturnsCurrentBoardState() throws Exception, PositionOccupiedException, NotPlayersTurnException {
        GameController gameController = new GameController();
        Game game = gameController.createGame();
        List<Piece> pieces = gameController.getPieces(game.getId(), new Piece(PebbleType.BLACK, 0, 0));

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
        Game expectedGame = gameController.getGame(newGame.getId());



        assertThat(expectedGame.getId(), is(newGame.getId()));
    }
}
