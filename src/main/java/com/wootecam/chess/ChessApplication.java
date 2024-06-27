package com.wootecam.chess;

import com.wootecam.chess.pieces.Color;
import com.wootecam.chess.pieces.Rank;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.StringTokenizer;

public class ChessApplication {

    private static final String START_COMMAND = "start";
    private static final String END_COMMAND = "end";
    private static final String MOVE_COMMAND = "move";

    public static void main(String[] args) {
        Scanner inputReader = new Scanner(System.in);
        Board board = initialize();

        printStartMessage();
        while (isContinue(inputReader.nextLine())) {
            board.print();
            System.out.println("명령을 입력하세요.");
            String command = inputReader.nextLine();

            if (command.startsWith(MOVE_COMMAND)) {
                moveCommand(command, board);
            }

            board.print();
            printStartMessage();
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
        CoordinatesExtractor extractor = new CoordinatesExtractor();

        return new Board(ranks, extractor);
    }

    private static void printStartMessage() {
        System.out.println("시작하려면 start를 종료하려면 end를 입력하세요.");
    }

    private static boolean isContinue(String input) {
        if (START_COMMAND.equals(input)) {
            return true;
        }
        if (END_COMMAND.equals(input)) {
            return false;
        }
        String message = String.format("잘못된 입력입니다. ('%s', '%s'만 가능) input = %s", START_COMMAND, END_COMMAND, input);
        throw new IllegalArgumentException(message);
    }

    private static void moveCommand(final String command, final Board board) {
        StringTokenizer moveToken = new StringTokenizer(command.substring(MOVE_COMMAND.length()));
        String startCoordinates = moveToken.nextToken();
        String targetCoordinates = moveToken.nextToken();

        board.move(startCoordinates, targetCoordinates);
    }
}
