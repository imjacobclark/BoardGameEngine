package xyz.jacobclark.unit.validators;

import org.junit.Test;
import xyz.jacobclark.models.Piece;
import xyz.jacobclark.models.PebbleType;
import xyz.jacobclark.validators.GomokuWinValidator;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

public class GomokuWinValidatorTest {
    @Test
    public void gameIsNotWon_WhenBlackHas4PiecesHorizontallyWithoutGap() throws Exception {
        List<Piece> pieces = new ArrayList<>();
        pieces.add(new Piece(PebbleType.BLACK, 1, 0));
        pieces.add(new Piece(PebbleType.BLACK, 2, 0));
        pieces.add(new Piece(PebbleType.BLACK, 3, 0));
        pieces.add(new Piece(PebbleType.BLACK, 4, 0));

        boolean result = new GomokuWinValidator().validate(pieces, 5);
        assertThat(result, is(false));
    }

    @Test
    public void gameIsNotWon_WhenBlackHas6PiecesInterruptedByOneWhitePiece() throws Exception {
        List<Piece> pieces = new ArrayList<>();
        pieces.add(new Piece(PebbleType.BLACK, 1, 0));
        pieces.add(new Piece(PebbleType.BLACK, 2, 0));
        pieces.add(new Piece(PebbleType.BLACK, 3, 0));
        pieces.add(new Piece(PebbleType.BLACK, 4, 0));
        pieces.add(new Piece(PebbleType.WHITE, 5, 0));
        pieces.add(new Piece(PebbleType.BLACK, 6, 0));
        pieces.add(new Piece(PebbleType.BLACK, 7, 0));

        boolean result = new GomokuWinValidator().validate(pieces, 5);
        assertThat(result, is(false));
    }

    @Test
    public void gameIsNotWon_WhenNoPiecesArePlaced() throws Exception {
        List<Piece> pieces = new ArrayList<>();
        boolean result = new GomokuWinValidator().validate(pieces, 5);
        assertThat(result, is(false));
    }

    @Test
    public void gameIsNotWon_WhenBlackHas1PieceHorizontallyWithoutGap() throws Exception {
        List<Piece> pieces = new ArrayList<>();
        pieces.add(new Piece(PebbleType.BLACK, 0, 0));

        boolean result = new GomokuWinValidator().validate(pieces, 5);
        assertThat(result, is(false));
    }

    @Test
    public void gameIsNotWon_WhenBlackAndWhiteBothHave1PieceHorizontallyWithoutGap() throws Exception {
        List<Piece> pieces = new ArrayList<>();
        pieces.add(new Piece(PebbleType.BLACK, 0, 0));
        pieces.add(new Piece(PebbleType.WHITE, 0, 1));

        boolean result = new GomokuWinValidator().validate(pieces, 5);
        assertThat(result, is(false));
    }

    @Test
    public void gameIsNotWon_WhenBoardHasManyPieces_AndBlackHas6PiecesHorizontallyWithGapInMiddle() throws Exception {
        List<Piece> pieces = new ArrayList<>();
        pieces.add(new Piece(PebbleType.BLACK, 1, 1));
        pieces.add(new Piece(PebbleType.BLACK, 2, 1));
        pieces.add(new Piece(PebbleType.BLACK, 3, 1));
        pieces.add(new Piece(PebbleType.BLACK, 5, 1));
        pieces.add(new Piece(PebbleType.BLACK, 6, 1));
        pieces.add(new Piece(PebbleType.BLACK, 7, 1));


        boolean result = new GomokuWinValidator().validate(pieces, 5);
        assertThat(result, is(false));
    }

    @Test
    public void gameIsWon_WhenBlackHas5PiecesHorizontallyWithoutGap() throws Exception {
        List<Piece> pieces = new ArrayList<>();
        pieces.add(new Piece(PebbleType.BLACK, 1, 0));
        pieces.add(new Piece(PebbleType.BLACK, 2, 0));
        pieces.add(new Piece(PebbleType.BLACK, 3, 0));
        pieces.add(new Piece(PebbleType.BLACK, 4, 0));
        pieces.add(new Piece(PebbleType.BLACK, 5, 0));

        boolean result = new GomokuWinValidator().validate(pieces, 5);
        assertThat(result, is(true));
    }

    @Test
    public void gameIsWon_WhenBoardHasManyPieces_AndBlackHas5PiecesHorizontallyWithoutGap() throws Exception {
        List<Piece> pieces = new ArrayList<>();
        pieces.add(new Piece(PebbleType.BLACK, 0, 0));
        pieces.add(new Piece(PebbleType.BLACK, 1, 0));
        pieces.add(new Piece(PebbleType.WHITE, 2, 0));
        pieces.add(new Piece(PebbleType.WHITE, 3, 0));
        pieces.add(new Piece(PebbleType.BLACK, 4, 0));

        pieces.add(new Piece(PebbleType.WHITE, 1, 1));

        pieces.add(new Piece(PebbleType.WHITE, 4, 2));
        pieces.add(new Piece(PebbleType.BLACK, 5, 2));

        pieces.add(new Piece(PebbleType.WHITE, 6, 3));
        pieces.add(new Piece(PebbleType.WHITE, 7, 3));
        pieces.add(new Piece(PebbleType.WHITE, 8, 3));

        pieces.add(new Piece(PebbleType.BLACK, 8, 4));
        pieces.add(new Piece(PebbleType.BLACK, 9, 4));
        pieces.add(new Piece(PebbleType.BLACK, 10, 4));
        pieces.add(new Piece(PebbleType.BLACK, 11, 4));
        pieces.add(new Piece(PebbleType.BLACK, 12, 4));

        boolean result = new GomokuWinValidator().validate(pieces, 5);
        assertThat(result, is(true));
    }

