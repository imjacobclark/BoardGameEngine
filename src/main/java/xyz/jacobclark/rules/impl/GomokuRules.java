package xyz.jacobclark.rules.impl;

import xyz.jacobclark.exceptions.PositionOutOfBoundsException;
import xyz.jacobclark.models.Move;
import xyz.jacobclark.exceptions.PositionOccupiedException;
import xyz.jacobclark.models.Piece;
import xyz.jacobclark.rules.Rules;

import java.util.List;
import java.util.function.Predicate;

public class GomokuRules implements Rules {
    @Override
    public boolean validateMove(List<Piece> pieces, Move move) throws PositionOccupiedException, PositionOutOfBoundsException {
        if (pieces.stream().anyMatch(twoPiecesAtSameXYIntersectionOnBoard(move)))
            throw new PositionOccupiedException();

        isValidColumn(move);
        isValidRow(move);

        return true;
    }

    private void isValidRow(Move move) throws PositionOutOfBoundsException {
        if (move.getRow() > 15)
            throw new PositionOutOfBoundsException();

        if (move.getRow() < 0)
            throw new PositionOutOfBoundsException();
    }

    private void isValidColumn(Move move) throws PositionOutOfBoundsException {
        if (move.getColumn() > 15)
            throw new PositionOutOfBoundsException();

        if (move.getColumn() < 0)
            throw new PositionOutOfBoundsException();
    }

    private Predicate<Piece> twoPiecesAtSameXYIntersectionOnBoard(Move move) {
        return piece ->
                Integer.valueOf(move.getRow()).equals(piece.getRow()) && Integer.valueOf(move.getColumn()).equals(piece.getColumn());
    }
}
