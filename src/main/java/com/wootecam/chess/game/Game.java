package com.wootecam.chess.game;

import static com.wootecam.chess.board.Board.END_COLUMN_SYMBOL;
import static com.wootecam.chess.board.Board.END_ROW_SYMBOL;
import static com.wootecam.chess.board.Board.START_COLUMN_SYMBOL;
import static com.wootecam.chess.board.Board.START_ROW_SYMBOL;

import com.wootecam.chess.board.Board;
import com.wootecam.chess.pieces.Blank;
import com.wootecam.chess.pieces.Color;
import com.wootecam.chess.pieces.Piece;
import com.wootecam.chess.pieces.Position;
import com.wootecam.chess.pieces.Type;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

public class Game {

    private static final Color[] ORDER = {Color.WHITE, Color.BLACK};
    private static final AtomicInteger COUNTER = new AtomicInteger(0);

    private final Board board;
    private final CoordinatesExtractor extractor;
    private final PieceMoveVerifier pieceMoveVerifier;

    public Game(final Board board, final CoordinatesExtractor extractor, final PieceMoveVerifier pieceMoveVerifier) {
        this.board = board;
        this.extractor = extractor;
        this.pieceMoveVerifier = pieceMoveVerifier;
    }

    public void move(final String startCoordinates, final String targetCoordinates) {
        Color currentOrderColor = ORDER[COUNTER.getAndIncrement() % ORDER.length];

        Position startPosition = extractor.extractPosition(startCoordinates);
        Position targetPosition = extractor.extractPosition(targetCoordinates);

        Piece startPiece = board.findPiece(startPosition);
        Piece targetPiece = board.findPiece(targetPosition);

        validateMovePieces(startPiece, currentOrderColor);
        pieceMoveVerifier.verifyMove(startPiece, targetPiece, startPosition, targetPosition, board::findPiece);

        board.updatePiece(startPosition, new Blank());
        board.updatePiece(targetPosition, startPiece);
    }

    private void validateMovePieces(final Piece piece, final Color currentOrderColor) {
        if (piece.isBlank()) {
            throw new IllegalArgumentException("빈칸은 이동시킬 수 없습니다.");
        }
        if (piece.getColor() != currentOrderColor) {
            String message = String.format("지금은 %s(이)가 놓을 차례입니다. selectedColor = %s",
                    currentOrderColor,
                    piece.getColor()
            );
            throw new IllegalStateException(message);
        }
    }

    public double calculatePoint(final Color color) {
        return calculatePiecesPoint(color) + calculatePawnsPoint(color);
    }

    private double calculatePiecesPoint(final Color color) {
        return board.getRanks()
                .stream()
                .mapToDouble(rank -> rank.calculateRankPiecesPoint(color))
                .sum();
    }

    private double calculatePawnsPoint(final Color color) {
        return IntStream.rangeClosed(START_COLUMN_SYMBOL, END_COLUMN_SYMBOL)
                .mapToDouble(columnSymbol -> calculateColumnPawnPoint(color, (char) columnSymbol))
                .sum();
    }

    private double calculateColumnPawnPoint(final Color color, final char columnSymbol) {
        int pawnCount = (int) IntStream.rangeClosed(START_ROW_SYMBOL, END_ROW_SYMBOL)
                .filter(rowSymbol -> isSamePawn(color, extractor.createCoordinate(columnSymbol, (char) rowSymbol)))
                .count();

        return Type.getPawnPoint(pawnCount);
    }

    private boolean isSamePawn(final Color color, final String coordinates) {
        Position position = extractor.extractPosition(coordinates);

        return board.findPiece(position).isSameColorAndType(color, Type.PAWN);
    }
}
