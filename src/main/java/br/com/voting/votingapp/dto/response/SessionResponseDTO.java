package br.com.voting.votingapp.dto.response;

import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class SessionResponseDTO {

    private String id;

    private String description;

    private LocalDateTime endTime;
}
