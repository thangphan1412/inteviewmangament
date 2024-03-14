package fa.training.service.impl;

import fa.training.model.Offer;
import fa.training.repository.OfferRepository;
import fa.training.service.OfferService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class OfferServiceImpl implements OfferService {

    @Autowired
    OfferRepository offerRepository;

    @Override
    public int createOffer(Offer offer) {
        try {
            Offer offerResult = offerRepository.save(offer);
        } catch (Exception ex) {
            ex.printStackTrace();
            return -1;
        }
        return 1;
    }

    @Override
    public int updateOffer(Offer offer) {
        try {
            Optional<Offer> offerOptional = offerRepository.findById(offer.getOfferId());
            if (offerOptional.isPresent()) {
                Offer updateOffer = offerOptional.get();
                updateOffer.setUser(offer.getUser());
                updateOffer.setCandidate(offer.getCandidate());
                updateOffer.setActive(offer.getActive());
                updateOffer.setDepartment(offer.getDepartment());
                updateOffer.setNote(offer.getNote());
                updateOffer.setDueDate(offer.getDueDate());
                updateOffer.setApproveBy(offer.getApproveBy());
                updateOffer.setLevel(offer.getLevel());
                updateOffer.setBasicSalary(offer.getBasicSalary());
                updateOffer.setContractStart(offer.getContractStart());
                updateOffer.setContractEnd(offer.getContractEnd());
                updateOffer.setContractType(offer.getContractType());
                updateOffer.setPositionId(offer.getPositionId());

                offerRepository.save(updateOffer);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return -1;
        }
        return 1;
    }

    @Override
    public List<Offer> getAll() {
        List<Offer> offers = new ArrayList<>();
        try {
            offers = offerRepository.findAllByActive(true);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return offers;
    }

    @Override
    public Offer getOfferById(int id) {
        return offerRepository.findById(id).orElse(null);
    }

    @Override
    public int deleteOffer(int id) {
        try {
            Offer offer = offerRepository.getById(id);
            if (offer == null) {
                throw new Exception();
            }
            offer.setActive(false);
            offerRepository.save(offer);
            return 1;
        } catch (Exception ex) {
            ex.printStackTrace();
            return -1;
        }
    }


}
