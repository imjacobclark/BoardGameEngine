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

        return doesHaveNConsecutivePiecesInColumn(validConsecutiveNumber, whitePieces) ||
                doesHaveNConsecutivePiecesInColumn(validConsecutiveNumber, blackPieces);
    }

    private boolean doesHaveNConsecutivePiecesInColumn(int validConsecutiveNumber, List<Piece> pieces) {
        boolean initialRun = true;
        int consecutivePiecesInColumn = 0;
        int lastCheckedColumn = 0;

        for (Integer row = 0; row <= 15; row++) {
            Integer rowToFilterOn = row;
            List<Piece> rows = pieces.stream().filter(piece -> piece.getRow() == rowToFilterOn).collect(toList());

            if (rows.size() == 0) continue;

            rows.sort(Comparator.comparing(Piece::getColumn));

            for (Integer column = 0; column <= rows.size() - 1; column++) {
                if (initialRun) {
                    initialRun = false;
                    lastCheckedColumn = rows.get(column).getColumn();
                    consecutivePiecesInColumn++;
                    continue;
                }

                if (lastCheckedColumn + 1 == rows.get(column).getColumn()) {
                    consecutivePiecesInColumn++;
                } else {
                    consecutivePiecesInColumn = 1;
                }

                lastCheckedColumn = rows.get(column).getColumn();
            }
        }

        if (consecutivePiecesInColumn >= validConsecutiveNumber)
            return true;

        return false;
    }
}
