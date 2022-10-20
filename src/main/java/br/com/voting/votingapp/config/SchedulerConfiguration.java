package br.com.voting.votingapp.config;

import java.time.LocalDateTime;

import br.com.voting.votingapp.util.RestTemplateUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@Slf4j
@Configuration
@EnableScheduling
@RequiredArgsConstructor
public class SchedulerConfiguration {

    private final RestTemplateUtil restTemplateUtil;

    @Value("${rest.api.schedule.calculate-result}")
    private String URL;

    // Scheduler used for demonstration purposes, to simulate the execution of the job
    // it should be scheduled by an in-house scheduler / cloud scheduler(aws, gcp, azure)
    @Scheduled(cron = "*/60 * * * * *")
    public void calculateExpiredSessionResults() {
        log.info("[SCHEDULED-TASK:CalculateExpiredSessionResults]: {}", LocalDateTime.now());
        restTemplateUtil.doPostRes(URL, null, new HttpHeaders(), ParameterizedTypeReference.forType(Void.class));

    }
}
