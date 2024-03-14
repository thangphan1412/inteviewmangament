package fa.training.repository;

import fa.training.model.Candidate;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CandidateRepository extends JpaRepository<Candidate, Integer> {
    Candidate findByEmail(String email);

    List<Candidate> findByCandidateStatusAndActiveIsTrue(String status);

    Candidate findByCandidateStatus(String status);

    List<Candidate> findAllByActive(boolean active);


}
