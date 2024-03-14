package fa.training.controller;

import fa.training.dto.InterviewScheduleCreateDTO;
import fa.training.dto.InterviewScheduleListDTO;
import fa.training.enumm.CandidateStatus;
import fa.training.enumm.Role;
import fa.training.enumm.Status;
import fa.training.javamail.JavaMail;
import fa.training.model.Candidate;
import fa.training.model.InterviewSchedule;
import fa.training.model.User;
import fa.training.service.ICandidateService;
import fa.training.service.InterviewService;
import fa.training.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Controller
@RequestMapping("/Interview")
public class InterviewController {

    @Autowired
    private JavaMail javaMail;

    @Autowired
    private InterviewService interviewService;

    @Autowired
    private UserService userService;

    @Autowired
    private ICandidateService candidateService;


    public InterviewController(InterviewService interviewService, UserService userService, ICandidateService candidateService) {
        this.interviewService = interviewService;
        this.userService = userService;
        this.candidateService = candidateService;
    }

    @GetMapping("/createInterview")
    public String createInterviewView(Model model) {
        List<User> assigneeList = userService.findByRole(Role.Interviewer.toString());
        if (assigneeList.isEmpty()) {
            User user = new User();
            user.setUserName("Does not have data");
            assigneeList.add(user);
        }
        model.addAttribute("assigneeList", assigneeList);

        List<Candidate> candidateList = candidateService.findByCandidateStatus(CandidateStatus.Open);
        candidateList.addAll(candidateService.findByCandidateStatus(CandidateStatus.FailedInterview));
        candidateList.addAll(candidateService.findByCandidateStatus(CandidateStatus.CancelInterview));
        model.addAttribute("candidateList", candidateList);

        List<User> recruiterList = userService.findByRole(Role.Recruiter.toString());
        model.addAttribute("recruiterList", recruiterList);

        return "pages/interview/create-interview";
    }


