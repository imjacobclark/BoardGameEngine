package xyz.jacobclark.controllers;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import xyz.jacobclark.Board;
import xyz.jacobclark.exceptions.PositionOccupiedException;
import xyz.jacobclark.exceptions.PositionOutOfBoundsException;
import xyz.jacobclark.models.Move;
import xyz.jacobclark.models.Piece;
import xyz.jacobclark.rules.impl.GomokuRules;

import java.util.List;

@RestController
public class BoardController {
    Board board = new Board(new GomokuRules());

    @MessageMapping("/board")
    public List<Piece> placePiece(Move move) throws PositionOutOfBoundsException, PositionOccupiedException {
        board.placePiece(move.getPlayer(), move.getColumn(), move.getRow());
        return board.getPieces();
    }

    @CrossOrigin(origins = "*")
    @GetMapping("/board")
    public List<Piece> getPieces() {
        return board.getPieces();
    }
}