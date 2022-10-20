package br.com.voting.votingapp.messaging.producer;

public interface Producer<T> {

    void send(T message);
}
