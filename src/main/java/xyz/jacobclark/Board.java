package xyz.jacobclark;

import xyz.jacobclark.exceptions.PositionOccupiedException;
import xyz.jacobclark.models.Piece;
import xyz.jacobclark.models.Player;
import xyz.jacobclark.rules.Rules;

import java.util.ArrayList;
import java.util.List;

public class Board {
    private List<Piece> pieces = new ArrayList<>();
    private final Rules rules;

    public Board(Rules rules) {
        this.rules = rules;
    }

    public Piece placePiece(Player player, int column, int row) throws PositionOccupiedException {
        rules.validateMove(pieces, player, column, row);

        Piece piece = new Piece(player, column, row);
        pieces.add(piece);

        return piece;
    }
}
