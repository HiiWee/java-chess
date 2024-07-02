package com.wootecam.chess;

import static com.wootecam.chess.utils.StringUtils.NEW_LINE;
import static com.wootecam.chess.utils.StringUtils.appendNewLine;

import com.wootecam.chess.board.Rank;
import java.util.List;
import java.util.stream.Collectors;

public class ChessView {

    public void printStartMessage() {
        System.out.println("시작하려면 start를 종료하려면 end를 입력하세요.");
    }

    public void printBoard(final List<Rank> ranks) {
        System.out.println(showBoard(ranks));
    }

    private String showBoard(final List<Rank> ranks) {
        return appendNewLine(ranks.stream()
                .map(this::createRankResults)
                .collect(Collectors.joining(NEW_LINE)));
    }

    private String createRankResults(final Rank rank) {
        return rank.createResults();
    }

    public void printCommandInput() {
        System.out.println("명령을 입력하세요.");
    }

    public void printRetry() {
        System.out.println("다시 입력하세요.");
    }
}
