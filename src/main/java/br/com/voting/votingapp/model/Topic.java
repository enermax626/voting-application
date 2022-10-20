package br.com.voting.votingapp.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@Builder
@Document(collection = "topic")
public class Topic {

    @Id
    private String id;

    private String name;

    private String description;

    @Builder.Default
    private List<Session> sessions = new ArrayList<>();

    @Builder.Default
    private LocalDateTime createdAt = LocalDateTime.now();

    @JsonIgnore
    public Optional<Session> getActiveSession() {
        return sessions.stream()
                .filter(Session::getActive)
                .findFirst();
    }

    @JsonIgnore
    public Optional<Session> getExpiredSession() {
        return sessions.stream()
                .filter(Session::isExpired)
                .findFirst();
    }

    public void addSession(Session session) {
        sessions.add(session);
    }

}
