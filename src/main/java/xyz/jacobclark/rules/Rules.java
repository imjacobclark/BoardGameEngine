package xyz.jacobclark.rules;

import xyz.jacobclark.exceptions.NotPlayersTurnException;
import xyz.jacobclark.exceptions.PositionOutOfBoundsException;
import xyz.jacobclark.exceptions.PositionOccupiedException;
import xyz.jacobclark.models.Piece;

import java.util.List;

public interface Rules {
    boolean validateThatMoveIsLegal(List<Piece> pieces, Piece piece) throws PositionOccupiedException, PositionOutOfBoundsException, NotPlayersTurnException;
    boolean validateThatGameIsWin(List<Piece> pieces);
}
