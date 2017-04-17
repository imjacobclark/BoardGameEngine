package xyz.jacobclark.validators;

import xyz.jacobclark.models.PebbleType;
import xyz.jacobclark.models.Piece;

import java.util.List;
import java.util.stream.Collectors;

public class GomokuWinValidator {
    public boolean validate(List<Piece> pieces, int validConsecutiveNumber) {
        List<Piece> whitePieces = pieces.stream().filter(piece -> piece.getPebbleType() == PebbleType.WHITE).collect(Collectors.toList());
        List<Piece> blackPieces = pieces.stream().filter(piece -> piece.getPebbleType() == PebbleType.BLACK).collect(Collectors.toList());

        return doesHaveNConsecutivePiecesInColumn(validConsecutiveNumber, whitePieces) ||
                doesHaveNConsecutivePiecesInColumn(validConsecutiveNumber, blackPieces);
    }

    private boolean doesHaveNConsecutivePiecesInColumn(int validConsecutiveNumber, List<Piece> pieces) {
        boolean initialRun = true;
        int consecutivePiecesInColumn = 0;
        int lastCheckedColumn = 0;

        for (Piece piece : pieces) {
            if (initialRun) {
                consecutivePiecesInColumn++;
                lastCheckedColumn = piece.getColumn();
                initialRun = false;
                continue;
            }

            if (lastCheckedColumn + 1 == piece.getColumn()) {
                consecutivePiecesInColumn++;
            } else {
                consecutivePiecesInColumn = 1;
            }

            lastCheckedColumn = piece.getColumn();
        }

        if (consecutivePiecesInColumn >= validConsecutiveNumber)
            return true;

        return false;
    }
}
