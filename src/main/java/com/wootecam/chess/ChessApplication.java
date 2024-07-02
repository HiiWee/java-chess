package com.wootecam.chess;

import com.wootecam.chess.board.Board;
import com.wootecam.chess.board.Rank;
import com.wootecam.chess.game.CoordinatesExtractor;
import com.wootecam.chess.game.Game;
import com.wootecam.chess.game.PieceMoveVerifier;
import com.wootecam.chess.pieces.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ChessApplication {

    public static void main(String[] args) {
        Scanner inputReader = new Scanner(System.in);

        Board board = initialize();
        CoordinatesExtractor extractor = new CoordinatesExtractor();
        PieceMoveVerifier pieceMoveVerifier = new PieceMoveVerifier();

        Game game = new Game(board, extractor, pieceMoveVerifier);
        ChessView chessView = new ChessView();

        chessView.printStartMessage();
        while (ExceptionResolver.resolveInput(() -> GameCommand.isContinue(inputReader.nextLine()))) {
            chessView.printBoard(board.getRanks());
            chessView.printCommandInput();
            ExceptionResolver.resolveBiConsumerAfterInput(inputReader::nextLine, game::move, GameCommand::move);
            chessView.printBoard(board.getRanks());
            chessView.printStartMessage();
        }
    }

    private static Board initialize() {

        List<Rank> ranks = new ArrayList<>();
        ranks.add(Rank.createBlackOtherPieces());
        ranks.add(Rank.createPawns(Color.BLACK));
        ranks.add(Rank.createBlanks());
        ranks.add(Rank.createBlanks());
        ranks.add(Rank.createBlanks());
        ranks.add(Rank.createBlanks());
        ranks.add(Rank.createPawns(Color.WHITE));
        ranks.add(Rank.createWhiteOtherPieces());

        return new Board(ranks);
    }
}
