package fa.training.service;

import fa.training.model.Candidate;

import java.util.List;

public interface ICandidateService {
    int createCandidate(Candidate candidate);

    int updateCandidate(Candidate candidate);

    int updateCandidateStatus(Candidate candidate);

    List<Candidate> getAll();

    Candidate getCandidateById(int id);

    int deleteCandidate(int id);

    Candidate getCandidateByEmail(String email);

    List<Candidate> findByCandidateStatus(String status);
}
