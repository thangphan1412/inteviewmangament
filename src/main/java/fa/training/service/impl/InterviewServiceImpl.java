package fa.training.service.impl;

import fa.training.model.Candidate;
import fa.training.model.InterviewSchedule;
import fa.training.model.User;
import fa.training.repository.InterviewRepository;
import fa.training.service.InterviewService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class InterviewServiceImpl implements InterviewService {

    @Autowired
    InterviewRepository interviewRepository;

    @Override
    public int createInterview(InterviewSchedule interviewSchedule) {
        try {
            InterviewSchedule interviewScheduleResult = interviewRepository.save(interviewSchedule);
        } catch (Exception ex) {
            ex.printStackTrace();
            return -1;
        }
        return 1;
    }

    @Override
    public int updateInterview(InterviewSchedule interviewSchedule) {
        try {
            Optional<InterviewSchedule> interviewScheduleOptional = interviewRepository.findById(interviewSchedule.getScheduleId());
            if (interviewScheduleOptional.isPresent()) {
                // Cập nhật thông tin của candidate
                InterviewSchedule interviewScheduleUpdate = interviewScheduleOptional.get();

                ModelMapper modelMapper = new ModelMapper();
                modelMapper.map(interviewSchedule, interviewScheduleUpdate);

//                interviewScheduleUpdate.setScheduleTitle(interviewSchedule.getScheduleTitle());
//                interviewScheduleUpdate.setAssignee(interviewSchedule.getAssignee());
//                interviewScheduleUpdate.setScheduleDate(interviewSchedule.getScheduleDate());
//                interviewScheduleUpdate.setScheduleFrom(interviewSchedule.getScheduleFrom());
//                interviewScheduleUpdate.setScheduleTo(interviewSchedule.getScheduleTo());
//                interviewScheduleUpdate.setLocation(interviewSchedule.getLocation());
//                interviewScheduleUpdate.setMeetingId(interviewSchedule.getMeetingId());
//                interviewScheduleUpdate.setNote(interviewSchedule.getNote());
//                interviewScheduleUpdate.setActive(interviewSchedule.getActive());
//                interviewScheduleUpdate.setInterviewScheduleStatus(interviewSchedule.getInterviewScheduleStatus());
                //interviewScheduleUpdate.setUsers(interviewSchedule.getUsers());
                // interviewScheduleUpdate.setRecruiterOwner(interviewSchedule.getRecruiterOwner());

                // Lưu candidate cập nhật vào database
                interviewRepository.save(interviewScheduleUpdate);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return -1;
        }
        return 1;
    }

    @Override
    public List<InterviewSchedule> getAll() {
        try {
            return interviewRepository.findAll();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return Collections.emptyList();
    }

    @Override
    public InterviewSchedule getInterviewScheduleById(int id) {
        return interviewRepository.findById(id).orElse(null);
    }

    @Override
    public int deleteInterviewSchedule(int id) {
        try {
            interviewRepository.deleteById(id);
            return 1;
        } catch (Exception ex) {
            ex.printStackTrace();
            return -1;
        }
    }

    @Override
    public List<InterviewSchedule> getAllActive() {
        return interviewRepository.findByActiveIsTrue();
    }

    @Override
    public List<InterviewSchedule> getInterviewScheduleByAssignee(User assignee) {
        return interviewRepository.findByActiveIsTrueAndAssigneeIs(assignee);
    }

    @Override
    public void updateInterviewScheduleResultByScheduleDate(String result, LocalDate date, Candidate candidateStatus) {
        interviewRepository.updateInterviewScheduleResultByScheduleDateAndCandidateStatus(result, date, candidateStatus);
    }
}
