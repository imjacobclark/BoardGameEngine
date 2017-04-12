package xyz.jacobclark.rules.impl;

import xyz.jacobclark.exceptions.NotPlayersTurnException;
import xyz.jacobclark.exceptions.PositionOutOfBoundsException;
import xyz.jacobclark.models.Move;
import xyz.jacobclark.exceptions.PositionOccupiedException;
import xyz.jacobclark.models.Piece;
import xyz.jacobclark.rules.Rules;
import xyz.jacobclark.validators.HorizontalValidator;

import java.util.List;
import java.util.function.Predicate;

public class GomokuRules implements Rules {

    public static final int WINNING_CONSECUTIVE_NUMBER = 5;

    @Override
    public boolean validateThatMoveIsLegal(List<Piece> pieces, Move move) throws PositionOccupiedException, PositionOutOfBoundsException, NotPlayersTurnException {
        if (pieces.stream().anyMatch(twoPiecesAtSameXYIntersectionOnBoard(move)))
            throw new PositionOccupiedException();

        if (pieces.size() > 0 && pieces.get(pieces.size() - 1).getPlayer() == move.getPlayer())
            throw new NotPlayersTurnException();

        isValidColumn(move);
        isValidRow(move);

        return true;
    }

    @Override
    public boolean validateThatGameIsWin(List<Piece> pieces) {
        return isHorizontalWin(pieces);
    }

    private boolean isHorizontalWin(List<Piece> pieces) {
        return new HorizontalValidator().validateConsecutivePieces(pieces, WINNING_CONSECUTIVE_NUMBER);
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
