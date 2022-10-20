package br.com.voting.votingapp.service;

import static br.com.voting.votingapp.exception.ExceptionMessage.NO_SESSION_ACTIVE;
import static br.com.voting.votingapp.exception.ExceptionMessage.TOPIC_NOT_FOUND;

import java.time.LocalDateTime;
import java.util.List;

import br.com.voting.votingapp.dao.repository.TopicRepository;
import br.com.voting.votingapp.dao.repository.VoteRepository;
import br.com.voting.votingapp.dao.rest.UserDAO;
import br.com.voting.votingapp.dto.input.UserValidationInputDTO;
import br.com.voting.votingapp.dto.input.VoteCountInputDTO;
import br.com.voting.votingapp.dto.request.SessionRequestDTO;
import br.com.voting.votingapp.dto.request.TopicRequestDTO;
import br.com.voting.votingapp.dto.request.VoteRequestDTO;
import br.com.voting.votingapp.dto.response.TopicResponseDTO;
import br.com.voting.votingapp.dto.response.VoteResponseDTO;
import br.com.voting.votingapp.exception.EndDateBeforeCurrentDateException;
import br.com.voting.votingapp.exception.NotFoundException;
import br.com.voting.votingapp.exception.SessionAlreadyActiveException;
import br.com.voting.votingapp.exception.SessionEndedException;
import br.com.voting.votingapp.exception.UserAlreadyVotedException;
import br.com.voting.votingapp.exception.UserNotAbleToVoteException;
import br.com.voting.votingapp.mapper.SessionMapper;
import br.com.voting.votingapp.mapper.TopicMapper;
import br.com.voting.votingapp.mapper.VoteMapper;
import br.com.voting.votingapp.messaging.producer.Producer;
import br.com.voting.votingapp.model.Session;
import br.com.voting.votingapp.model.Topic;
import br.com.voting.votingapp.model.Vote;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class TopicService {

    private final TopicRepository topicRepository;

    private final VoteRepository voteRepository;

    private final Producer<Session> kafkaProducer;

    private final UserDAO userDAO;

    public TopicResponseDTO getTopicInfo(String topicId) {
        Topic topic = topicRepository.findById(topicId).orElseThrow(() -> new NotFoundException(TOPIC_NOT_FOUND.getMessage()));

        return TopicMapper.toTopicResponseDTO(topic);
    }

    public void create(TopicRequestDTO topicRequestDTO) {

        Topic newTopic = TopicMapper.toTopic(topicRequestDTO);

        topicRepository.save(newTopic);
    }

    public void createSession(String topicId, SessionRequestDTO sessionRequestDTO) {

        Topic topic = topicRepository.findById(topicId)
                .orElseThrow(() -> new NotFoundException(TOPIC_NOT_FOUND.getMessage()));

        topic.getActiveSession()
                .ifPresent(session -> {
                    throw new SessionAlreadyActiveException();
                });

        if (sessionRequestDTO.getEndAt() == null) {
            sessionRequestDTO.setEndAt(LocalDateTime.now().plusMinutes(1));
        }

        if (sessionRequestDTO.getEndAt().isBefore(LocalDateTime.now())) {
            throw new EndDateBeforeCurrentDateException();
        }

        Session session = SessionMapper.toSession(topicId, sessionRequestDTO);

        topic.addSession(session);

        topicRepository.save(topic);
    }

    public VoteResponseDTO registerVote(String topicId, VoteRequestDTO voteRequestDTO) {

        UserValidationInputDTO userValidationInputDTO = userDAO.validateUserById(voteRequestDTO.getUserId());

        if (Boolean.FALSE.equals(userValidationInputDTO.getStatus().isAbleToVote())) {
            throw new UserNotAbleToVoteException();
        }

        Topic topic = topicRepository.findById(topicId)
                .orElseThrow(() -> new NotFoundException(TOPIC_NOT_FOUND.getMessage()));

        Session session = topic.getActiveSession()
                .orElseThrow(() -> new NotFoundException(NO_SESSION_ACTIVE.getMessage()));

        if (session.isExpired()) {
            throw new SessionEndedException();
        }

        voteRepository.findByTopicIdAndUserId(topicId, voteRequestDTO.getUserId()).ifPresent(vote -> {
            throw new UserAlreadyVotedException();
        });

        Vote persistedVote = voteRepository.save(VoteMapper.toVote(voteRequestDTO, topicId, session.getId()));
        return VoteMapper.toVoteOutputDTO(persistedVote);
    }

    @Transactional
    public void calculateVotesFromEndedSessions() {
        topicRepository.findAllWithActiveSession()
                .forEach(topic -> {
                    topic.getExpiredSession()
                            .ifPresent(session -> {
                                session.setActive(false);
                                List<VoteCountInputDTO> voteCountList = voteRepository.countBySessionId(session.getId());
                                session.populateVoteCount(voteCountList);
                                kafkaProducer.send(session);
                                log.info(
                                        "[JOB-TASK-CALCULATE-SESSION-VOTES] sessionId: " + session.getId()
                                                + " - yesVotes: " + session.getYesVotes() + " - noVotes: " + session.getNoVotes());
                                topicRepository.save(topic);

                            });
                });
    }
}








