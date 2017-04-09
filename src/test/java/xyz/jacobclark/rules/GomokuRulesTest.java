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

import static org.junit.Assert.assertTrue;

public class GomokuRulesTest {
    Rules gomokuRules = new GomokuRules();

    @Test
    public void canExecuteAValidMove() throws PositionOutOfBoundsException, PositionOccupiedException {
        boolean result = gomokuRules.validateMove(new ArrayList<>(), new Move(Player.BLACK, 15, 15));
        assertTrue(result);
    }

    @Test(expected = PositionOccupiedException.class)
    public void canNotPlaceAStoneAtGivenPositionOnBoard_WhenAStoneIsAlreadyPlacedThere() throws PositionOccupiedException, PositionOutOfBoundsException {
        List<Piece> pieces = new ArrayList<>();
        pieces.add(new Piece(Player.BLACK, 0, 0));

        gomokuRules.validateMove(pieces, new Move(Player.BLACK, 0, 0));
    }

    @Test(expected = PositionOutOfBoundsException.class)
    public void canNotPlaceAStoneAtGivenPositionOnBoard_WhenItIsInAnNonExistentUpperBoundColumn() throws PositionOutOfBoundsException, PositionOccupiedException {
        gomokuRules.validateMove(new ArrayList<>(), new Move(Player.BLACK, 16, 15));
    }

    @Test(expected = PositionOutOfBoundsException.class)
    public void canNotPlaceAStoneAtGivenPositionOnBoard_WhenItIsInAnNonExistentUpperBoundRow() throws PositionOutOfBoundsException, PositionOccupiedException {
        gomokuRules.validateMove(new ArrayList<>(), new Move(Player.BLACK, 15, 16));
    }

    @Test(expected = PositionOutOfBoundsException.class)
    public void canNotPlaceAStoneAtGivenPositionOnBoard_WhenItIsInAnNonExistentLowerBoundColumn() throws PositionOutOfBoundsException, PositionOccupiedException {
        gomokuRules.validateMove(new ArrayList<>(), new Move(Player.BLACK, -16, 15));

    }

    @Test(expected = PositionOutOfBoundsException.class)
    public void canNotPlaceAStoneAtGivenPositionOnBoard_WhenItIsInAnNonExistentLowerBoundRow() throws PositionOutOfBoundsException, PositionOccupiedException {
        gomokuRules.validateMove(new ArrayList<>(), new Move(Player.BLACK, 15, -16));
    }
}
