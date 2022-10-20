package br.com.voting.votingapp.controller;

import javax.validation.Valid;

import br.com.voting.votingapp.dto.request.SessionRequestDTO;
import br.com.voting.votingapp.dto.request.TopicRequestDTO;
import br.com.voting.votingapp.dto.request.VoteRequestDTO;
import br.com.voting.votingapp.dto.response.TopicResponseDTO;
import br.com.voting.votingapp.dto.response.VoteResponseDTO;
import br.com.voting.votingapp.service.TopicService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/topic")
@RequiredArgsConstructor
public class TopicController {

    private final TopicService topicService;

    @GetMapping("/{topicId}")
    public ResponseEntity<TopicResponseDTO> getInfo(@PathVariable String topicId) {

        TopicResponseDTO topicInfo = topicService.getTopicInfo(topicId);
        return ResponseEntity.ok(topicInfo);
    }

    @PostMapping
    public ResponseEntity<Void> create(@Valid @RequestBody TopicRequestDTO topicDTO) {

        topicService.create(topicDTO);
        return ResponseEntity.status(201).build();
    }

    @PostMapping("/{topicId}/session")
    public ResponseEntity<Void> createSession(@PathVariable String topicId,
                                              @Valid @RequestBody SessionRequestDTO sessionDTO) {

        topicService.createSession(topicId, sessionDTO);
        return ResponseEntity.status(201).build();
    }

    @PostMapping("/{topicId}/vote")
    public ResponseEntity<VoteResponseDTO> registerVote(@PathVariable String topicId,
                                                        @Valid @RequestBody VoteRequestDTO voteRequestDTO) {

        VoteResponseDTO vote = topicService.registerVote(topicId, voteRequestDTO);
        return ResponseEntity.status(201).body(vote);
    }
}
