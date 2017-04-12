package xyz.jacobclark.unit.rules;

import org.junit.Test;
import xyz.jacobclark.exceptions.NotPlayersTurnException;
import xyz.jacobclark.exceptions.PositionOccupiedException;
import xyz.jacobclark.exceptions.PositionOutOfBoundsException;
import xyz.jacobclark.models.Piece;
import xyz.jacobclark.models.PebbleType;
import xyz.jacobclark.rules.Rules;
import xyz.jacobclark.rules.impl.GomokuRules;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

public class GomokuRulesTest {
    Rules gomokuRules = new GomokuRules();

    @Test
    public void canExecuteAValidMove() throws PositionOutOfBoundsException, PositionOccupiedException, NotPlayersTurnException {
        boolean result = gomokuRules.validateThatMoveIsLegal(new ArrayList<>(), new Piece(PebbleType.BLACK, 15, 15));
        assertTrue(result);
    }

    @Test(expected = PositionOccupiedException.class)
    public void canNotPlaceAStoneAtGivenPositionOnBoard_WhenAStoneIsAlreadyPlacedThere() throws PositionOccupiedException, PositionOutOfBoundsException, NotPlayersTurnException {
        List<Piece> pieces = new ArrayList<>();
        pieces.add(new Piece(PebbleType.BLACK, 0, 0));

        gomokuRules.validateThatMoveIsLegal(pieces, new Piece(PebbleType.BLACK, 0, 0));
    }

    @Test(expected = PositionOutOfBoundsException.class)
    public void canNotPlaceAStoneAtGivenPositionOnBoard_WhenItIsInAnNonExistentUpperBoundColumn() throws PositionOutOfBoundsException, PositionOccupiedException, NotPlayersTurnException {
        gomokuRules.validateThatMoveIsLegal(new ArrayList<>(), new Piece(PebbleType.BLACK, 16, 15));
    }

    @Test(expected = PositionOutOfBoundsException.class)
    public void canNotPlaceAStoneAtGivenPositionOnBoard_WhenItIsInAnNonExistentUpperBoundRow() throws PositionOutOfBoundsException, PositionOccupiedException, NotPlayersTurnException {
        gomokuRules.validateThatMoveIsLegal(new ArrayList<>(), new Piece(PebbleType.BLACK, 15, 16));
    }

    @Test(expected = PositionOutOfBoundsException.class)
    public void canNotPlaceAStoneAtGivenPositionOnBoard_WhenItIsInAnNonExistentLowerBoundColumn() throws PositionOutOfBoundsException, PositionOccupiedException, NotPlayersTurnException {
        gomokuRules.validateThatMoveIsLegal(new ArrayList<>(), new Piece(PebbleType.BLACK, -16, 15));

    }

    @Test(expected = PositionOutOfBoundsException.class)
    public void canNotPlaceAStoneAtGivenPositionOnBoard_WhenItIsInAnNonExistentLowerBoundRow() throws PositionOutOfBoundsException, PositionOccupiedException, NotPlayersTurnException {
        gomokuRules.validateThatMoveIsLegal(new ArrayList<>(), new Piece(PebbleType.BLACK, 15, -16));
    }

    @Test(expected = NotPlayersTurnException.class)
    public void canNotPlaceSameColouredStoneTwiceInARow() throws Exception, PositionOccupiedException, NotPlayersTurnException {
        ArrayList<Piece> pieces = new ArrayList<>();
        pieces.add(new Piece(PebbleType.BLACK, 0,0 ));
        pieces.add(new Piece(PebbleType.WHITE, 0,1 ));
        pieces.add(new Piece(PebbleType.BLACK, 0,2 ));

        gomokuRules.validateThatMoveIsLegal(pieces, new Piece(PebbleType.BLACK, 0, 3));
    }
}
