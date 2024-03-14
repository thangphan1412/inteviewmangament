package fa.training.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "candidate", schema = "dbo")
public class Candidate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "candidate_id")
    private Integer candidateId;

    @Column(name = "full_name", nullable = false)
    private String fullname;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "gender", nullable = false)
    private Boolean gender;

    @Column(name = "phone_no", nullable = false)
    private String phoneNo;

    @Column(name = "address", nullable = false)
    private String address;

    @Column(name = "dob", nullable = false)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate dob;

    @Column(name = "candidate_status", nullable = false)
    private String candidateStatus;

    @Column(name = " cv_attachment", nullable = false)
    private String cvAttachment;

    @Column(name = "position", nullable = false)
    private String postion;

    @Column(name = "skikls", nullable = false)
    private String skills;

    @Column(name = "year_of_experience", nullable = false)
    private String yearOfExperience;

    @Column(name = "highest_level", nullable = false)
    private String highest_level;

    @Column(name = "note", nullable = false)
    private String note;

    @Column(name = "active", nullable = false)
    private Boolean active;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "recruiter", referencedColumnName = "user_id")
    private User user;

    @OneToMany(mappedBy = "candidate")
    @JsonIgnore
    private List<Job> jobs;

    @OneToMany(mappedBy = "candidate")
    @JsonIgnore
    private List<Offer> offer;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "candidateName")
    private List<InterviewSchedule> interviewScheduleList;


    public Integer getCandidateId() {
        return candidateId;
    }

    public void setCandidateId(Integer candidateId) {
        this.candidateId = candidateId;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Boolean getGender() {
        return gender;
    }

    public void setGender(Boolean gender) {
        this.gender = gender;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public LocalDate getDob() {
        return dob;
    }

    public void setDob(LocalDate dob) {
        this.dob = dob;
    }

    public String getCandidateStatus() {
        return candidateStatus;
    }

    public void setCandidateStatus(String candidateStatus) {
        this.candidateStatus = candidateStatus;
    }

    public String getCvAttachment() {
        return cvAttachment;
    }

    public void setCvAttachment(String cvAttachment) {
        this.cvAttachment = cvAttachment;
    }

    public String getPostion() {
        return postion;
    }

    public void setPosition(String position) {
        this.postion = position;
    }

    public String getSkills() {
        return skills;
    }

    public void setSkills(String skills) {
        this.skills = skills;
    }

    public String getYearOfExperience() {
        return yearOfExperience;
    }

    public void setYearOfExperience(String yearOfExperience) {
        this.yearOfExperience = yearOfExperience;
    }

    public String getHighest_level() {
        return highest_level;
    }

    public void setHighest_level(String highest_level) {
        this.highest_level = highest_level;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Job> getJobs() {
        return jobs;
    }

    public void setJobs(List<Job> jobs) {
        this.jobs = jobs;
    }

    public List<Offer> getOffer() {
        return offer;
    }

    public void setOffer(List<Offer> offer) {
        this.offer = offer;
    }
}