    @PostMapping("/createInterview")
    @ResponseBody
    public ResponseEntity<Object> createInterview(@RequestBody InterviewScheduleCreateDTO interviewScheduleCreateDTO) {
        // xử lý thông tin candidate được gửi lên từ form ()


        //Check validate
        // Check Not Null
        if (interviewScheduleCreateDTO.getScheduleTitle() == null || interviewScheduleCreateDTO.getAssigneeAccount() == null
                || interviewScheduleCreateDTO.getCandidateEmail() == null || interviewScheduleCreateDTO.getScheduleDate() == null
                || interviewScheduleCreateDTO.getScheduleTo() == null || interviewScheduleCreateDTO.getScheduleTo() == null
                || interviewScheduleCreateDTO.getRecruiterOwnerAccount() == null) {
            return new ResponseEntity<>("Create interview schedule failed", HttpStatus.BAD_REQUEST);
        }

        //Check Date
        if (interviewScheduleCreateDTO.getScheduleDate().isBefore(LocalDate.now())) {
            return new ResponseEntity<>("Create interview schedule failed", HttpStatus.BAD_REQUEST);
        }

        //Check Nếu Candidate Có lịch phỏng vấn rồi thì không cho tạo nữa
        Candidate candidateDTO = candidateService.getCandidateByEmail(interviewScheduleCreateDTO.getCandidateEmail());
        if (candidateDTO.getCandidateStatus().equals(CandidateStatus.Banned) || candidateDTO.getCandidateStatus().equals(CandidateStatus.PassInterview)) {
            return new ResponseEntity<>("This candidate has interview schedule!", HttpStatus.BAD_REQUEST);
        }


        //Lấy tất cả Assignee rồi set lại ( interviewer )
        List<String> assigneeAccountList = Arrays.stream(interviewScheduleCreateDTO.getAssigneeAccount().split(",")).collect(Collectors.toList());
        List<User> assigneeList = new ArrayList<>();
        assigneeAccountList.forEach(s -> assigneeList.add(userService.findByUserName(s)));

//      //Check assignee đã có lịch phỏng vấn trong thời gian tạo chưa
        for (User assignee : assigneeList) {
            List<InterviewSchedule> assigneeInterviewSchedule = interviewService.getInterviewScheduleByAssignee(assignee);
            for (InterviewSchedule i : assigneeInterviewSchedule) {
                if (i.getScheduleDate().equals(interviewScheduleCreateDTO.getScheduleDate()) && i.getScheduleFrom().isBefore(interviewScheduleCreateDTO.getScheduleTo())) {
                    return new ResponseEntity<>("This interviewer has interview schedule in this time!", HttpStatus.BAD_REQUEST);
                }
            }
        }

        interviewScheduleCreateDTO.setAssigneeList(assigneeList);

        //Set object candidate
        interviewScheduleCreateDTO.setCandidateName(candidateDTO);

        //Set object Recruiter (HR)
        interviewScheduleCreateDTO.setRecruiterOwner(userService.findByUserName(interviewScheduleCreateDTO.getRecruiterOwnerAccount()));

        //Map sang entity

        InterviewSchedule interviewSchedule = new InterviewSchedule();
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.map(interviewScheduleCreateDTO, interviewSchedule);

        interviewSchedule.setActive(true);
        Candidate candidateSave = candidateService.getCandidateByEmail(interviewScheduleCreateDTO.getCandidateEmail());

        candidateSave.setCandidateStatus("Waiting for Interview");
        interviewSchedule.setInterviewScheduleStatus(candidateSave.getCandidateStatus());
        interviewSchedule.setInterviewScheduleResult(Status.Open.toString());

        //Send mail to interview and assignee

        String emailCandidate = interviewScheduleCreateDTO.getCandidateEmail();
        String body = "Invitation letter for interview\n"
                + "Hello " + interviewSchedule.getCandidateName().getFullname() + "!\n"
                + "You have interview schedule\n"
                + "At: " + interviewScheduleCreateDTO.getLocation() + "\n"
                + "Day: " + interviewScheduleCreateDTO.getScheduleDate() + "\n"
                + "From: " + interviewScheduleCreateDTO.getScheduleFrom() + " - To: " + interviewScheduleCreateDTO.getScheduleTo() + "\n"
                + "Meeting Id: " + interviewScheduleCreateDTO.getMeetingId() + "\n"
                + "Please reply this mail before " + LocalDate.now().plusDays(3);
        javaMail.sendEmail(emailCandidate, "[Interview Schedule]", body);

        for (User assignee : assigneeList) {
            String emailInterviewer = assignee.getEmail();
            String bodyInterviewer = "Invitation letter for interview with role is interview\n"
                    + "Hello " + interviewSchedule.getCandidateName().getFullname() + "!\n"
                    + "You have interview schedule\n"
                    + "At: " + interviewScheduleCreateDTO.getLocation() + "\n"
                    + "Day: " + interviewScheduleCreateDTO.getScheduleDate() + "\n"
                    + "From: " + interviewScheduleCreateDTO.getScheduleFrom() + " - To: " + interviewScheduleCreateDTO.getScheduleTo() + "\n"
                    + "Meeting Id: " + interviewScheduleCreateDTO.getMeetingId() + "\n"
                    + "Please reply this mail before " + LocalDate.now().plusDays(3);
            javaMail.sendEmail(emailInterviewer, "[Interview Schedule]", bodyInterviewer);
        }
        //Save Interview
        int status = interviewService.createInterview(interviewSchedule);
        if (status == -1) {
            return new ResponseEntity<>("Create interview schedule failed", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>("Created interview schedule successfully", HttpStatus.OK);
    }

    @PutMapping("/updateInterview")
    public ResponseEntity<Object> updateInterview(@RequestBody InterviewScheduleCreateDTO interviewScheduleCreateDTO) throws IllegalAccessException {

        //Check validate
        // Check Not Null
        if (interviewScheduleCreateDTO.getScheduleTitle() == null || interviewScheduleCreateDTO.getAssigneeAccount() == null
                || interviewScheduleCreateDTO.getScheduleDate() == null
                || interviewScheduleCreateDTO.getScheduleTo() == null || interviewScheduleCreateDTO.getScheduleTo() == null
                || interviewScheduleCreateDTO.getRecruiterOwnerAccount() == null) {
            return new ResponseEntity<>("Create interview schedule failed", HttpStatus.BAD_REQUEST);
        }

        //Check Date
//        if(interviewScheduleCreateDTO.getScheduleDate().isBefore(LocalDate.now())){
//            return new ResponseEntity<>("Create interview schedule failed", HttpStatus.BAD_REQUEST);
//        }

        //Entity được update
        InterviewSchedule interviewSchedule = interviewService.getInterviewScheduleById(interviewScheduleCreateDTO.getScheduleId());

        // xử lý thông tin candidate được gửi lên từ form

        //Lấy tất cả Assignee rồi set lại ( interviewer )
        List<String> assigneeAccountList = Arrays.stream(interviewScheduleCreateDTO.getAssigneeAccount().split(",")).collect(Collectors.toList());
        List<User> assigneeList = new ArrayList<>();
        assigneeAccountList.forEach(s -> assigneeList.add(userService.findByUserName(s)));
        interviewScheduleCreateDTO.setAssigneeList(assigneeList);

        //check xem assignee có sự thay đổi không
        boolean flag = false;
        List<User> assigneeInDataBase = interviewSchedule.getAssignee();
        for (User u : assigneeList) {
            if (assigneeInDataBase.contains(u)) {
                flag = true;
            } else {
                flag = false;
                break;
            }
        }
        //Nếu có thì Check assignee đã có lịch phỏng vấn trong thời gian tạo chưa

        if (!flag) {
            for (User assignee : assigneeList) {
                List<InterviewSchedule> assigneeInterviewSchedule = interviewService.getInterviewScheduleByAssignee(assignee);
                for (InterviewSchedule i : assigneeInterviewSchedule) {
                    if (i.getScheduleDate().equals(interviewScheduleCreateDTO.getScheduleDate()) && i.getScheduleFrom().isBefore(interviewScheduleCreateDTO.getScheduleTo())) {
                        return new ResponseEntity<>("This interviewer has interview schedule in this time!", HttpStatus.BAD_REQUEST);
                    }
                }
            }
        }


        interviewSchedule.getAssignee().clear();

        //Set object candidate
        interviewScheduleCreateDTO.setCandidateName(interviewSchedule.getCandidateName());

        //Set object Recruiter (HR)
        interviewScheduleCreateDTO.setRecruiterOwner(userService.findByUserName(interviewScheduleCreateDTO.getRecruiterOwnerAccount()));

        //Check result của cuộc phỏng vấn rồi đưa sang trạng thái của candidate
        Map<String, String> resultMap = Status.getMapStatus();
        String result = resultMap.get(interviewScheduleCreateDTO.getInterviewScheduleResult());

        Map<String, String> candidateStatusMap = CandidateStatus.getCandidateStatusHasKeyIsResult();
        interviewScheduleCreateDTO.setInterviewScheduleStatus(candidateStatusMap.get(result));

        interviewScheduleCreateDTO.getCandidateName().setCandidateStatus(candidateStatusMap.get(result));
        //Map sang entity


        ModelMapper modelMapper = new ModelMapper();
        modelMapper.map(interviewScheduleCreateDTO, interviewSchedule);

        int status = interviewService.updateInterview(interviewSchedule);
        if (status == -1) {
            return new ResponseEntity<>("Update candidate failed", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>("Update candidate successfully", HttpStatus.OK);
    }

    @GetMapping("/interviewList")
    public String viewListCandidate(Model model) throws IllegalAccessException {

        //Check nếu là hôm nau va result cua candidate là waitting for interview thì result của inter view sẽ là interviewing
        for (Candidate candidate : candidateService.findByCandidateStatus(CandidateStatus.WaitingInterview)) {
            interviewService.updateInterviewScheduleResultByScheduleDate("Interviewing", LocalDate.now(), candidate);
        }


        List<InterviewScheduleListDTO> interviewScheduleListDTOList = new ArrayList<>();

        List<InterviewSchedule> interviewScheduleList = interviewService.getAllActive();

        for (InterviewSchedule i : interviewScheduleList) {
            InterviewScheduleListDTO iDTO = new InterviewScheduleListDTO();
            iDTO.setScheduleId(i.getScheduleId());
            iDTO.setScheduleTitle(i.getScheduleTitle());
            iDTO.setCandidateName(i.getCandidateName().getFullname());

            List<String> assigneeList = new ArrayList<>();
            i.getAssignee().forEach(assignee -> assigneeList.add(assignee.getUserName()));
            iDTO.setAssignee(assigneeList);
            iDTO.setScheduleDate(i.getScheduleDate());
            iDTO.setScheduleFrom(i.getScheduleFrom());
            iDTO.setScheduleTo(i.getScheduleTo());
            iDTO.setInterviewScheduleResult(i.getInterviewScheduleResult());
            iDTO.setInterviewScheduleStatus(i.getInterviewScheduleStatus());
            interviewScheduleListDTOList.add(iDTO);
        }
        model.addAttribute("interviewScheduleListDTOList", interviewScheduleListDTOList);

        List<User> assigneeList = userService.findByRole(Role.Interviewer.toString());
        if (assigneeList.isEmpty()) {
            User user = new User();
            user.setUserName("Does not have data");
            assigneeList.add(user);
        }
        model.addAttribute("assigneeList", assigneeList);

        List<Candidate> candidateList = candidateService.getAll();
        model.addAttribute("candidateList", candidateList);

        List<User> recruiterList = userService.findByRole(Role.Recruiter.toString());
        model.addAttribute("recruiterList", recruiterList);

        List<String> resultList = Status.getListStatus();
        model.addAttribute("resultList", resultList);
        return "pages/interview/interview-list";
    }

    @GetMapping("/{id}")
    public ResponseEntity<InterviewScheduleCreateDTO> getInterview(@PathVariable Integer id) {
        InterviewSchedule interviewSchedule = interviewService.getInterviewScheduleById(id);

        InterviewScheduleCreateDTO interviewScheduleCreateDTO = new InterviewScheduleCreateDTO();

        //Lấy tất cả Assignee rồi set lại ( interviewer )
        StringBuffer assigneeList = new StringBuffer();
        interviewSchedule.getAssignee().forEach(assignee -> assigneeList.append(assignee.getUserName() + ","));
        interviewScheduleCreateDTO.setAssigneeAccount(assigneeList.toString());
        //Set object candidate
        interviewScheduleCreateDTO.setCandidateEmail(interviewSchedule.getCandidateName().getEmail());

        //Set object Recruiter (HR)
        interviewScheduleCreateDTO.setRecruiterOwnerAccount(interviewSchedule.getRecruiterOwner().getUserName());

        //Map sang entity
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.map(interviewSchedule, interviewScheduleCreateDTO);

//

        if (interviewSchedule == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().body(interviewScheduleCreateDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Integer> deleteInterview(@PathVariable Integer id) {
        InterviewSchedule interviewSchedule = interviewService.getInterviewScheduleById(id);
        interviewSchedule.setActive(false);
        int deleteStatus = interviewService.updateInterview(interviewSchedule);
        if (deleteStatus == -1) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok().body(deleteStatus);
    }
}
