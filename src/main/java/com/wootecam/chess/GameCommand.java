package com.wootecam.chess;

import java.util.Objects;
import java.util.StringTokenizer;
import java.util.function.BiConsumer;

public enum GameCommand {
    START("start"),
    END("end"),
    MOVE("move");

    private final String command;

    GameCommand(final String command) {
        this.command = command;
    }

    public static boolean isContinue(final String input) {
        if (START.command.equals(input)) {
            return true;
        }
        if (END.command.equals(input)) {
            return false;
        }
        String message = String.format("잘못된 입력입니다. ('%s', '%s'만 가능) input = %s", START.command, END.command, input);
        throw new IllegalArgumentException(message);
    }

    public static void move(final String moveInput,
                            final BiConsumer<String, String> gamePlayConsumer) {
        if (Objects.isNull(moveInput) || !moveInput.startsWith(MOVE.command)) {
            throw new IllegalArgumentException("'move {좌표1} {좌표2}'와 같이 입력해야 합니다.");
        }
        StringTokenizer moveToken = new StringTokenizer(moveInput.substring(MOVE.command.length()));
        String startCoordinates = moveToken.nextToken();
        String targetCoordinates = moveToken.nextToken();

        gamePlayConsumer.accept(startCoordinates, targetCoordinates);
    }
}
