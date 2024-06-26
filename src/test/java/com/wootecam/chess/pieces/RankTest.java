package com.wootecam.chess.pieces;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class RankTest {

    @Test
    void Rank에_포함되는_Pawn_집합을_만들_수_있다() {
        // when
        Rank pawns = Rank.createPawns(Color.WHITE);

        // then
        assertAll(
                () -> assertThat(pawns.countPiece()).isEqualTo(8),
                () -> assertThat(pawns.createResults()).isEqualTo("pppppppp")
        );
    }

    @Test
    void Rank에_포함되는_Pawn을_제외한_기물_집합을_만들_수_있다() {
        // when
        Rank whiteOtherPieces = Rank.createWhiteOtherPieces();

        // then
        assertAll(
                () -> assertThat(whiteOtherPieces.countPiece()).isEqualTo(8),
                () -> assertThat(whiteOtherPieces.createResults()).isEqualTo("rnbqkbnr")
        );
    }

    @Nested
    class Blank인_Rank집합은 {

        private Rank blanks;

        @BeforeEach
        void setUp() {
            blanks = Rank.createBlanks();
        }

        @Test
        void piece개수를_셀때_제외된다() {
            // then
            assertThat(blanks.countPiece()).isEqualTo(0);
        }

        @Test
        void 아무기물도_표시하지_않는다() {
            // then
            assertThat(blanks.createResults()).isEqualTo("........");
        }
    }
}
