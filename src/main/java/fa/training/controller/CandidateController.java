package fa.training.controller;

import fa.training.dto.CandidateWithRecruiter;
import fa.training.model.Candidate;
import fa.training.model.User;
import fa.training.service.ICandidateService;
import fa.training.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/Candidate")
public class CandidateController {

    private ICandidateService iCandidateService;

    @Autowired
    private UserService userService;

    public CandidateController(ICandidateService iCandidateService) {
        super();
        this.iCandidateService = iCandidateService;
    }

    @GetMapping("/CreateCandidate")
    public String createCandidateView(Model model) {
//        Candidate candidate = new Candidate();
//        model.addAttribute("candidate", candidate);
        model.addAttribute("recruiterList", userService.findByRole("recruiter"));
        return "pages/candidate/create-candidate";
    }

//    @PostMapping("/createCandidate")
//    public String createCandidate(@ModelAttribute("candidate") Candidate candidate, Model model) {
//        // xử lý thông tin candidate được gửi lên từ form
//        candidate.setActive(true);
//        int status = iCandidateService.createCandidate(candidate);
//        return "redirect:/candidates";
//    }

    @PostMapping("/createCandidate")
    @ResponseBody
    public ResponseEntity<Object> createCandidate(@RequestBody CandidateWithRecruiter candidateWithRecruiter) {
        Candidate candidate = candidateWithRecruiter.getCandidate();
        User user = candidateWithRecruiter.getUser();
        // xử lý thông tin candidate được gửi lên từ form
        candidate.setActive(true);
        candidate.setUser(user);
        int status = iCandidateService.createCandidate(candidate);
        if (status == -1) {
            return new ResponseEntity<>("Create candidate failed", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>("Created candidate successfully", HttpStatus.OK);
    }

    @PutMapping("/updateCandidate")
    public ResponseEntity<Object> updateCandidate(@RequestBody CandidateWithRecruiter candidateWithRecruiter) {
        // xử lý thông tin candidate được gửi lên từ form
        Candidate candidate = candidateWithRecruiter.getCandidate();
        candidate.setUser(candidateWithRecruiter.getUser());
        int status = iCandidateService.updateCandidate(candidate);
        if (status == -1) {
            return new ResponseEntity<>("Update candidate failed", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>("Update candidate successfully", HttpStatus.OK);
    }

    @GetMapping("/candidateList")
    public String viewListCandidate(Model model) {
        model.addAttribute("candidateList", iCandidateService.getAll());
        return "pages/candidate/candidate-list";
    }

    @GetMapping("viewDetails/{id}")
    public ResponseEntity<CandidateWithRecruiter> getCandidate(@PathVariable Integer id) {
        Candidate candidate = iCandidateService.getCandidateById(id);
        CandidateWithRecruiter candidateWithRecruiter = new CandidateWithRecruiter();
        candidateWithRecruiter.setCandidate(candidate);
        candidateWithRecruiter.setUser(candidate.getUser());
        if (candidate == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().body(candidateWithRecruiter);
    }

    @GetMapping("/edit/{id}")
    public String editCandidate(Model model, @PathVariable Integer id) {
//        Candidate candidate = iCandidateService.getCandidateById(id);
//        model.addAttribute("candidate", candidate);
        model.addAttribute("id", id);
        model.addAttribute("recruiterList", userService.findByRole("recruiter"));

        return "pages/candidate/edit-candidate";
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Integer> deleteCandidate(@PathVariable Integer id) {
        int deleteStatus = iCandidateService.deleteCandidate(id);
        if (deleteStatus == -1) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok().body(deleteStatus);
    }
}
