package br.com.voting.votingapp.exception;

public class NotFoundException extends BusinessException {

    public NotFoundException(String message) {
        super(message);
    }
}
