package fa.training.dto;

import fa.training.model.Candidate;
import fa.training.model.Offer;
import fa.training.model.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CreateOffer {
    Candidate candidate;

    User user;

    User approveBy;

    Offer offer;

    String offerStatus;
}
