package fa.training.service;


import fa.training.model.Candidate;
import fa.training.model.InterviewSchedule;
import fa.training.model.User;

import java.time.LocalDate;
import java.util.List;

public interface InterviewService {

    int createInterview(InterviewSchedule interviewSchedule);

    int updateInterview(InterviewSchedule interviewSchedule);

    List<InterviewSchedule> getAll();

    InterviewSchedule getInterviewScheduleById(int id);

    int deleteInterviewSchedule(int id);

    List<InterviewSchedule> getAllActive();

    List<InterviewSchedule> getInterviewScheduleByAssignee(User assignee);

    void updateInterviewScheduleResultByScheduleDate(String result, LocalDate date, Candidate candidateStatus);
}
