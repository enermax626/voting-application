package br.com.voting.votingapp.dto.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class ErrorResponseDTO {

    private final String timestamp = String.valueOf(System.currentTimeMillis());

    private final String message;

    private final Integer code;

}
