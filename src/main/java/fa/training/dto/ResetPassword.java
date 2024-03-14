package fa.training.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResetPassword {
    private String newPassword;

    private String token;
}
