package br.com.voting.votingapp.exception;

import static br.com.voting.votingapp.exception.ExceptionMessage.USER_NOT_ABLE_TO_VOTE;

public class UserNotAbleToVoteException extends BusinessException {

    public UserNotAbleToVoteException(String message) {
        super(message);
    }

    public UserNotAbleToVoteException() {
        super(USER_NOT_ABLE_TO_VOTE.getMessage());
    }
}
