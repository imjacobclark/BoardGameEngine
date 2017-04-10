package xyz.jacobclark.unit.controllers;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;
import xyz.jacobclark.controllers.BoardController;
import xyz.jacobclark.exceptions.PositionOccupiedException;
import xyz.jacobclark.models.Move;
import xyz.jacobclark.models.Piece;
import xyz.jacobclark.models.Player;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

@RunWith(MockitoJUnitRunner.class)
public class BoardControllerTest {

    @Test
    public void placePieceAddsPiece_AndReturnsCurrentBoardState() throws Exception, PositionOccupiedException {
        BoardController boardController = new BoardController();
        List<Piece> pieces = boardController.placePiece(new Move(Player.BLACK, 0, 0));

        List<Piece> expectedPieces = new ArrayList<>();
        expectedPieces.add(new Piece(Player.BLACK, 0, 0));

        assertThat(pieces, is(expectedPieces));
    }

    @Test
    public void getPiecesReturnsAllPiecesOnBoard() throws Exception, PositionOccupiedException {
        List<Piece> expectedPieces = new ArrayList<>();
        expectedPieces.add(new Piece(Player.BLACK, 0, 0));

        BoardController boardController = new BoardController();
        boardController.placePiece(new Move(Player.BLACK, 0, 0));

        assertThat(boardController.getPieces(), is(expectedPieces));
    }
}