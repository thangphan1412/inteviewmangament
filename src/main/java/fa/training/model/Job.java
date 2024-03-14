package fa.training.model;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "job", schema = "dbo")
public class Job {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "job_id", nullable = false)
    private Integer jobId;

    @Column(name = "job_title", nullable = false)
    private String title;

    @Column(name = "start_date", nullable = false)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate startDate;

    @Column(name = "end_date", nullable = false)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate endDate;

    @Column(name = "job_skill", nullable = false)
    private String skill;

    @Column(name = "job_benefit", nullable = false)
    private String benefit;

    @Column(name = "salary_range_from", nullable = false)
    private int salaryRangeFrom;

    @Column(name = "salary_range_to", nullable = false)
    private int salaryRangeTo;

    @Column(name = "working_address", nullable = false)
    private String address;

    @Column(name = "job_description", nullable = false)
    private String description;

    @Column(name = "job_status", nullable = false)
    private String status;

    @Column(name = "active", nullable = false)
    private Boolean active;

//	@OneToMany(mappedBy = "job")
//	private List<Offer> offers;

    @ManyToOne
    @JoinColumn(name = "candidate_id", referencedColumnName = "candidate_id")
    private Candidate candidate;

    public Integer getJobId() {
        return jobId;
    }

    public void setJobId(Integer jobId) {
        this.jobId = jobId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public String getSkill() {
        return skill;
    }

    public void setSkill(String skill) {
        this.skill = skill;
    }

    public String getBenefit() {
        return benefit;
    }

    public void setBenefit(String benefit) {
        this.benefit = benefit;
    }

    public int getSalaryRangeFrom() {
        return salaryRangeFrom;
    }

    public void setSalaryRangeFrom(int salaryRangeFrom) {
        this.salaryRangeFrom = salaryRangeFrom;
    }

    public int getSalaryRangeTo() {
        return salaryRangeTo;
    }

    public void setSalaryRangeTo(int salaryRangeTo) {
        this.salaryRangeTo = salaryRangeTo;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

//	public List<Offer> getOffers() {
//		return offers;
//	}
//
//	public void setOffers(List<Offer> offers) {
//		this.offers = offers;
//	}

    public Candidate getCandidate() {
        return candidate;
    }

    public void setCandidate(Candidate candidate) {
        this.candidate = candidate;
    }
}
