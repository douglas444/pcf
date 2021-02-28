package br.ufu.facom.pcf.gui.exception;

import org.apache.commons.lang3.exception.ExceptionUtils;

public class CustomExceptionMessage {

    public static String build(final Exception exception) {

        final String[] stack = ExceptionUtils.getRootCauseStackTrace(exception.getCause());

        if (stack.length == 0) {
            return ExceptionUtils.getRootCauseMessage(exception);
        }

        StringBuilder message = new StringBuilder();

        for (final String line : stack) {
            if (!line.contains("br.ufu.facom.pcf")) {
                message.append("\n    ").append(line);
            } else {
                break;
            }
        }

        return message.toString();

    }
}
