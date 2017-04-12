package xyz.jacobclark;

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
    private final Rules rules;

    public Board(Rules rules) {
        this.rules = rules;
    }

    public Piece place(PebbleType pebbleType, int column, int row) throws PositionOccupiedException, PositionOutOfBoundsException, NotPlayersTurnException {
        Piece piece = new Piece(pebbleType, column, row);

        rules.validateThatMoveIsLegal(pieces, piece);

        pieces.add(piece);

        return piece;
    }

    public List<Piece> getAll() {
        return pieces;
    }
}
