package xyz.jacobclark.validators;

import xyz.jacobclark.models.Piece;
import xyz.jacobclark.models.PebbleType;

import java.util.List;

public class HorizontalValidator {
    public boolean validateConsecutivePieces(List<Piece> pieces, int validConsecutiveNumber) {
        PebbleType previousCheckedPiece = null;
        int consecutiveSameColourPiecesInRow = 0;
        int lastPieceColumnPosition = 0;

        for (Piece piece : pieces) {
            boolean isFirstCheckedPiece = previousCheckedPiece == null;

            if (isFirstCheckedPiece) {
                previousCheckedPiece = piece.getPebbleType();
                lastPieceColumnPosition = piece.getColumn();
                consecutiveSameColourPiecesInRow++;
                continue;
            }

            boolean thePieceIsSameColourAsLastPiece = previousCheckedPiece == piece.getPebbleType();
            boolean thereAreMultipleSameColourPiecesInARow = consecutiveSameColourPiecesInRow > 0;
            boolean thePiecesAreUnbroken = lastPieceColumnPosition == (piece.getColumn() - 1);
            boolean lastRowWasBroken = lastPieceColumnPosition == 0;

            if (thePieceIsSameColourAsLastPiece &&
                    thereAreMultipleSameColourPiecesInARow &&
                    thePiecesAreUnbroken ||
                    lastRowWasBroken) {
                consecutiveSameColourPiecesInRow++;
                lastPieceColumnPosition = piece.getColumn();
            } else {
                consecutiveSameColourPiecesInRow = 0;
                lastPieceColumnPosition = 0;
            }
        }

        if (consecutiveSameColourPiecesInRow < validConsecutiveNumber) {
            return false;
        }

        return true;
    }
}
