package com.wootecam.chess;

import static com.wootecam.chess.utils.StringUtils.appendNewLine;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

import com.wootecam.chess.pieces.Color;
import com.wootecam.chess.pieces.Piece;
import com.wootecam.chess.pieces.Type;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

public class BoardTest {

    private Board board;

    @BeforeEach
    void setUp() {
        board = new Board();
    }

    @Nested
    class showBoard_메소드는 {

        @Nested
        class 보드를_생성하고_호출하면 {

            @Test
            void 현재_보드의_상태를_문자열로_반환한다() {
                // given
                String expectedResults = generateDefaultBoardResults();

                // when
                String currentBoardResults = board.showBoard();

                // then
                assertThat(currentBoardResults).isEqualTo(expectedResults);
            }

            private String generateDefaultBoardResults() {
                String blankRank = appendNewLine("........");

                return appendNewLine("RNBQKBNR") +
                        appendNewLine("PPPPPPPP") +
                        blankRank + blankRank + blankRank + blankRank +
                        appendNewLine("pppppppp") +
                        appendNewLine("rnbqkbnr");
            }
        }
    }

    @Nested
    class pieceCount_메소드는 {

        @Nested
        class 보드를_생성하고_호출하면 {

            @Test
            void 현재_보드에_존재하는_기물의_갯수를_반환한다() {
                // when
                int count = board.countBoardPieces();

                // then
                assertThat(count).isEqualTo(32);
            }
        }
    }

    @Nested
    class countSpecificBoardPieces_메소드는 {

        @Test
        void 특정_색상과_타입을_가진_모든_Piece의_개수를_카운팅한다() {
            // when
            int blackPawnCount = board.countSpecificBoardPieces(Color.BLACK, Type.PAWN);
            int whiteBishopCount = board.countSpecificBoardPieces(Color.WHITE, Type.BISHOP);

            // then
            assertAll(
                    () -> assertThat(blackPawnCount).isEqualTo(8),
                    () -> assertThat(whiteBishopCount).isEqualTo(2)
            );
        }
    }

    @Nested
    class findPiece_메소드는 {

        @Test
        void 주어진_위치의_기물을_조회한다() {
            // expect
            assertAll(
                    () -> assertThat(board.findPiece("a8")).isEqualTo(Piece.createBlack(Type.ROOK)),
                    () -> assertThat(board.findPiece("h8")).isEqualTo(Piece.createBlack(Type.ROOK)),
                    () -> assertThat(board.findPiece("a1")).isEqualTo(Piece.createWhite(Type.ROOK)),
                    () -> assertThat(board.findPiece("h1")).isEqualTo(Piece.createWhite(Type.ROOK))
            );
        }

        @Nested
        class 만약_8x8_크기의_체스판을_벗어나는_좌표를_입력하면 {

            @ParameterizedTest
            @ValueSource(strings = {"a9", "a0", "i5"})
            void 예외가_발생한다(String invalidCoordinate) {
                // expect
                assertThatThrownBy(() -> board.findPiece(invalidCoordinate))
                        .isInstanceOf(IllegalArgumentException.class)
                        .hasMessage("8 x 8 크기의 체스판의 범위를 벗어나는 좌표입니다. coordinate = " + invalidCoordinate);
            }
        }
    }

    @Nested
    class initializeEmpty_메소드는 {

        @Test
        void 아무것도_없는_체스판을_생성한다() {
            // when
            board.initializeEmpty();
            int count = board.countBoardPieces();

            // then
            assertThat(count).isZero();
        }
    }

    @Nested
    class move_메소드는 {

        @Test
        void 입력한_좌표로_기물을_이동시킨다() {
            // given
            board.initializeEmpty();
            Piece piece = Piece.createBlack(Type.ROOK);
            String coordinate = "b5";

            // when
            board.move(coordinate, piece);

            // then
            assertThat(board.findPiece(coordinate)).isEqualTo(piece);
        }
    }
}
