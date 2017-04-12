package xyz.jacobclark.unit.controllers;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;
import xyz.jacobclark.controllers.PiecesController;
import xyz.jacobclark.exceptions.NotPlayersTurnException;
import xyz.jacobclark.exceptions.PositionOccupiedException;
import xyz.jacobclark.models.Piece;
import xyz.jacobclark.models.PebbleType;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

@RunWith(MockitoJUnitRunner.class)
public class PiecesControllerTest {

    @Test
    public void placePieceAddsPiece_AndReturnsCurrentBoardState() throws Exception, PositionOccupiedException, NotPlayersTurnException {
        PiecesController piecesController = new PiecesController();
        List<Piece> pieces = piecesController.placePiece(new Piece(PebbleType.BLACK, 0, 0));

        List<Piece> expectedPieces = new ArrayList<>();
        expectedPieces.add(new Piece(PebbleType.BLACK, 0, 0));

        assertThat(pieces, is(expectedPieces));
    }

    @Test
    public void getPiecesReturnsAllPiecesOnBoard() throws Exception, PositionOccupiedException, NotPlayersTurnException {
        List<Piece> expectedPieces = new ArrayList<>();
        expectedPieces.add(new Piece(PebbleType.BLACK, 0, 0));

        PiecesController piecesController = new PiecesController();
        piecesController.placePiece(new Piece(PebbleType.BLACK, 0, 0));

        assertThat(piecesController.getPieces(), is(expectedPieces));
    }
}
