package br.com.voting.votingapp;

import java.time.ZoneId;
import java.util.TimeZone;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class VotingAppApplication {

    public static void main(String[] args) {

        TimeZone.setDefault(TimeZone.getTimeZone(ZoneId.of("UTC")));

        SpringApplication.run(VotingAppApplication.class, args);
    }

}
