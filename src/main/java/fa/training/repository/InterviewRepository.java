package fa.training.repository;

import fa.training.model.Candidate;
import fa.training.model.InterviewSchedule;
import fa.training.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface InterviewRepository extends JpaRepository<InterviewSchedule, Integer> {
    List<InterviewSchedule> findByActiveIsTrue();

//    @Query("SELECT is.scheduleId, is.assignee, is.note FROM InterviewSchedule is WHERE is.candidateName = :candidateId AND is.interviewScheduleResult = :result")
//    InterviewSchedule findInterviewScheduleByCandidateIdAndStatus(int candidateId, String result);

    InterviewSchedule findByActiveIsTrueAndCandidateNameAndInterviewScheduleResult(Candidate candidate, String result);

    List<InterviewSchedule> findByActiveIsTrueAndAssigneeIs(User assignee);

    @Modifying
    @Transactional
    @Query("UPDATE InterviewSchedule i SET i.interviewScheduleResult = :result WHERE i.scheduleDate = :today AND i.candidateName = :candidate")
    void updateInterviewScheduleResultByScheduleDateAndCandidateStatus(@Param("result") String result, @Param("today") LocalDate today, @Param("candidate") Candidate candidate);
}