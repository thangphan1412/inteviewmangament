package fa.training.service;

import fa.training.model.Offer;

import java.util.List;

public interface OfferService {
    int createOffer(Offer offer);

    int updateOffer(Offer offer);

    List<Offer> getAll();

    Offer getOfferById(int id);

    int deleteOffer(int id);
}
