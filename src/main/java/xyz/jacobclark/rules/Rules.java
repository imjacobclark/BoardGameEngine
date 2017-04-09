package xyz.jacobclark.rules;

import xyz.jacobclark.exceptions.PositionOccupiedException;
import xyz.jacobclark.models.Piece;
import xyz.jacobclark.models.Player;

import java.util.List;

public interface Rules {
    void validateMove(List<Piece> pieces, Player player, int column, int row) throws PositionOccupiedException;
}
