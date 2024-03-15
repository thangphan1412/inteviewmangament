package fa.training.dto;

import fa.training.model.User;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class UserDTO extends User {
    private String token;
}
