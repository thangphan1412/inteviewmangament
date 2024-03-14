package fa.training.dto;

import fa.training.model.Candidate;
import fa.training.model.User;

public class CandidateWithRecruiter {
    private User user;
    private Candidate candidate;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Candidate getCandidate() {
        return candidate;
    }

    public void setCandidate(Candidate candidate) {
        this.candidate = candidate;
    }
}
