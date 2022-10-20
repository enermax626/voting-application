package br.com.voting.votingapp.dto.request;

import java.time.LocalDateTime;

import javax.validation.constraints.NotEmpty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SessionRequestDTO {

    @NotEmpty(message = "Description is required")
    private String description;

    private LocalDateTime endAt;

}
