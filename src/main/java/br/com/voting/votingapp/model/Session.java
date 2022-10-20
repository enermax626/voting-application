package br.com.voting.votingapp.model;

import java.time.LocalDateTime;
import java.util.List;

import br.com.voting.votingapp.dto.input.VoteCountInputDTO;
import br.com.voting.votingapp.model.enums.VotingValue;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.bson.types.ObjectId;

@Getter
@Setter
@Builder
public class Session {

    @Builder.Default
    private String id = ObjectId.get().toHexString();

    private String topicId;

    private String description;

    @Builder.Default
    private Long yesVotes = 0L;

    @Builder.Default
    private Long noVotes = 0L;

    private LocalDateTime endDate;

    @Builder.Default
    private Boolean active = Boolean.TRUE;

    @Builder.Default
    private LocalDateTime createdAt = LocalDateTime.now();

    public Boolean isExpired() {
        return LocalDateTime.now().isAfter(endDate) && active;
    }

    public void populateVoteCount(List<VoteCountInputDTO> voteCountList) {
        voteCountList.forEach(voteCount -> {
            if (voteCount.match(VotingValue.YES.name())) {
                this.setYesVotes(voteCount.getCount());
            } else {
                this.setNoVotes(voteCount.getCount());
            }
        });
    }
}