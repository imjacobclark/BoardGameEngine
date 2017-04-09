package xyz.jacobclark.rules;

import org.junit.Test;
import xyz.jacobclark.exceptions.PositionOccupiedException;
import xyz.jacobclark.exceptions.PositionOutOfBoundsException;
import xyz.jacobclark.models.Move;
import xyz.jacobclark.models.Piece;
import xyz.jacobclark.models.Player;
import xyz.jacobclark.rules.impl.GomokuRules;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

public class GomokuRulesTest {
    Rules gomokuRules = new GomokuRules();

    @Test
    public void canExecuteAValidMove() throws PositionOutOfBoundsException, PositionOccupiedException {
        boolean result = gomokuRules.validateThatMoveIsLegal(new ArrayList<>(), new Move(Player.BLACK, 15, 15));
        assertTrue(result);
    }

    @Test(expected = PositionOccupiedException.class)
    public void canNotPlaceAStoneAtGivenPositionOnBoard_WhenAStoneIsAlreadyPlacedThere() throws PositionOccupiedException, PositionOutOfBoundsException {
        List<Piece> pieces = new ArrayList<>();
        pieces.add(new Piece(Player.BLACK, 0, 0));

        gomokuRules.validateThatMoveIsLegal(pieces, new Move(Player.BLACK, 0, 0));
    }

    @Test(expected = PositionOutOfBoundsException.class)
    public void canNotPlaceAStoneAtGivenPositionOnBoard_WhenItIsInAnNonExistentUpperBoundColumn() throws PositionOutOfBoundsException, PositionOccupiedException {
        gomokuRules.validateThatMoveIsLegal(new ArrayList<>(), new Move(Player.BLACK, 16, 15));
    }

    @Test(expected = PositionOutOfBoundsException.class)
    public void canNotPlaceAStoneAtGivenPositionOnBoard_WhenItIsInAnNonExistentUpperBoundRow() throws PositionOutOfBoundsException, PositionOccupiedException {
        gomokuRules.validateThatMoveIsLegal(new ArrayList<>(), new Move(Player.BLACK, 15, 16));
    }

    @Test(expected = PositionOutOfBoundsException.class)
    public void canNotPlaceAStoneAtGivenPositionOnBoard_WhenItIsInAnNonExistentLowerBoundColumn() throws PositionOutOfBoundsException, PositionOccupiedException {
        gomokuRules.validateThatMoveIsLegal(new ArrayList<>(), new Move(Player.BLACK, -16, 15));

    }

    @Test(expected = PositionOutOfBoundsException.class)
    public void canNotPlaceAStoneAtGivenPositionOnBoard_WhenItIsInAnNonExistentLowerBoundRow() throws PositionOutOfBoundsException, PositionOccupiedException {
        gomokuRules.validateThatMoveIsLegal(new ArrayList<>(), new Move(Player.BLACK, 15, -16));
    }

    @Test
    public void gameIsNotWon_WhenBlackHas4PiecesHorizontallyWithoutGap() throws Exception {
        List<Piece> pieces = new ArrayList<>();
        pieces.add(new Piece(Player.BLACK, 1, 0));
        pieces.add(new Piece(Player.BLACK, 2, 0));
        pieces.add(new Piece(Player.BLACK, 3, 0));
        pieces.add(new Piece(Player.BLACK, 4, 0));

        assertThat(gomokuRules.validateThatGameIsWin(pieces), is(false));
    }

    @Test
    public void gameIsNotWon_WhenBlackHas6PiecesInterruptedByOneWhitePiece() throws Exception {
        List<Piece> pieces = new ArrayList<>();
        pieces.add(new Piece(Player.BLACK, 1, 0));
        pieces.add(new Piece(Player.BLACK, 2, 0));
        pieces.add(new Piece(Player.BLACK, 3, 0));
        pieces.add(new Piece(Player.BLACK, 4, 0));
        pieces.add(new Piece(Player.WHITE, 5, 0));
        pieces.add(new Piece(Player.BLACK, 6, 0));
        pieces.add(new Piece(Player.BLACK, 7, 0));

        assertThat(gomokuRules.validateThatGameIsWin(pieces), is(false));
    }

    @Test
    public void gameIsNotWon_WhenNoPiecesArePlaced() throws Exception {
        List<Piece> pieces = new ArrayList<>();
        assertThat(gomokuRules.validateThatGameIsWin(pieces), is(false));
    }

    @Test
    public void gameIsNotWon_WhenBlackHas1PiecesHorizontallyWithoutGap() throws Exception {
        List<Piece> pieces = new ArrayList<>();
        pieces.add(new Piece(Player.BLACK, 0, 0));

        assertThat(gomokuRules.validateThatGameIsWin(pieces), is(false));
    }

    @Test
    public void gameIsNotWon_WhenBoardHasManyPieces_AndBlackHas6PiecesHorizontallyWithGapInMiddle() throws Exception {
        List<Piece> pieces = new ArrayList<>();
        pieces.add(new Piece(Player.BLACK, 1, 1));
        pieces.add(new Piece(Player.BLACK, 2, 1));
        pieces.add(new Piece(Player.BLACK, 3, 1));
        pieces.add(new Piece(Player.BLACK, 5, 1));
        pieces.add(new Piece(Player.BLACK, 6, 1));
        pieces.add(new Piece(Player.BLACK, 7, 1));


        assertThat(gomokuRules.validateThatGameIsWin(pieces), is(false));
    }

