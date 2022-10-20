package br.com.voting.votingapp.service;

import static br.com.voting.votingapp.exception.ExceptionMessage.USER_NOT_FOUND;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Optional;

import br.com.voting.votingapp.dao.repository.TopicRepository;
import br.com.voting.votingapp.dao.repository.VoteRepository;
import br.com.voting.votingapp.dao.rest.UserDAO;
import br.com.voting.votingapp.dto.input.UserStatus;
import br.com.voting.votingapp.dto.input.UserValidationInputDTO;
import br.com.voting.votingapp.dto.request.SessionRequestDTO;
import br.com.voting.votingapp.dto.request.TopicRequestDTO;
import br.com.voting.votingapp.dto.request.VoteRequestDTO;
import br.com.voting.votingapp.dto.response.TopicResponseDTO;
import br.com.voting.votingapp.dto.response.VoteResponseDTO;
import br.com.voting.votingapp.exception.ExceptionMessage;
import br.com.voting.votingapp.exception.NotFoundException;
import br.com.voting.votingapp.messaging.producer.Producer;
import br.com.voting.votingapp.model.Session;
import br.com.voting.votingapp.model.Topic;
import br.com.voting.votingapp.model.Vote;
import br.com.voting.votingapp.model.enums.VotingValue;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class TopicServiceTest {

    @InjectMocks
    private TopicService topicService;

    @Mock
    private TopicRepository topicRepository;

    @Mock
    private VoteRepository voteRepository;

    @Mock
    private Producer<Vote> kafkaProducer;

    @Mock
    private UserDAO userDAO;

    @Test
    void shouldGetTopicInfoAndExpectNoErrors() {
        // given
        String topicId = "634ffe6407bcc72fe63576f4";
        Topic topicResponse = Topic.builder()
                .id("634ffe6407bcc72fe63576f4")
                .name("Topic 1")
                .description("Test Description")
                .createdAt(LocalDateTime.now())
                .build();
        when(topicRepository.findById(topicId)).thenReturn(Optional.of(topicResponse));

        // when
        TopicResponseDTO topicInfo = topicService.getTopicInfo(topicId);

        //then
        verify(topicRepository).findById(topicId);
        assertEquals(topicId, topicInfo.getId());
        assertEquals("Topic 1", topicInfo.getName());
        assertEquals("Test Description", topicInfo.getDescription());
    }

    @Test
    @Disabled
    void shouldNotGetTopicInfoAndExpectNotFoundException() {
        //TODO
    }

    @Test
    void shouldCreateTopicAndExpectNoErrors() {
        // given
        TopicRequestDTO request = TopicRequestDTO.builder()
                .name("Topic 1")
                .description("Test Description")
                .build();
        // when
        topicService.create(request);

        //then
        verify(topicRepository).save(any(Topic.class));
    }

    @Test
    void shouldCreateSessionAndExpectNoErrors() {

        // given
        String topicId = "634ffe6407bcc72fe63576f4";
        Topic topicResponse = Topic.builder()
                .id("634ffe6407bcc72fe63576f4")
                .name("Topic 1")
                .description("Test Description")
                .createdAt(LocalDateTime.now())
                .build();
        SessionRequestDTO request = SessionRequestDTO.builder()
                .description("Test Description")
                .endAt(LocalDateTime.now().plusMinutes(10))
                .build();

        when(topicRepository.findById(topicId)).thenReturn(Optional.of(topicResponse));

        // when
        topicService.createSession(topicId, request);

        // then
        verify(topicRepository).findById(topicId);
        verify(topicRepository).save(any(Topic.class));

    }

    @Test
    @Disabled
    void shouldCreateSessionWithoutEndDateAndExpectNoErrors() {
        //TODO
    }

    @Test
    void shouldNotCreateSessionAndExpectTopicNotFoundException() {

        // given
        String topicId = "634ffe6407bcc72fe63576f4";
        SessionRequestDTO request = SessionRequestDTO.builder()
                .description("Test Description")
                .endAt(LocalDateTime.now().plusMinutes(10))
                .build();
        when(topicRepository.findById(topicId)).thenReturn(Optional.empty());

        // when

        NotFoundException exception = assertThrows(NotFoundException.class, () -> topicService.createSession(topicId, request));

        // then
        verify(topicRepository).findById(topicId);
        assertEquals(ExceptionMessage.TOPIC_NOT_FOUND.getMessage(), exception.getMessage());

    }

    @Test
    @Disabled
    void shouldNotCreateSessionAndExpectSessionAlreadyActiveException() {
        //TODO
    }

    @Test
    @Disabled
    void shouldNotCreateSessionAndExpectEndDateBeforeCurrentDateException() {
        //TODO
    }

    @Test
    void shouldRegisterVoteAndExpectNoErrors() {

        // given
        String topicId = "634ffe6407bcc72fe63576f4";
        VoteRequestDTO request = VoteRequestDTO.builder()
                .userId("12345678901")
                .vote(VotingValue.YES)
                .build();
        Session session = Session.builder()
                .id("634cf6f4fbe63e64c72f0757")
                .topicId(topicId)
                .description("Test Description")
                .endDate(LocalDateTime.now().plusMinutes(10))
                .build();
        Topic topicResponse = Topic.builder()
                .id("634ffe6407bcc72fe63576f4")
                .name("Topic 1")
                .description("Test Description")
                .createdAt(LocalDateTime.now())
                .sessions(Collections.singletonList(session))
                .build();

        Vote vote = Vote.builder()
                .id("634ffe6407bcc72fe63576f4")
                .sessionId(session.getId())
                .topicId(topicId)
                .userId("12345678901")
                .value(VotingValue.YES)
                .build();

        when(topicRepository.findById(topicId)).thenReturn(Optional.of(topicResponse));
        when(userDAO.validateUserById(any(String.class))).thenReturn(UserValidationInputDTO.builder().status(UserStatus.ABLE_TO_VOTE).build());
        when(voteRepository.findByTopicIdAndUserId(topicId, request.getUserId())).thenReturn(Optional.empty());
        when(voteRepository.save(any(Vote.class))).thenReturn(vote);

        // when
        VoteResponseDTO voteResponseDTO = topicService.registerVote(topicId, request);

        // then
        verify(topicRepository).findById(topicId);
        verify(userDAO).validateUserById(any(String.class));
        verify(voteRepository).findByTopicIdAndUserId(topicId, request.getUserId());
        verify(voteRepository).save(any(Vote.class));
        assertEquals(VotingValue.YES, voteResponseDTO.getValue());
        assertEquals(request.getUserId(), voteResponseDTO.getUserId());
        assertEquals(session.getId(), voteResponseDTO.getSessionId());
    }

    @Test
    void shouldNotRegisterVoteAndExpectNotFoundException() {

        // given
        String topicId = "634ffe6407bcc72fe63576f4";
        VoteRequestDTO request = VoteRequestDTO.builder()
                .userId("12345678901")
                .vote(VotingValue.YES)
                .build();

        when(userDAO.validateUserById(any(String.class))).thenThrow(new NotFoundException(USER_NOT_FOUND.getMessage()));

        // when
        NotFoundException notFoundException = assertThrows(NotFoundException.class, () -> topicService.registerVote(topicId, request));

        // then
        verify(userDAO).validateUserById(any(String.class));
        assertEquals(USER_NOT_FOUND.getMessage(), notFoundException.getMessage());

    }

    @Test
    @Disabled
    void shouldNotRegisterVoteAndExpectUserNotAbleToVoteException() {
        //TODO
    }

    @Test
    @Disabled
    void shouldNotRegisterVoteAndExpectUserNotFoundException() {
        //TODO
    }

    @Test
    void shouldCalculateVotesFromEndedSessionsAndExpectNoErrors() {

        // given
        Session session = Session.builder()
                .id("634cf6f4fbe63e64c72f0757")
                .topicId("634ffe6407bcc72fe63576f4")
                .description("Test Description")
                .active(true)
                .yesVotes(10L)
                .noVotes(5L)
                .endDate(LocalDateTime.now().minusMinutes(10))
                .build();

        Topic topicResponse = Topic.builder()
                .id("634ffe6407bcc72fe63576f4")
                .name("Topic 1")
                .description("Test Description")
                .createdAt(LocalDateTime.now())
                .sessions(Collections.singletonList(session))
                .build();

        when(topicRepository.findAllWithActiveSession()).thenReturn(Collections.singletonList(topicResponse));

        // when
        topicService.calculateVotesFromEndedSessions();

        // then
        verify(topicRepository).findAllWithActiveSession();
        verify(topicRepository).save(any(Topic.class));
        verify(voteRepository).countBySessionId(session.getId());

    }

    @Test
    void shouldCalculateVotesFromEndedSessionsAndExpectToDoNothing() {

        // given
        when(topicRepository.findAllWithActiveSession()).thenReturn(Collections.emptyList());

        // when
        topicService.calculateVotesFromEndedSessions();

        // then
        verify(topicRepository).findAllWithActiveSession();
    }
}