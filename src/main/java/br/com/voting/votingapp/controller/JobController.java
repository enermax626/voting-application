package br.com.voting.votingapp.controller;

import br.com.voting.votingapp.service.TopicService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/job")
@RequiredArgsConstructor
public class JobController {

    private final TopicService topicService;

    // Controller that should be used by schedulers to calculate the results of expired sessions
    @PostMapping("/calculate-results")
    public ResponseEntity<Void> calculateVotes() {

        topicService.calculateVotesFromEndedSessions();
        return ResponseEntity.ok().build();
    }
}
