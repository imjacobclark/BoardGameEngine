package xyz.jacobclark.unit.models;

import org.junit.Test;
import xyz.jacobclark.exceptions.GameWonException;
import xyz.jacobclark.models.Board;
import xyz.jacobclark.exceptions.NotPlayersTurnException;
import xyz.jacobclark.exceptions.PositionOccupiedException;
import xyz.jacobclark.exceptions.PositionOutOfBoundsException;
import xyz.jacobclark.models.PebbleType;
import xyz.jacobclark.models.Piece;
import xyz.jacobclark.rules.Rules;
import xyz.jacobclark.rules.impl.GomokuRules;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.beans.SamePropertyValuesAs.samePropertyValuesAs;
import static org.hamcrest.core.Is.is;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static xyz.jacobclark.models.PebbleType.BLACK;
import static xyz.jacobclark.models.PebbleType.WHITE;

public class BoardTest {
    Rules gomokuRules = new GomokuRules();

    @Test
    public void canPlaceABlackStoneAtGivenPositionOnBoard() throws PositionOccupiedException, PositionOutOfBoundsException, NotPlayersTurnException, GameWonException {
        Board board = new Board(gomokuRules);
        Piece expectedPiece = new Piece(BLACK, 0, 0);

        assertThat(board.placePiece(new Piece(BLACK, 0, 0)), samePropertyValuesAs(expectedPiece));
    }

    @Test
    public void canPlaceAWhiteStoneAtGivenPositionOnBoard_WhenMoveIsValid() throws PositionOccupiedException, PositionOutOfBoundsException, NotPlayersTurnException, GameWonException {
        Board board = new Board(gomokuRules);
        board.placePiece(new Piece(BLACK, 0, 0));

        Piece expectedPiece = new Piece(WHITE, 0, 1);

        assertThat(board.placePiece(new Piece(WHITE, 0, 1)), samePropertyValuesAs(expectedPiece));
    }

    @Test
    public void canPlaceManyStonesAtGivenPositionOnBoard() throws PositionOccupiedException, PositionOutOfBoundsException, NotPlayersTurnException, GameWonException {
        Board board = new Board(gomokuRules);

        assertThat(board.placePiece(new Piece(BLACK, 0, 0)), samePropertyValuesAs(new Piece(BLACK, 0, 0)));
        assertThat(board.placePiece(new Piece(WHITE, 0, 1)), samePropertyValuesAs(new Piece(WHITE, 0, 1)));
        assertThat(board.placePiece(new Piece(BLACK, 1, 0)), samePropertyValuesAs(new Piece(BLACK, 1, 0)));
        assertThat(board.placePiece(new Piece(WHITE, 1, 1)), samePropertyValuesAs(new Piece(WHITE, 1, 1)));
    }

    @Test
    public void moveIsValidated_WhenStoneIsPlaced() throws PositionOutOfBoundsException, PositionOccupiedException, NotPlayersTurnException, GameWonException {
        GomokuRules mockGomokuRules = mock(GomokuRules.class);
        Board board = new Board(mockGomokuRules);

        board.placePiece(new Piece(BLACK, 0, 0));

        verify(mockGomokuRules).validateThatMoveIsLegal(any(), any());
    }

    @Test
    public void canRetrievePlacedStonesOnBoard() throws Exception, PositionOccupiedException, NotPlayersTurnException {
        Board board = new Board(gomokuRules);

        List<Piece> expectedPieces = new ArrayList<>();

        Piece pieceOne = new Piece(BLACK, 0, 0);
        Piece pieceTwo = new Piece(WHITE, 0, 1);

        expectedPieces.add(pieceOne);
        expectedPieces.add(pieceTwo);

        board.placePiece(new Piece(BLACK, 0, 0));
        board.placePiece(new Piece(WHITE, 0, 1));

        assertThat(board.getPieces(), samePropertyValuesAs(expectedPieces));
    }

    @Test(expected = GameWonException.class)
    public void boardNotifiesOfWin() throws PositionOccupiedException, PositionOutOfBoundsException, NotPlayersTurnException, GameWonException {
        Board board = new Board(gomokuRules);

        board.placePiece(new Piece(PebbleType.BLACK, 8, 8));
        board.placePiece(new Piece(PebbleType.WHITE, 1, 8));
        board.placePiece(new Piece(PebbleType.BLACK, 9, 8));
        board.placePiece(new Piece(PebbleType.WHITE, 2, 8));
        board.placePiece(new Piece(PebbleType.BLACK, 10, 8));
        board.placePiece(new Piece(PebbleType.WHITE, 3, 8));
        board.placePiece(new Piece(PebbleType.BLACK, 11, 8));
        board.placePiece(new Piece(PebbleType.WHITE, 4, 8));
        board.placePiece(new Piece(PebbleType.BLACK, 12, 8));
        board.placePiece(new Piece(PebbleType.WHITE, 5, 9));
    }
}
