package br.com.voting.votingapp.mapper;

import java.util.Optional;

import br.com.voting.votingapp.dto.request.TopicRequestDTO;
import br.com.voting.votingapp.dto.response.SessionResponseDTO;
import br.com.voting.votingapp.dto.response.TopicResponseDTO;
import br.com.voting.votingapp.model.Session;
import br.com.voting.votingapp.model.Topic;

public class TopicMapper {

    public static Topic toTopic(TopicRequestDTO topicRequestDTO) {
        return Topic.builder()
                .name(topicRequestDTO.getName())
                .description(topicRequestDTO.getDescription())
                .build();
    }

    public static TopicResponseDTO toTopicResponseDTO(Topic topic) {

        Long yesVotes = topic.getSessions().stream()
                .map(Session::getYesVotes)
                .reduce(Long::sum).orElse(0L);
        Long noVotes = topic.getSessions().stream()
                .map(Session::getNoVotes)
                .reduce(Long::sum).orElse(0L);

        Optional<SessionResponseDTO> openSession = topic.getSessions().stream()
                .filter(Session::getActive)
                .map(SessionMapper::toSessionResponseDTO)
                .findFirst();

        return TopicResponseDTO.builder()
                .id(topic.getId())
                .name(topic.getName())
                .description(topic.getDescription())
                .yesVotes(yesVotes)
                .noVotes(noVotes)
                .openSession(openSession.orElse(null))
                .build();
    }
}
