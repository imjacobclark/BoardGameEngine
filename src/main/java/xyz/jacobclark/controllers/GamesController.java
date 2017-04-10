package xyz.jacobclark.controllers;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import xyz.jacobclark.Board;
import xyz.jacobclark.exceptions.PositionOccupiedException;
import xyz.jacobclark.models.Move;
import xyz.jacobclark.models.Piece;
import xyz.jacobclark.rules.impl.GomokuRules;

import java.util.List;

@Controller
public class GamesController {
    Board board = new Board(new GomokuRules());

    @MessageMapping("/games")
    @SendTo("/topic/games")
    public List<Piece> placePiece(Move move) throws Exception, PositionOccupiedException {
        board.placePiece(move.getPlayer(), move.getColumn(), move.getRow());
        return board.getPieces();
    }
}