package com.wootecam.chess;

import static com.wootecam.chess.utils.StringUtils.appendNewLine;

import com.wootecam.chess.pieces.Piece;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Board {

    private static final String EMPTY_PIECES_RESULTS = "........";
    private static final int PIECE_COUNT = 8;

    private final List<Piece> blackPawnPieces = new ArrayList<>();
    private final List<Piece> blackOtherPieces = new ArrayList<>();
    private final List<Piece> whitePawnPieces = new ArrayList<>();
    private final List<Piece> whiteOtherPieces = new ArrayList<>();

    public Board() {
    }

    public void initialize() {
        blackPawnPieces.addAll(createPawns(Piece.COLOR_BLACK, Piece.BLACK_PAWN_REPRESENTATION));
        whitePawnPieces.addAll(createPawns(Piece.COLOR_WHITE, Piece.WHITE_PAWN_REPRESENTATION));
        blackOtherPieces.addAll((createBlackOtherPieces()));
        whiteOtherPieces.addAll((createWhiteOtherPieces()));
    }

    private List<Piece> createPawns(String color, String representation) {
        return IntStream.range(0, PIECE_COUNT)
                .mapToObj(i -> new Piece(color, representation))
                .toList();
    }

    private List<Piece> createBlackOtherPieces() {
        return List.of(
                Piece.createBlackRook(),
                Piece.createBlackKnight(),
                Piece.createBlackBishop(),
                Piece.createBlackQueen(),
                Piece.createBlackKing(),
                Piece.createBlackBishop(),
                Piece.createBlackKnight(),
                Piece.createBlackRook()
        );
    }

    private List<Piece> createWhiteOtherPieces() {
        return List.of(
                Piece.createWhiteRook(),
                Piece.createWhiteKnight(),
                Piece.createWhiteBishop(),
                Piece.createWhiteQueen(),
                Piece.createWhiteKing(),
                Piece.createWhiteBishop(),
                Piece.createWhiteKnight(),
                Piece.createWhiteRook()
        );
    }

    public void add(final Piece piece) {
        whitePawnPieces.add(piece);
    }

    public Piece findPiece(final int pieceIndex) {
        if (pieceIndex < 0 || whitePawnPieces.size() <= pieceIndex) {
            String message = String.format("폰 인덱스는 0미만이거나 폰의 개수보다 크거나 같을 수 없습니다. size = %d", whitePawnPieces.size());
            throw new IllegalArgumentException(message);
        }

        return whitePawnPieces.get(pieceIndex);
    }

    public void print() {
        StringBuilder boardResults = new StringBuilder();

        boardResults.append(appendNewLine(EMPTY_PIECES_RESULTS))
                .append(appendNewLine(getBlackPawnsResults()))
                .append(appendNewLine(EMPTY_PIECES_RESULTS))
                .append(appendNewLine(EMPTY_PIECES_RESULTS))
                .append(appendNewLine(EMPTY_PIECES_RESULTS))
                .append(appendNewLine(EMPTY_PIECES_RESULTS))
                .append(appendNewLine(getWhitePawnsResults()))
                .append(appendNewLine(EMPTY_PIECES_RESULTS));

        System.out.println(boardResults);
    }

    public String showBoard() {
        StringBuilder boardResults = new StringBuilder();

        boardResults.append(appendNewLine(createPiecesResults(blackOtherPieces)))
                .append(appendNewLine(createPiecesResults(blackPawnPieces)))
                .append(appendNewLine(EMPTY_PIECES_RESULTS))
                .append(appendNewLine(EMPTY_PIECES_RESULTS))
                .append(appendNewLine(EMPTY_PIECES_RESULTS))
                .append(appendNewLine(EMPTY_PIECES_RESULTS))
                .append(appendNewLine(createPiecesResults(whitePawnPieces)))
                .append(appendNewLine(createPiecesResults(whiteOtherPieces)));

        return boardResults.toString();
    }

    public int pieceCount() {
        return blackPawnPieces.size()
                + blackOtherPieces.size()
                + whitePawnPieces.size()
                + whiteOtherPieces.size();
    }

    public int size() {
        return whitePawnPieces.size();
    }

    public String getWhitePawnsResults() {
        return createPiecesResults(whitePawnPieces);
    }

    public String getBlackPawnsResults() {
        return createPiecesResults(blackPawnPieces);
    }

    private String createPiecesResults(List<Piece> pieces) {
        return pieces.stream()
                .map(Piece::getRepresentation)
                .collect(Collectors.joining());
    }
}
