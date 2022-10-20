package br.com.voting.votingapp.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RestTemplateRequestException extends RuntimeException {
    private final String message;

    public RestTemplateRequestException(String message) {
        this.message = message;
    }
}
