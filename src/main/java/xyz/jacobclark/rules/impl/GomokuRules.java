package xyz.jacobclark.rules.impl;

import xyz.jacobclark.exceptions.PositionOutOfBoundsException;
import xyz.jacobclark.models.Move;
import xyz.jacobclark.exceptions.PositionOccupiedException;
import xyz.jacobclark.models.Piece;
import xyz.jacobclark.models.Player;
import xyz.jacobclark.rules.Rules;

import java.util.List;
import java.util.function.Predicate;

public class GomokuRules implements Rules {
    @Override
    public boolean validateThatMoveIsLegal(List<Piece> pieces, Move move) throws PositionOccupiedException, PositionOutOfBoundsException {
        if (pieces.stream().anyMatch(twoPiecesAtSameXYIntersectionOnBoard(move)))
            throw new PositionOccupiedException();

        isValidColumn(move);
        isValidRow(move);

        return true;
    }

    @Override
    public boolean validateThatGameIsWin(List<Piece> pieces) {
        return isHorizontalWin(pieces);
    }

    private boolean isHorizontalWin(List<Piece> pieces) {
        Player previousCheckedStone = null;
        int consecutiveSameColourStonesInRow = 0;
        int lastPieceColumnPosition = 0;

        for (Piece piece : pieces) {
            boolean isFirstCheckedStone = previousCheckedStone == null;

            if (isFirstCheckedStone) {
                previousCheckedStone = piece.getPlayer();
                lastPieceColumnPosition = piece.getColumn();
                consecutiveSameColourStonesInRow++;
                continue;
            }

            boolean theStoneIsSameColourAsLastStone = previousCheckedStone == piece.getPlayer();
            boolean thereAreMultipleSameColourStonesInARow = consecutiveSameColourStonesInRow >= 0;
            boolean theStonesAreUnbroken = lastPieceColumnPosition == (piece.getColumn() - 1);
            boolean lastRowWasBroken = lastPieceColumnPosition == 0;

            if (theStoneIsSameColourAsLastStone &&
                    thereAreMultipleSameColourStonesInARow &&
                    theStonesAreUnbroken ||
                    lastRowWasBroken) {
                consecutiveSameColourStonesInRow++;
                lastPieceColumnPosition = piece.getColumn();
            } else {
                consecutiveSameColourStonesInRow = 0;
                lastPieceColumnPosition = 0;
            }
        }

        if (consecutiveSameColourStonesInRow < 5) {
            return false;
        }

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
