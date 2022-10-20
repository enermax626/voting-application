package br.com.voting.votingapp.mapper;

import br.com.voting.votingapp.dto.request.VoteRequestDTO;
import br.com.voting.votingapp.dto.response.VoteResponseDTO;
import br.com.voting.votingapp.model.Vote;

public class VoteMapper {

    public static Vote toVote(VoteRequestDTO voteRequestDTO, String topicId, String sessionId) {
        return Vote.builder()
                .userId(voteRequestDTO.getUserId())
                .topicId(topicId)
                .sessionId(sessionId)
                .value(voteRequestDTO.getVote())
                .build();
    }

    public static VoteResponseDTO toVoteOutputDTO(Vote vote) {
        return VoteResponseDTO.builder()
                .id(vote.getId())
                .topicId(vote.getTopicId())
                .sessionId(vote.getSessionId())
                .userId(vote.getUserId())
                .value(vote.getValue())
                .createdAt(vote.getCreatedAt())
                .build();
    }
}
