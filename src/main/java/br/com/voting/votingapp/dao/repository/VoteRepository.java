package br.com.voting.votingapp.dao.repository;

import java.util.List;
import java.util.Optional;

import br.com.voting.votingapp.dto.input.VoteCountInputDTO;
import br.com.voting.votingapp.model.Vote;
import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

public interface VoteRepository extends MongoRepository<Vote, String> {

    @Query("{ 'topicId' : ?0, 'userId' : ?1 }")
    Optional<Vote> findByTopicIdAndUserId(String topicId, String userId);

    List<Vote> findBySessionId(String sessionId);

    @Aggregation(pipeline = {"{ '$match': { 'sessionId': ?0 }}",
                             "{ $group: { _id: \"$value\", count: { $sum: 1 } } }"})
    List<VoteCountInputDTO> countBySessionId(String sessionId);
}

