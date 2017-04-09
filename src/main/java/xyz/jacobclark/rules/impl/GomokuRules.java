package xyz.jacobclark.rules.impl;

import xyz.jacobclark.exceptions.PositionOccupiedException;
import xyz.jacobclark.models.Piece;
import xyz.jacobclark.models.Player;
import xyz.jacobclark.rules.Rules;

import java.util.List;
import java.util.function.Predicate;

public class GomokuRules implements Rules {
    @Override
    public void validateMove(List<Piece> pieces, Player player, int column, int row) throws PositionOccupiedException {
        if (pieces.stream().anyMatch(twoPiecesAtSameXYIntersectionOnBoard(column, row)))
            throw new PositionOccupiedException();
    }

    private Predicate<Piece> twoPiecesAtSameXYIntersectionOnBoard(int column, int row) {
        return piece ->
                Integer.valueOf(row).equals(piece.getRow()) && Integer.valueOf(column).equals(piece.getColumn());
    }
}
