package fa.training.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
import java.beans.Transient;
import java.time.LocalDate;
import java.util.List;

@ToString
@Entity
@Table(name = "users")
@Data
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
