package fa.training.dto;

import fa.training.model.User;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class AutoFillCandidate {
    String candidateStatus;

    String interviewNote;

    List<User> interviewerList;

}
