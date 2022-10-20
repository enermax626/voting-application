package br.com.voting.votingapp.exception;

import lombok.Getter;

@Getter
public enum ExceptionMessage {
    TOPIC_NOT_FOUND("topic.not.found"),
    SESSION_NOT_FOUND("session.not.found"),
    SESSION_ALREADY_ACTIVE("session.already.active"),
    END_DATE_BEFORE_START_DATE("end.date.before.current.date"),
    NO_SESSION_ACTIVE("no.session.active"),
    SESSION_ENDED("session.ended"),
    USER_ALREADY_VOTED("user.already.voted"),
    USER_NOT_FOUND("user.not.found"),
    USER_NOT_ABLE_TO_VOTE("user.not.able.to.vote"),
    RESPONSE_DATA_NOT_FOUND("response.data.not.found"),
    KAFKA_PRODUCER_ERROR("kafka.producer.error");

    private final String message;

    ExceptionMessage(String message) {
        this.message = message;
    }
}
