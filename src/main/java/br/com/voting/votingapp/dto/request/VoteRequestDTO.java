package br.com.voting.votingapp.dto.request;

import javax.validation.constraints.NotNull;

import br.com.voting.votingapp.model.enums.VotingValue;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VoteRequestDTO {

    private String userId;

    @NotNull(message = "Vote is required")
    private VotingValue vote;
}
