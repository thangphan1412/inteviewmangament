package fa.training.model;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.math.BigInteger;
import java.time.LocalDate;

@Entity
@Table(name = "offer")
public class Offer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "offer_id")
    private Integer offerId;

    @Column(name = "position_id", nullable = false)
    private String positionId;

    @ManyToOne
    @JoinColumn(name = "approve_by_id", referencedColumnName = "user_id")
    private User approveBy;

    @Column(name = "contract_type", nullable = false)
    private String contractType;

    @Column(name = "level", nullable = false)
    private String level;

    @Column(name = "department", nullable = true)
    private String department;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "due_date", nullable = true)
    private LocalDate dueDate;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "contract_start", nullable = false)
    private LocalDate contractStart;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "contract_end", nullable = false)
    private LocalDate contractEnd;

    @Column(name = "basic_salary", nullable = false)
    private BigInteger basicSalary;

    @Column(name = "note", nullable = true)
    private String note;

    @Column(name = "active", nullable = false)
    private Boolean active;

    @ManyToOne
    @JoinColumn(name = "recruiter_owner", referencedColumnName = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "candidate_id", referencedColumnName = "candidate_id")
    private Candidate candidate;

//	@ManyToOne
//	@JoinColumn(name = "job_id", referencedColumnName = "job_id")
//	private Job job;

    public Integer getOfferId() {
        return offerId;
    }

    public void setOfferId(Integer offerId) {
        this.offerId = offerId;
    }

    public String getPositionId() {
        return positionId;
    }

    public void setPositionId(String positionId) {
        this.positionId = positionId;
    }

    public User getApproveBy() {
        return approveBy;
    }

    public void setApproveBy(User approveBy) {
        this.approveBy = approveBy;
    }

    public String getContractType() {
        return contractType;
    }

    public void setContractType(String contractType) {
        this.contractType = contractType;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public LocalDate getContractStart() {
        return contractStart;
    }

    public void setContractStart(LocalDate contractStart) {
        this.contractStart = contractStart;
    }

    public LocalDate getContractEnd() {
        return contractEnd;
    }

    public void setContractEnd(LocalDate contractEnd) {
        this.contractEnd = contractEnd;
    }

    public BigInteger getBasicSalary() {
        return basicSalary;
    }

    public void setBasicSalary(BigInteger basicSalary) {
        this.basicSalary = basicSalary;
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

    public Candidate getCandidate() {
        return candidate;
    }

    public void setCandidate(Candidate candidate) {
        this.candidate = candidate;
    }

//	public Job getJob() {
//		return job;
//	}
//
//	public void setJob(Job job) {
//		this.job = job;
//	}

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

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }
}
