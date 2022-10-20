package br.com.voting.votingapp.dao.repository;

import java.time.LocalDateTime;
import java.util.List;

import br.com.voting.votingapp.model.Topic;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface TopicRepository extends MongoRepository<Topic, String> {


    @Query("{'sessions' : {$elemMatch: { 'active': true}}}")
    List<Topic> findAllWithActiveSession();

}
