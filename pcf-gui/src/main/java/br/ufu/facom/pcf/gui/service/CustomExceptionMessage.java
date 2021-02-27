package br.ufu.facom.pcf.gui.service;

import org.apache.commons.lang3.exception.ExceptionUtils;

public class CustomExceptionMessage {

    public static String build(final Exception exception) {

        final String[] stack = ExceptionUtils.getRootCauseStackTrace(exception.getCause());

        if (stack.length == 0) {
            return ExceptionUtils.getRootCauseMessage(exception);
        } else if (stack.length == 1) {
            return stack[0];
        } else {
            return stack[0] + "\n    " + stack[1];
        }
    }
}
