package br.com.voting.votingapp.model;

import java.time.LocalDateTime;

import br.com.voting.votingapp.model.enums.VotingValue;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@Builder
@Document(collection = "vote")
public class Vote {

    @Id
    private String id;

    private String topicId;

    private String sessionId;

    private String userId;

    private VotingValue value;

    @Builder.Default
    private LocalDateTime createdAt = LocalDateTime.now();
}
