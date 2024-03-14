package fa.training.service.impl;

import fa.training.model.Candidate;
import fa.training.repository.CandidateRepository;
import fa.training.service.ICandidateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CandidateServiceImpl implements ICandidateService {
    @Autowired
    CandidateRepository candidateRepository;

    @Override
    public int createCandidate(Candidate candidate) {
        try {
            Candidate candidateResult = candidateRepository.save(candidate);
        } catch (Exception ex) {
            ex.printStackTrace();
            return -1;
        }
        return 1;
    }

    @Override
    public int updateCandidate(Candidate candidate) {
        try {
            Optional<Candidate> candidateOptional = candidateRepository.findById(candidate.getCandidateId());
            if (candidateOptional.isPresent()) {
                // Cập nhật thông tin của candidate
                Candidate updatedCandidate = candidateOptional.get();
                updatedCandidate.setFullname(candidate.getFullname());
                updatedCandidate.setDob(candidate.getDob());
                updatedCandidate.setGender(candidate.getGender());
                updatedCandidate.setPhoneNo(candidate.getPhoneNo());
                updatedCandidate.setEmail(candidate.getEmail());
                updatedCandidate.setAddress(candidate.getAddress());
                updatedCandidate.setCvAttachment(candidate.getCvAttachment());
                updatedCandidate.setPosition(candidate.getPostion());
                updatedCandidate.setYearOfExperience(candidate.getYearOfExperience());
                updatedCandidate.setSkills(candidate.getSkills());
                updatedCandidate.setCandidateStatus(candidate.getCandidateStatus());
                updatedCandidate.setHighest_level(candidate.getHighest_level());
                updatedCandidate.setUser(candidate.getUser());
                updatedCandidate.setNote(candidate.getNote());

                // Lưu candidate cập nhật vào database
                candidateRepository.save(updatedCandidate);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return -1;
        }
        return 1;
    }

    @Override
    public List<Candidate> getAll() {
        List<Candidate> candidates = new ArrayList<>();
        try {
            candidates = candidateRepository.findAllByActive(true);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return candidates;
    }

    @Override
    public Candidate getCandidateById(int id) {
        return candidateRepository.findById(id).orElse(null);
    }

    @Override
    public int deleteCandidate(int id) {
        try {
            Candidate candidate = candidateRepository.findById(id).orElse(null);
            if (candidate == null) {
                throw new Exception();
            }
            candidate.setActive(false);
            candidateRepository.save(candidate);
            return 1;
        } catch (Exception ex) {
            ex.printStackTrace();
            return -1;
        }
    }

    @Override
    public Candidate getCandidateByEmail(String email) {
        return candidateRepository.findByEmail(email);
    }

    @Override
    public List<Candidate> findByCandidateStatus(String status) {
        return candidateRepository.findByCandidateStatusAndActiveIsTrue(status);
    }

    @Override
    public int updateCandidateStatus(Candidate candidate) {
        try {
            Optional<Candidate> candidateOptional = candidateRepository.findById(candidate.getCandidateId());
            if (candidateOptional.isPresent()) {
                // Cập nhật thông tin của candidate
                Candidate updatedCandidate = candidateOptional.get();
                updatedCandidate.setCandidateStatus(candidate.getCandidateStatus());
                // Lưu candidate cập nhật vào database
                candidateRepository.save(updatedCandidate);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return -1;
        }
        return 1;
    }
}
