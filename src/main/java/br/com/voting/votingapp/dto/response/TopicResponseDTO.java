package br.com.voting.votingapp.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class TopicResponseDTO {

    private String id;

    private String name;

    private String description;

    private Long yesVotes;

    private Long noVotes;

    private SessionResponseDTO openSession;
}
