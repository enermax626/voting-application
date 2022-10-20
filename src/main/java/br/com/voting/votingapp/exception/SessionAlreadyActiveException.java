package br.com.voting.votingapp.exception;

import static br.com.voting.votingapp.exception.ExceptionMessage.SESSION_ALREADY_ACTIVE;

public class SessionAlreadyActiveException extends BusinessException {

    public SessionAlreadyActiveException(String message) {
        super(message);
    }

    public SessionAlreadyActiveException() {
        super(SESSION_ALREADY_ACTIVE.getMessage());
    }
}
