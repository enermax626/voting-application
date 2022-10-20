package br.com.voting.votingapp.config;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.KafkaAdmin;

@Configuration
@Profile("dev")
public class KafkaTopicConfiguration {

    @Value("${messaging.kafka.topic.result-topic}")
    public String topicName;

    @Value(value = "${spring.kafka.producer.bootstrap-servers}")
    private String BOOTSTRAP_ADDRESS;

    // Create a topic for demonstration purposes
    @Bean
    public NewTopic simpleSessionResultTopic() {
        // simple topic
        return TopicBuilder.name(topicName)
                .partitions(3)
                .replicas(1)
                .compact()
                .build();
    }

    // Used to Create a topic for demonstration purposes
    @Bean
    public KafkaAdmin kafkaAdmin() {
        //If you Want to add custom properties to KafkaAdmin
        Map<String, Object> configs = new HashMap<>();

        configs.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, BOOTSTRAP_ADDRESS);
        return new KafkaAdmin(configs);
    }

}
