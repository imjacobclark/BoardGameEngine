package xyz.jacobclark;

import org.junit.Test;
import xyz.jacobclark.exceptions.PositionOccupiedException;
import xyz.jacobclark.models.Piece;
import xyz.jacobclark.rules.Rules;
import xyz.jacobclark.rules.impl.GomokuRules;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static xyz.jacobclark.models.Player.BLACK;
import static xyz.jacobclark.models.Player.WHITE;

public class GomokuBoardTest {
    Rules gomokuRules = new GomokuRules();

    @Test
    public void canPlaceABlackStoneAtGivenPositionOnBoard() throws PositionOccupiedException {
        Board board = new Board(gomokuRules);
        Piece expectedPiece = new Piece(BLACK, 0, 0);

        assertThat(board.placePiece(BLACK, 0, 0), is(expectedPiece));
    }

    @Test
    public void canPlaceAWhiteStoneAtGivenPositionOnBoard() throws PositionOccupiedException {
        Board board = new Board(gomokuRules);
        Piece expectedPiece = new Piece(WHITE, 0, 0);

        assertThat(board.placePiece(WHITE, 0, 0), is(expectedPiece));
    }

    @Test
    public void canPlaceManyStoneAtGivenPositionOnBoard_WhenPositionsAreUnOccupied() throws PositionOccupiedException {
        Board board = new Board(gomokuRules);

        assertThat(board.placePiece(BLACK, 0, 0), is(new Piece(BLACK, 0, 0)));
        assertThat(board.placePiece(WHITE, 0, 1), is(new Piece(WHITE, 0, 1)));
        assertThat(board.placePiece(BLACK, 1, 0), is(new Piece(BLACK, 1, 0)));
        assertThat(board.placePiece(WHITE, 1, 1), is(new Piece(WHITE, 1, 1)));
    }

    @Test(expected = PositionOccupiedException.class)
    public void canNotPlaceAStoneAtGivenPositionOnBoard_WhenAStoneIsAlreadyPlacedThere() throws PositionOccupiedException {
        Board board = new Board(gomokuRules);

        board.placePiece(BLACK, 0, 0);
        board.placePiece(WHITE, 0, 0);
    }
}
