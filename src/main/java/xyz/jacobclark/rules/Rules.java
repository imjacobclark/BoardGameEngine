package xyz.jacobclark.rules;

import xyz.jacobclark.exceptions.PositionOutOfBoundsException;
import xyz.jacobclark.models.Move;
import xyz.jacobclark.exceptions.PositionOccupiedException;
import xyz.jacobclark.models.Piece;

import java.util.List;

public interface Rules {
    boolean validateMove(List<Piece> pieces, Move move) throws PositionOccupiedException, PositionOutOfBoundsException;
}
