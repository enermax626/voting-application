package br.com.voting.votingapp.mapper;

import br.com.voting.votingapp.dto.request.SessionRequestDTO;
import br.com.voting.votingapp.dto.response.SessionResponseDTO;
import br.com.voting.votingapp.model.Session;

public class SessionMapper {

    public static Session toSession(String topicId, SessionRequestDTO sessionRequestDTO) {
        return Session.builder()
                .description(sessionRequestDTO.getDescription())
                .endDate(sessionRequestDTO.getEndAt())
                .topicId(topicId)
                .build();
    }

    public static SessionResponseDTO toSessionResponseDTO(Session session) {
        return SessionResponseDTO.builder()
                .id(session.getId())
                .description(session.getDescription())
                .endTime(session.getEndDate())
                .build();
    }
}
