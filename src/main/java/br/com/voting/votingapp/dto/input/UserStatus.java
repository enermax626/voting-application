package br.com.voting.votingapp.dto.input;

public enum UserStatus {
    ABLE_TO_VOTE,
    UNABLE_TO_VOTE;

    public Boolean isAbleToVote() {
        return this.equals(ABLE_TO_VOTE);
    }
}
