package fa.training.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@ToString
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private int userId;

    @Column(name = "full_name", nullable = false)
    private String fullName;

    @Column(name = "user_name", nullable = false)
    private String userName;

    @Column(name = "password", nullable = true)
    private String password;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "phone_no", nullable = false)
    private String phoneNo;

    @Column(name = "dob", nullable = false)
    private LocalDate dob;

    @Column(name = "address", nullable = false)
    private String address;

    @Column(name = "gender", nullable = false)
    private Boolean gender;

    @Column(name = "role", nullable = false)
    private String role;

    @Column(name = "department", nullable = true)
    private String department;


    @Column(name = "note", nullable = false)
    private String note;

    @Column(name = "active", nullable = false)
    private Boolean active;

    @OneToMany(mappedBy = "user")
    @JsonIgnore
    private List<Offer> offers;

    @OneToMany(mappedBy = "approveBy")
    @JsonIgnore
    private List<Offer> offersApprove;

    @OneToMany(mappedBy = "user")
    @JsonIgnore
    private List<Candidate> candidate;

    @ManyToMany(mappedBy = "assignee")
    @JsonIgnore
    private List<InterviewSchedule> interviewSchedules;


    @OneToMany(mappedBy = "recruiterOwner")
    @JsonIgnore
    private List<InterviewSchedule> RecruiterScheduleList;

    @Column(name = "reset_password_token")
    private String resetPasswordToken;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Offer> getOffersApprove() {
        return offersApprove;
    }

    public void setOffersApprove(List<Offer> offersApprove) {
        this.offersApprove = offersApprove;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public LocalDate getDob() {
        return dob;
    }

    public void setDob(LocalDate dob) {
        this.dob = dob;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Boolean getGender() {
        return gender;
    }

    public void setGender(Boolean gender) {
        this.gender = gender;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
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

    public List<Offer> getOffers() {
        return offers;
    }

    public void setOffers(List<Offer> offers) {
        this.offers = offers;
    }

    public List<Candidate> getCandidate() {
        return candidate;
    }

    public void setCandidate(List<Candidate> candidate) {
        this.candidate = candidate;
    }

    public List<InterviewSchedule> getInterviewSchedules() {
        return interviewSchedules;
    }

    public void setInterviewSchedules(List<InterviewSchedule> interviewSchedules) {
        this.interviewSchedules = interviewSchedules;
    }

    public String getResetPasswordToken() {
        return resetPasswordToken;
    }

    public void setResetPasswordToken(String resetPasswordToken) {
        this.resetPasswordToken = resetPasswordToken;
    }

    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", fullName='" + fullName + '\'' +
                ", userName='" + userName + '\'' +
                ", email='" + email + '\'' +
                ", phoneNo='" + phoneNo + '\'' +
                ", dob=" + dob +
                ", address='" + address + '\'' +
                ", gender=" + gender +
                ", role='" + role + '\'' +
                ", department='" + department + '\'' +
                ", note='" + note + '\'' +
                ", active=" + active +
                ", offers=" + offers +
                ", candidate=" + candidate +
                ", interviewSchedules=" + interviewSchedules +
                '}';
    }
}
