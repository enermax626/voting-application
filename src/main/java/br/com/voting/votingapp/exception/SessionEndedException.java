package br.com.voting.votingapp.exception;

import static br.com.voting.votingapp.exception.ExceptionMessage.SESSION_ENDED;

public class SessionEndedException extends BusinessException {

    public SessionEndedException(String message) {
        super(message);
    }

    public SessionEndedException() {
        super(SESSION_ENDED.getMessage());
    }
}
