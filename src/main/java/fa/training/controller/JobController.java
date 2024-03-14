package fa.training.controller;

import fa.training.model.Job;
import fa.training.service.JobService;
import fa.training.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/Job")
public class JobController {
    @Autowired
    private UserService userService;

    @Autowired
    private JobService jobService;

    public JobController(JobService jobService) {
        super();
        this.jobService = jobService;
    }

    @GetMapping("/createJob")
    public String createJobView(Model model) {
        return "pages/job/create-job";
    }


    @PostMapping("/createJob")
    @ResponseBody
    public ResponseEntity<Object> createJob(@RequestBody Job job) {
        // xử lý thông tin candidate được gửi lên từ form
        job.setActive(true);
        int status = jobService.createJob(job);
        if (status == -1) {
            return new ResponseEntity<>("Create job failed", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>("Created job successfully", HttpStatus.OK);
    }


    @PutMapping("/editJob")
    @ResponseBody
    public ResponseEntity<Object> updateCandidate(@RequestBody Job job) {
        // xử lý thông tin candidate được gửi lên từ form
        job.setActive(true);
        int status = jobService.updateJob(job);
        if (status == -1) {
            return new ResponseEntity<>("Update candidate failed", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>("Update candidate successfully", HttpStatus.OK);
    }


    @GetMapping("/jobList")
    public String viewListJob(Model model) {
        model.addAttribute("jobList", jobService.getAll());
        return "pages/job/job-list";
    }

    @GetMapping("/{id}")
    public ResponseEntity<Job> getJob(@PathVariable Integer id) {
        Job job = jobService.getJobById(id);

        if (job == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().body(job);
    }

    @GetMapping("/edit/{id}")
    public String editJob(Model model, @PathVariable Integer id) {

        model.addAttribute("id", id);
        model.addAttribute("recruiterList", userService.findByRole("recruiter"));

        return "pages/job/edit-job";
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Integer> deleteJob(@PathVariable Integer id) {
        int deleteStatus = jobService.deleteJob(id);
        if (deleteStatus == -1) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok().body(deleteStatus);
    }

}
