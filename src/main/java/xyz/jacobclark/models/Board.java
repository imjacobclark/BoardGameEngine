package xyz.jacobclark.models;

import xyz.jacobclark.exceptions.NotPlayersTurnException;
import xyz.jacobclark.exceptions.PositionOccupiedException;
import xyz.jacobclark.exceptions.PositionOutOfBoundsException;
import xyz.jacobclark.models.Piece;
import xyz.jacobclark.models.PebbleType;
import xyz.jacobclark.rules.Rules;

import java.util.ArrayList;
import java.util.List;

public class Board {
    private List<Piece> pieces = new ArrayList<>();
    private Rules rules = null;

    public Board() {
    }

    public Board(Rules rules) {
        this.rules = rules;
    }

    public Piece placePiece(PebbleType pebbleType, int column, int row) throws PositionOccupiedException, PositionOutOfBoundsException, NotPlayersTurnException {
        Piece piece = new Piece(pebbleType, column, row);

        rules.validateThatMoveIsLegal(pieces, piece);

        pieces.add(piece);

        return piece;
    }

    public List<Piece> getPieces() {
        return pieces;
    }
}
