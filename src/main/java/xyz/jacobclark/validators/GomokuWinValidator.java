package xyz.jacobclark.validators;

import xyz.jacobclark.models.PebbleType;
import xyz.jacobclark.models.Piece;

import java.util.Comparator;
import java.util.List;

import static java.util.stream.Collectors.toList;

public class GomokuWinValidator {
    public boolean validate(List<Piece> pieces, int validConsecutiveNumber) {
        List<Piece> whitePieces = pieces.stream().filter(piece -> piece.getPebbleType() == PebbleType.WHITE).collect(toList());
        List<Piece> blackPieces = pieces.stream().filter(piece -> piece.getPebbleType() == PebbleType.BLACK).collect(toList());

        return doesHaveNConsecutivePiecesInRow(validConsecutiveNumber, whitePieces) ||
                doesHaveNConsecutivePiecesInRow(validConsecutiveNumber, blackPieces) ||
                doesHaveNConsecutivePiecesInColumn(validConsecutiveNumber, whitePieces) ||
                doesHaveNConsecutivePiecesInColumn(validConsecutiveNumber, blackPieces);
    }

    public boolean doesHaveNConsecutivePiecesInColumn(int validConsecutiveNumber, List<Piece> pieces){
        boolean initialRun = true;
        int consecutivePiecesInColumn = 0;
        int lastCheckedRow = 0;

        for (Integer row = 0; row <= 15; row++) {
            Integer columnToFilterOn = row;
            List<Piece> piecesInColumn = pieces.stream().filter(piece -> piece.getColumn() == columnToFilterOn).collect(toList());

            if (piecesInColumn.size() == 0) continue;

            piecesInColumn.sort(Comparator.comparing(Piece::getColumn));

            for (Integer piece = 0; piece <= piecesInColumn.size() - 1; piece++) {
                if (initialRun) {
                    initialRun = false;
                    lastCheckedRow = piecesInColumn.get(piece).getRow();
                    consecutivePiecesInColumn++;
                    continue;
                }

                if (lastCheckedRow + 1 == piecesInColumn.get(piece).getRow()) {
                    consecutivePiecesInColumn++;
                } else {
                    consecutivePiecesInColumn = 1;
                }

                lastCheckedRow = piecesInColumn.get(piece).getColumn();
            }
        }

        if (consecutivePiecesInColumn >= validConsecutiveNumber)
            return true;

        return false;
    }

    private boolean doesHaveNConsecutivePiecesInRow(int validConsecutiveNumber, List<Piece> pieces) {
        boolean initialRun = true;
        int consecutivePiecesInColumn = 0;
        int lastCheckedColumn = 0;

        for (Integer row = 0; row <= 15; row++) {
            Integer rowToFilterOn = row;
            List<Piece> piecesInRow = pieces.stream().filter(piece -> piece.getRow() == rowToFilterOn).collect(toList());

            if (piecesInRow.size() == 0) continue;

            piecesInRow.sort(Comparator.comparing(Piece::getColumn));

            for (Integer piece = 0; piece <= piecesInRow.size() - 1; piece++) {
                if (initialRun) {
                    initialRun = false;
                    lastCheckedColumn = piecesInRow.get(piece).getColumn();
                    consecutivePiecesInColumn++;
                    continue;
                }

                if (lastCheckedColumn + 1 == piecesInRow.get(piece).getColumn()) {
                    consecutivePiecesInColumn++;
                } else {
                    consecutivePiecesInColumn = 1;
                }

                lastCheckedColumn = piecesInRow.get(piece).getColumn();
            }
        }

        if (consecutivePiecesInColumn >= validConsecutiveNumber)
            return true;

        return false;
    }
}
