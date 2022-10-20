package br.com.voting.votingapp.dto.response;

import java.time.LocalDateTime;

import br.com.voting.votingapp.model.enums.VotingValue;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class VoteResponseDTO {

    private String id;

    private String topicId;

    private String sessionId;

    private String userId;

    private VotingValue value;

    private LocalDateTime createdAt;
}