    @Test
    public void gameIsWon_WhenBoardHasManyPieces_AndBlackHas10PiecesHorizontallyWithoutGap() throws Exception {
        List<Piece> pieces = new ArrayList<>();
        pieces.add(new Piece(PebbleType.BLACK, 0, 0));
        pieces.add(new Piece(PebbleType.BLACK, 0, 1));
        pieces.add(new Piece(PebbleType.WHITE, 0, 2));
        pieces.add(new Piece(PebbleType.WHITE, 0, 3));
        pieces.add(new Piece(PebbleType.BLACK, 0, 4));

        pieces.add(new Piece(PebbleType.WHITE, 1, 4));

        pieces.add(new Piece(PebbleType.WHITE, 4, 4));
        pieces.add(new Piece(PebbleType.BLACK, 4, 1));

        pieces.add(new Piece(PebbleType.WHITE, 7, 4));
        pieces.add(new Piece(PebbleType.WHITE, 7, 3));
        pieces.add(new Piece(PebbleType.WHITE, 7, 3));

        pieces.add(new Piece(PebbleType.BLACK, 5, 7));
        pieces.add(new Piece(PebbleType.BLACK, 6, 7));
        pieces.add(new Piece(PebbleType.BLACK, 7, 7));
        pieces.add(new Piece(PebbleType.BLACK, 8, 7));
        pieces.add(new Piece(PebbleType.BLACK, 9, 7));
        pieces.add(new Piece(PebbleType.BLACK, 10, 7));
        pieces.add(new Piece(PebbleType.BLACK, 11, 7));
        pieces.add(new Piece(PebbleType.BLACK, 12, 7));
        pieces.add(new Piece(PebbleType.BLACK, 13, 7));
        pieces.add(new Piece(PebbleType.BLACK, 14, 7));

        boolean result = new GomokuWinValidator().validate(pieces, 5);
        assertThat(result, is(true));
    }

    @Test
    public void gameIsWon_WhenBoardHasManyPieces_AndBlackHas10PiecesHorizontallyWithGap() throws Exception {
        List<Piece> pieces = new ArrayList<>();
        pieces.add(new Piece(PebbleType.BLACK, 0, 0));
        pieces.add(new Piece(PebbleType.BLACK, 0, 0));
        pieces.add(new Piece(PebbleType.WHITE, 0, 0));
        pieces.add(new Piece(PebbleType.WHITE, 0, 0));
        pieces.add(new Piece(PebbleType.BLACK, 0, 0));

        pieces.add(new Piece(PebbleType.WHITE, 1, 4));

        pieces.add(new Piece(PebbleType.WHITE, 4, 5));
        pieces.add(new Piece(PebbleType.BLACK, 4, 5));

        pieces.add(new Piece(PebbleType.WHITE, 7, 6));
        pieces.add(new Piece(PebbleType.WHITE, 7, 6));
        pieces.add(new Piece(PebbleType.WHITE, 7, 6));

        pieces.add(new Piece(PebbleType.BLACK, 8, 7));
        pieces.add(new Piece(PebbleType.BLACK, 9, 7));
        pieces.add(new Piece(PebbleType.BLACK, 10, 7));
        pieces.add(new Piece(PebbleType.BLACK, 11, 7));
        pieces.add(new Piece(PebbleType.BLACK, 12, 7));
        pieces.add(new Piece(PebbleType.BLACK, 13, 7));
        pieces.add(new Piece(PebbleType.BLACK, 14, 7));
        pieces.add(new Piece(PebbleType.BLACK, 15, 7));

        boolean result = new GomokuWinValidator().validate(pieces, 5);
        assertThat(result, is(true));
    }

    @Test
    public void gameIsWon_WhenBoardHasManyPieces_AndWhiteHas5PiecesHorizontallyWithoutGap() throws Exception {
        List<Piece> pieces = new ArrayList<>();
        pieces.add(new Piece(PebbleType.WHITE, 8, 8));
        pieces.add(new Piece(PebbleType.WHITE, 9, 8));
        pieces.add(new Piece(PebbleType.WHITE, 10, 8));
        pieces.add(new Piece(PebbleType.WHITE, 11, 8));
        pieces.add(new Piece(PebbleType.WHITE, 12, 8));

        boolean result = new GomokuWinValidator().validate(pieces, 5);
        assertThat(result, is(true));
    }

    @Test
    public void gameIsWon_WhenGameIsPlayed_BlackWhite_BlackWhite() throws Exception {
        List<Piece> pieces = new ArrayList<>();
        pieces.add(new Piece(PebbleType.BLACK, 8, 8));
        pieces.add(new Piece(PebbleType.WHITE, 1, 8));
        pieces.add(new Piece(PebbleType.BLACK, 9, 8));
        pieces.add(new Piece(PebbleType.WHITE, 2, 8));
        pieces.add(new Piece(PebbleType.BLACK, 10, 8));
        pieces.add(new Piece(PebbleType.WHITE, 8, 8));
        pieces.add(new Piece(PebbleType.BLACK, 11, 8));
        pieces.add(new Piece(PebbleType.WHITE, 4, 8));
        pieces.add(new Piece(PebbleType.BLACK, 12, 8));
        pieces.add(new Piece(PebbleType.WHITE, 1, 8));

        boolean result = new GomokuWinValidator().validate(pieces, 5);
        assertThat(result, is(true));
    }
}