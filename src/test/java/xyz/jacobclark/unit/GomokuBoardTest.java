package xyz.jacobclark.unit;

import org.junit.Test;
import xyz.jacobclark.Board;
import xyz.jacobclark.exceptions.NotPlayersTurnException;
import xyz.jacobclark.exceptions.PositionOccupiedException;
import xyz.jacobclark.exceptions.PositionOutOfBoundsException;
import xyz.jacobclark.models.Piece;
import xyz.jacobclark.rules.Rules;
import xyz.jacobclark.rules.impl.GomokuRules;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static xyz.jacobclark.models.PebbleType.BLACK;
import static xyz.jacobclark.models.PebbleType.WHITE;

public class GomokuBoardTest {
    Rules gomokuRules = new GomokuRules();

    @Test
    public void canPlaceABlackStoneAtGivenPositionOnBoard() throws PositionOccupiedException, PositionOutOfBoundsException, NotPlayersTurnException {
        Board board = new Board(gomokuRules);
        Piece expectedPiece = new Piece(BLACK, 0, 0);

        assertThat(board.place(BLACK, 0, 0), is(expectedPiece));
    }

    @Test
    public void canPlaceAWhiteStoneAtGivenPositionOnBoard() throws PositionOccupiedException, PositionOutOfBoundsException, NotPlayersTurnException {
        Board board = new Board(gomokuRules);
        Piece expectedPiece = new Piece(WHITE, 0, 0);

        assertThat(board.place(WHITE, 0, 0), is(expectedPiece));
    }

    @Test
    public void canPlaceManyStonesAtGivenPositionOnBoard() throws PositionOccupiedException, PositionOutOfBoundsException, NotPlayersTurnException {
        Board board = new Board(gomokuRules);

        assertThat(board.place(BLACK, 0, 0), is(new Piece(BLACK, 0, 0)));
        assertThat(board.place(WHITE, 0, 1), is(new Piece(WHITE, 0, 1)));
        assertThat(board.place(BLACK, 1, 0), is(new Piece(BLACK, 1, 0)));
        assertThat(board.place(WHITE, 1, 1), is(new Piece(WHITE, 1, 1)));
    }

    @Test
    public void moveIsValidated_WhenStoneIsPlaced() throws PositionOutOfBoundsException, PositionOccupiedException, NotPlayersTurnException {
        GomokuRules mockGomokuRules = mock(GomokuRules.class);
        Board board = new Board(mockGomokuRules);

        board.place(BLACK, 0, 0);

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

        board.place(BLACK, 0, 0);
        board.place(WHITE, 0, 1);

        assertThat(board.getAll(), is(expectedPieces));
    }
}
