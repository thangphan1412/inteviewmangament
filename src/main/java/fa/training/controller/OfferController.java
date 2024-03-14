package fa.training.controller;

import fa.training.dto.AutoFillCandidate;
import fa.training.dto.CreateOffer;
import fa.training.javamail.JavaMail;
import fa.training.model.Candidate;
import fa.training.model.InterviewSchedule;
import fa.training.model.Offer;
import fa.training.repository.InterviewRepository;
import fa.training.service.OfferService;
import fa.training.service.UserService;
import fa.training.service.impl.CandidateServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/offer")
public class OfferController {
    @Autowired
    UserService userService;
    @Autowired
    InterviewRepository interviewRepository;

    @Autowired
    CandidateServiceImpl candidateService;

    @Autowired
    OfferService offerService;

    @Autowired
    JavaMail javaMail;

    @GetMapping("/create")
    public ModelAndView createOfferView() {
        ModelAndView mv = new ModelAndView("pages/offer/create-offer");
        Offer offer = new Offer();
        mv.addObject("offer", offer);
        mv.addObject("managers", userService.findByRole("Manager"));
        mv.addObject("recruiters", userService.findByRole("Recruiter"));
        List<Candidate> candidates = candidateService.findByCandidateStatus("Pass Interview");
        candidates.addAll(candidateService.findByCandidateStatus("Rejected offer"));
        mv.addObject("candidates", candidates);
        mv.addObject("interviewers", userService.findByRole("Interviewer"));
        return mv;
    }

//	@PostMapping("/create")
//	public ModelAndView createOffer(@ModelAttribute("offer") Offer offer) {
//		ModelAndView mv = new ModelAndView("pages/offer/offer-list");
//		offer.setActive(true);
//		mv.addObject("users", userRepository.findAll());
//		mv.addObject("offers", offerRepository.findAll());
//		offerRepository.save(offer);
//		return mv;
//	}

    @GetMapping("/view")
    public ModelAndView viewOfferView() {
        ModelAndView mv = new ModelAndView("pages/offer/offer-list");
        mv.addObject("offers", offerService.getAll());
        mv.addObject("managers", userService.findByRole("Manager"));
        mv.addObject("candidates", candidateService.getAll());
        mv.addObject("recruiters", userService.findByRole("Recruiter"));
        mv.addObject("interviewers", userService.findByRole("Interviewer"));
        return mv;
    }

//	@PostMapping("/delete/{id}")
//	public ModelAndView delete(@PathVariable("id") Integer id) {
//		ModelAndView mv = new ModelAndView("pages/offer/offer-list");
//		System.out.println(id);
//		offerRepository.deleteById(id);
//		Offer offer = new Offer();
//		mv.addObject("offers", offerRepository.findAll());
//		return mv;
//	}

    @GetMapping("/edit/{id}")
    public String editOffer(Model model, @PathVariable Integer id) {

        model.addAttribute("managers", userService.findByRole("Manager"));
        model.addAttribute("candidates", candidateService.getAll());
        model.addAttribute("interviewers", userService.findByRole("Interviewer"));
        model.addAttribute("recruiters", userService.findByRole("Recruiter"));
        return "pages/offer/edit-offer";
    }

//	@PostMapping("/edit/{id}")
//	public ModelAndView editOffer(@PathVariable("id") Integer id, @ModelAttribute("offer") Offer offer) {
//		ModelAndView mv = new ModelAndView("pages/offer/offer-list");
//		offer.setOfferId(id);
//		offer.setActive(true);
//		offerRepository.save(offer);
//		mv.addObject("offers", offerRepository.findAll());
//		return mv;
//	}

    @PostMapping("/createOffer")
    @ResponseBody
    public ResponseEntity<Object> createOffer(@RequestBody CreateOffer createOffer) {
        Offer offer = createOffer.getOffer();
        Candidate candidate = createOffer.getCandidate();
        candidate.setCandidateStatus("Waiting for approval");

        offer.setActive(true);
        offer.setCandidate(createOffer.getCandidate());
        offer.setUser(createOffer.getUser());
        offer.setApproveBy(createOffer.getApproveBy());

        // Update lai status cua candidate thanh waiting for approval
        candidateService.updateCandidateStatus(candidate);
        int status = offerService.createOffer(offer);
        if (status == -1) {
            return new ResponseEntity<>("Create candidate failed", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>("Created candidate successfully", HttpStatus.OK);
    }

    @PutMapping("/updateOffer")
    public ResponseEntity<Object> updateOffer(@RequestBody CreateOffer createOffer) {
        // xử lý thông tin candidate được gửi lên từ form
        Offer offer = createOffer.getOffer();
        offer.setActive(true);
        offer.setCandidate(createOffer.getCandidate());
        offer.setUser(createOffer.getUser());
        offer.setApproveBy(createOffer.getApproveBy());
        int status = offerService.updateOffer(offer);
        if (status == -1) {
            return new ResponseEntity<>("Update candidate failed", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>("Update candidate successfully", HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Offer> getOffer(@PathVariable Integer id) {
        Offer offer = offerService.getOfferById(id);
        if (offer == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().body(offer);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Integer> deleteOffer(@PathVariable Integer id) {
        int deleteStatus = offerService.deleteOffer(id);
        if (deleteStatus == -1) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok().body(deleteStatus);
    }

    @GetMapping("/autofillCandidate/{id}")
    public ResponseEntity<AutoFillCandidate> getAutoFillData(@PathVariable Integer id) {


        Candidate candidate = candidateService.getCandidateById(id);
        String candidateStatus = candidate.getCandidateStatus();

//		InterviewSchedule interviewSchedule = interviewRepository.findInterviewScheduleByCandidateIdAndStatus(candidate.getCandidateId(), "Passed");

        InterviewSchedule interviewSchedule = interviewRepository.findByActiveIsTrueAndCandidateNameAndInterviewScheduleResult(candidate, "Pass");

        AutoFillCandidate autoFill = new AutoFillCandidate();
        autoFill.setCandidateStatus(candidateStatus);
        autoFill.setInterviewerList(interviewSchedule.getAssignee());
        autoFill.setInterviewNote(interviewSchedule.getNote());

        if (autoFill == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().body(autoFill);
    }

    @PutMapping("/updateOfferStatus")
    public ResponseEntity<Object> updateOfferStatus(@RequestBody CreateOffer createOffer) {
        Candidate candidate = candidateService.getCandidateById(createOffer.getCandidate().getCandidateId());
        // Check xem status bằng approve thì chuyển trạng thái candidate thành Waiting for response đồng thời gửi mail
        int status = 0;
        if (createOffer.getOfferStatus().equals("Approved offer")) {
            candidate.setCandidateStatus("Approved offer");
            status = candidateService.updateCandidateStatus(candidate);

        } else if (createOffer.getOfferStatus().equals("Rejected offer")) {
            candidate.setCandidateStatus("Rejected");
            status = candidateService.updateCandidateStatus(candidate);
        }


        if (status == -1) {
            return new ResponseEntity<>("Update candidate failed", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>("Update candidate successfully", HttpStatus.OK);
    }
}