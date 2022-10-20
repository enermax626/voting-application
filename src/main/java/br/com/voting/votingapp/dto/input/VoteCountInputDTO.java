package br.com.voting.votingapp.dto.input;

import lombok.Data;

@Data
public class VoteCountInputDTO {

    private String id;

    private Long count;

    public Boolean match(String id) {
        return this.id.equals(id);
    }
}
