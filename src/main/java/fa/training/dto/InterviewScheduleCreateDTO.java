package fa.training.dto;


import com.fasterxml.jackson.annotation.JsonProperty;
import fa.training.model.Candidate;
import fa.training.model.User;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@ToString
@Getter
@Setter
public class InterviewScheduleCreateDTO {

    private Integer scheduleId;

    private String scheduleTitle;

    @JsonProperty("assignee")
    private String assigneeAccount; //Lấy account của User có Role là interview

    private List<User> assigneeList;

    @JsonProperty("candidateEmail")
//Phần này sẽ lấy candidate Email thay vì candidateName bởi vì email unique ( nhưng vẫn để hiển thị trên front-end là CandidateName)
    private String candidateEmail;

    private Candidate candidateName; // Dùng để lấy giá trị rồi map sang entity thông qua candidate Email

    private String location;

    private LocalDate scheduleDate;

    private LocalTime scheduleFrom;

    private LocalTime scheduleTo;

    @JsonProperty("recruiterOwnerAccount")
    private String recruiterOwnerAccount;  //Account của recruiter
    private User recruiterOwner;    // từ account của recruiter sẽ lấy ra object trong db

    private String note;

    private String meetingId;

    private String interviewScheduleResult;

    private String interviewScheduleStatus;

}
