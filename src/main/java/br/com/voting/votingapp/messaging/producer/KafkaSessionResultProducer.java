package br.com.voting.votingapp.messaging.producer;

import java.util.concurrent.ExecutionException;

import br.com.voting.votingapp.exception.ExceptionMessage;
import br.com.voting.votingapp.exception.KafkaProducerException;
import br.com.voting.votingapp.model.Session;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class KafkaSessionResultProducer implements Producer<Session> {

    @Value("${messaging.kafka.topic.result-topic}")
    private String topic;

    private final KafkaTemplate<String, Session> kafkaTemplate;

    @Override
    public void send(Session message) {
        try {
            kafkaTemplate.send(topic, message.getId(), message).get();
        } catch (ExecutionException | InterruptedException e) {
            throw new KafkaProducerException(ExceptionMessage.KAFKA_PRODUCER_ERROR.getMessage());
        }
    }
}
