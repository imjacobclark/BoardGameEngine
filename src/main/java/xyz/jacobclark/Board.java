package xyz.jacobclark;

import xyz.jacobclark.exceptions.PositionOccupiedException;
import xyz.jacobclark.exceptions.PositionOutOfBoundsException;
import xyz.jacobclark.models.Move;
import xyz.jacobclark.models.Piece;
import xyz.jacobclark.models.Player;
import xyz.jacobclark.unit.rules.Rules;

import java.util.ArrayList;
import java.util.List;

public class Board {
    private List<Piece> pieces = new ArrayList<>();
    private final Rules rules;

    public Board(Rules rules) {
        this.rules = rules;
    }

    public Piece placePiece(Player player, int column, int row) throws PositionOccupiedException, PositionOutOfBoundsException {
        Move move = new Move(player, column, row);
        Piece piece = new Piece(move.getPlayer(), move.getColumn(), move.getRow());

        rules.validateThatMoveIsLegal(pieces, move);

        pieces.add(piece);

        return piece;
    }

    public List<Piece> getPieces() {
        return pieces;
    }
}
