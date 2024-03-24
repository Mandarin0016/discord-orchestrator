package com.mandarin.exception;

import com.mandarin.dto.Action;

public class ActionNotSupportedException extends RuntimeException {

    private static final String MESSAGE = "Given action [%s] is not supported by worker with id=[%s].";

    public ActionNotSupportedException(Action action, String workerId) {
        super(MESSAGE.formatted(action, workerId));
    }
}
