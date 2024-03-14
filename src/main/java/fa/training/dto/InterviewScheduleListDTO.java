package fa.training.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Getter
@Setter
@ToString
public class InterviewScheduleListDTO {

    private Integer scheduleId;

    private String scheduleTitle;

    private String candidateName;

    private List<String> assignee;

    private LocalDate scheduleDate;

    private LocalTime scheduleFrom;

    private LocalTime scheduleTo;

    private String interviewScheduleResult;

    private String interviewScheduleStatus;
}
