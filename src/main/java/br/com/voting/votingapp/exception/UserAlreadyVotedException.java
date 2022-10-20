package br.com.voting.votingapp.exception;

import static br.com.voting.votingapp.exception.ExceptionMessage.USER_ALREADY_VOTED;

public class UserAlreadyVotedException extends BusinessException {

    public UserAlreadyVotedException(String message) {
        super(message);
    }

    public UserAlreadyVotedException() {
        super(USER_ALREADY_VOTED.getMessage());
    }
}