    @Test
    public void gameIsWon_WhenBlackHas5PiecesHorizontallyWithoutGap() throws Exception {
        List<Piece> pieces = new ArrayList<>();
        pieces.add(new Piece(Player.BLACK, 1, 0));
        pieces.add(new Piece(Player.BLACK, 2, 0));
        pieces.add(new Piece(Player.BLACK, 3, 0));
        pieces.add(new Piece(Player.BLACK, 4, 0));
        pieces.add(new Piece(Player.BLACK, 5, 0));

        assertThat(gomokuRules.validateThatGameIsWin(pieces), is(true));
    }

    @Test
    public void gameIsWon_WhenBoardHasManyPieces_AndBlackHas5PiecesHorizontallyWithoutGap() throws Exception {
        List<Piece> pieces = new ArrayList<>();
        pieces.add(new Piece(Player.BLACK, 0, 0));
        pieces.add(new Piece(Player.BLACK, 1, 0));
        pieces.add(new Piece(Player.WHITE, 2, 0));
        pieces.add(new Piece(Player.WHITE, 3, 0));
        pieces.add(new Piece(Player.BLACK, 4, 0));

        pieces.add(new Piece(Player.WHITE, 1, 1));

        pieces.add(new Piece(Player.WHITE, 4, 2));
        pieces.add(new Piece(Player.BLACK, 5, 2));

        pieces.add(new Piece(Player.WHITE, 6, 3));
        pieces.add(new Piece(Player.WHITE, 7, 3));
        pieces.add(new Piece(Player.WHITE, 8, 3));

        pieces.add(new Piece(Player.BLACK, 8, 4));
        pieces.add(new Piece(Player.BLACK, 9, 4));
        pieces.add(new Piece(Player.BLACK, 10, 4));
        pieces.add(new Piece(Player.BLACK, 11, 4));
        pieces.add(new Piece(Player.BLACK, 12, 4));

        assertThat(gomokuRules.validateThatGameIsWin(pieces), is(true));
    }

    @Test
    public void gameIsWon_WhenBoardHasManyPieces_AndBlackHas10PiecesHorizontallyWithoutGap() throws Exception {
        List<Piece> pieces = new ArrayList<>();
        pieces.add(new Piece(Player.BLACK, 0, 0));
        pieces.add(new Piece(Player.BLACK, 0, 1));
        pieces.add(new Piece(Player.WHITE, 0, 2));
        pieces.add(new Piece(Player.WHITE, 0, 3));
        pieces.add(new Piece(Player.BLACK, 0, 4));

        pieces.add(new Piece(Player.WHITE, 1, 4));

        pieces.add(new Piece(Player.WHITE, 4, 4));
        pieces.add(new Piece(Player.BLACK, 4, 1));

        pieces.add(new Piece(Player.WHITE, 7, 4));
        pieces.add(new Piece(Player.WHITE, 7, 3));
        pieces.add(new Piece(Player.WHITE, 7, 3));

        pieces.add(new Piece(Player.BLACK, 5, 7));
        pieces.add(new Piece(Player.BLACK, 6, 7));
        pieces.add(new Piece(Player.BLACK, 7, 7));
        pieces.add(new Piece(Player.BLACK, 8, 7));
        pieces.add(new Piece(Player.BLACK, 9, 7));
        pieces.add(new Piece(Player.BLACK, 10, 7));
        pieces.add(new Piece(Player.BLACK, 11, 7));
        pieces.add(new Piece(Player.BLACK, 12, 7));
        pieces.add(new Piece(Player.BLACK, 13, 7));
        pieces.add(new Piece(Player.BLACK, 14, 7));

        assertThat(gomokuRules.validateThatGameIsWin(pieces), is(true));
    }

    @Test
    public void gameIsWon_WhenBoardHasManyPieces_AndBlackHas10PiecesHorizontallyWithGap() throws Exception {
        List<Piece> pieces = new ArrayList<>();
        pieces.add(new Piece(Player.BLACK, 0, 0));
        pieces.add(new Piece(Player.BLACK, 0, 0));
        pieces.add(new Piece(Player.WHITE, 0, 0));
        pieces.add(new Piece(Player.WHITE, 0, 0));
        pieces.add(new Piece(Player.BLACK, 0, 0));

        pieces.add(new Piece(Player.WHITE, 1, 4));

        pieces.add(new Piece(Player.WHITE, 4, 5));
        pieces.add(new Piece(Player.BLACK, 4, 5));

        pieces.add(new Piece(Player.WHITE, 7, 6));
        pieces.add(new Piece(Player.WHITE, 7, 6));
        pieces.add(new Piece(Player.WHITE, 7, 6));

        pieces.add(new Piece(Player.BLACK, 8, 7));
        pieces.add(new Piece(Player.BLACK, 9, 7));
        pieces.add(new Piece(Player.BLACK, 10, 7));
        pieces.add(new Piece(Player.BLACK, 11, 7));
        pieces.add(new Piece(Player.BLACK, 12, 7));
        pieces.add(new Piece(Player.BLACK, 13, 7));
        pieces.add(new Piece(Player.BLACK, 14, 7));
        pieces.add(new Piece(Player.BLACK, 15, 7));

        assertThat(gomokuRules.validateThatGameIsWin(pieces), is(true));
    }

    @Test
    public void gameIsWon_WhenBoardHasManyPieces_AndWhiteHas5PiecesHorizontallyWithoutGap() throws Exception {
        List<Piece> pieces = new ArrayList<>();
        pieces.add(new Piece(Player.WHITE, 8, 8));
        pieces.add(new Piece(Player.WHITE, 9, 8));
        pieces.add(new Piece(Player.WHITE, 10, 8));
        pieces.add(new Piece(Player.WHITE, 11, 8));
        pieces.add(new Piece(Player.WHITE, 12, 8));

        assertThat(gomokuRules.validateThatGameIsWin(pieces), is(true));
    }
}
