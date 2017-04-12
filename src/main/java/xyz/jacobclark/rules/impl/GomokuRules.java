package xyz.jacobclark.rules.impl;

import xyz.jacobclark.exceptions.NotPlayersTurnException;
import xyz.jacobclark.exceptions.PositionOutOfBoundsException;
import xyz.jacobclark.exceptions.PositionOccupiedException;
import xyz.jacobclark.models.Piece;
import xyz.jacobclark.rules.Rules;
import xyz.jacobclark.validators.HorizontalValidator;

import java.util.List;
import java.util.function.Predicate;

public class GomokuRules implements Rules {

    public static final int WINNING_CONSECUTIVE_NUMBER = 5;

    @Override
    public boolean validateThatMoveIsLegal(List<Piece> pieces, Piece piece) throws PositionOccupiedException, PositionOutOfBoundsException, NotPlayersTurnException {
        if (pieces.stream().anyMatch(twoPiecesAtSameXYIntersectionOnBoard(piece)))
            throw new PositionOccupiedException();

        if (pieces.size() > 0 && pieces.get(pieces.size() - 1).getPebbleType() == piece.getPebbleType())
            throw new NotPlayersTurnException();

        isValidColumn(piece);
        isValidRow(piece);

        return true;
    }

    @Override
    public boolean validateThatGameIsWin(List<Piece> pieces) {
        return isHorizontalWin(pieces);
    }

    private boolean isHorizontalWin(List<Piece> pieces) {
        return new HorizontalValidator().validateConsecutivePieces(pieces, WINNING_CONSECUTIVE_NUMBER);
    }

    private void isValidRow(Piece piece) throws PositionOutOfBoundsException {
        if (piece.getRow() > 15)
            throw new PositionOutOfBoundsException();

        if (piece.getRow() < 0)
            throw new PositionOutOfBoundsException();
    }

    private void isValidColumn(Piece piece) throws PositionOutOfBoundsException {
        if (piece.getColumn() > 15)
            throw new PositionOutOfBoundsException();

        if (piece.getColumn() < 0)
            throw new PositionOutOfBoundsException();
    }

    private Predicate<Piece> twoPiecesAtSameXYIntersectionOnBoard(Piece piece) {
        return pieceUnderTest ->
                Integer.valueOf(pieceUnderTest.getRow()).equals(piece.getRow()) && Integer.valueOf(pieceUnderTest.getColumn()).equals(piece.getColumn());
    }
}
