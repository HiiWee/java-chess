package com.wootecam.chess;

import java.util.function.BiConsumer;
import java.util.function.Supplier;

public class ExceptionResolver {

    private static final String ERROR_PREFIX_FORMAT = "[ERROR] %s";

    private ExceptionResolver() {
    }

    public static <T, R> void resolveBiConsumerAfterInput(final Supplier<T> inputSupplier, final R r,
                                                          final BiConsumer<T, R> consumer) {
        try {
            consumer.accept(inputSupplier.get(), r);
        } catch (IllegalArgumentException | IllegalStateException e) {
            printExceptionMessage(e.getMessage());
            resolveBiConsumerAfterInput(inputSupplier, r, consumer);
        }
    }

    public static <T> T resolveInput(final Supplier<T> supplier) {
        try {
            return supplier.get();
        } catch (IllegalArgumentException | IllegalStateException e) {
            printExceptionMessage(e.getMessage());
            return resolveInput(supplier);
        }
    }

    private static void printExceptionMessage(final String message) {
        System.out.println(String.format(ERROR_PREFIX_FORMAT, message));
    }
}
