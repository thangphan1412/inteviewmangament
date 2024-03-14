package fa.training.model;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Entity
@Table(name = "Interview_Schedule")
public class InterviewSchedule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "schedule_id")
    private Integer scheduleId;

    @Column(name = "schedule_title", nullable = false)
    private String scheduleTitle;

    @ManyToMany(cascade = {CascadeType.ALL})
    @JoinTable(name = "InterviewSchedule_Interviewer", joinColumns = {
            @JoinColumn(name = "schedule_id")},
            inverseJoinColumns = {@JoinColumn(name = "user_id")},
            uniqueConstraints = @UniqueConstraint(columnNames = {"schedule_id", "user_id"}))
    private List<User> assignee;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "candidate_name", referencedColumnName = "candidate_id")
    private Candidate candidateName;

    @Column(name = "location")
    private String location;

    @Column(name = "schedule_date", nullable = false)
    private LocalDate scheduleDate;

    @Column(name = "schedule_from", nullable = false)
    private LocalTime scheduleFrom;

    @Column(name = "schedule_to", nullable = false)
    private LocalTime scheduleTo;

    //@Column(name = "recruiter_owner", nullable = false)
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "recruiter_owner", referencedColumnName = "user_id")
    private User recruiterOwner;

    @Column(name = "note", nullable = true)
    private String note;

    @Column(name = "meeting_id", nullable = false)
    private String meetingId;

    @Column(name = "active")
    private Boolean active;

    @Column(name = "interview_schedule_status", nullable = false)
    private String interviewScheduleStatus;

    @Column(name = "interview_schedule_result", nullable = false)
    private String interviewScheduleResult;


    public Integer getScheduleId() {
        return scheduleId;
    }

    public void setScheduleId(Integer scheduleId) {
        this.scheduleId = scheduleId;
    }

    public String getScheduleTitle() {
        return scheduleTitle;
    }

    public void setScheduleTitle(String scheduleTitle) {
        this.scheduleTitle = scheduleTitle;
    }

    public LocalDate getScheduleDate() {
        return scheduleDate;
    }

    public void setScheduleDate(LocalDate scheduleDate) {
        this.scheduleDate = scheduleDate;
    }

    public User getRecruiterOwner() {
        return recruiterOwner;
    }

    public void setRecruiterOwner(User recruiterOwner) {
        this.recruiterOwner = recruiterOwner;
    }

    public LocalTime getScheduleFrom() {
        return scheduleFrom;
    }

    public void setScheduleFrom(LocalTime scheduleFrom) {
        this.scheduleFrom = scheduleFrom;
    }

    public LocalTime getScheduleTo() {
        return scheduleTo;
    }

    public void setScheduleTo(LocalTime scheduleTo) {
        this.scheduleTo = scheduleTo;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }


    public Candidate getCandidateName() {
        return candidateName;
    }

    public void setCandidateName(Candidate candidate) {
        this.candidateName = candidate;
    }

    public List<User> getAssignee() {
        return assignee;
    }

    public void setAssignee(List<User> assignee) {
        this.assignee = assignee;
    }

    public String getMeetingId() {
        return meetingId;
    }

    public void setMeetingId(String meetingId) {
        this.meetingId = meetingId;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public String getInterviewScheduleStatus() {
        return interviewScheduleStatus;
    }

    public void setInterviewScheduleStatus(String interviewScheduleStatus) {
        this.interviewScheduleStatus = interviewScheduleStatus;
    }

    public String getInterviewScheduleResult() {
        return interviewScheduleResult;
    }

    public void setInterviewScheduleResult(String interviewScheduleResult) {
        this.interviewScheduleResult = interviewScheduleResult;
    }


}
