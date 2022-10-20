package br.com.voting.votingapp.exception;

import static br.com.voting.votingapp.exception.ExceptionMessage.END_DATE_BEFORE_START_DATE;

public class EndDateBeforeCurrentDateException extends BusinessException {

    public EndDateBeforeCurrentDateException(String message) {
        super(message);
    }

    public EndDateBeforeCurrentDateException() {
        super(END_DATE_BEFORE_START_DATE.getMessage());
    }
}
