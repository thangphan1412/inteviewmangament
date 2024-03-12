/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package net.codejava.entities;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.sql.Time;
import java.time.LocalDate;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 *
 * @author thangphan
 */
@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "Interviews")
public class Interview {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long interviewID;
    @Column
    private String interviewScheduleTitle;
    @Column
    private LocalDate interviewSchedule;
    @Column(name = "interview_schedule_from")
    private Time interviewScheduleFrom;

    @Column(name = "interview_schedule_to")
    private Time interviewScheduleTo;

    @Column(name = "interview_location")
    private String interviewLocation;

    @Column(name = "interview_meetingid")
    private String interviewMeetingId;

    @Column(name = "interview_note")
    private String interviewNote;

    @Column(name = "interview_result")
    private String interviewResult;

    //(user vs interview  ) many to many => ( interview Mapper)
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "interview")
    @ToString.Exclude
    private List<InterviewMapper> interviewMappers;

    //Job vs Interview
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "interviews_job_id", foreignKey = @ForeignKey(name = "FK_INTERVIEW_JOB"))
    private Job job;


// interview vs candidate
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "interview_candidate_id", foreignKey = @ForeignKey(name = "FK_CANDIDATE_INTERVIEW"))
    private Candidate candidate;

// offer vs interview
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "interview")
    @ToString.Exclude
    private List<Offer> offers;

 // user vs interview
     @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
     @JoinColumn(name = "interview_recruiter_id", foreignKey = @ForeignKey(name = "FK_USER_INTERVIEW"))
     private User user;
}