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
import java.time.LocalDate;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.Nationalized;

/**
 *
 * @author thangphan
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@ToString
@Table(name = "Candidate")
public class Candidate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long candidateId;

    @Nationalized
    @Column(name = "candidate_fullname", length = 100)
    private String candidateFullName;

    @Column(name = "candidate_email", length = 100)
    private String candidateEmail;

    @Column(name = "candidate_year_of_experience")
    private Long candidateYearOfExperience;

    @Column(name = "candidate_phonenumber", length = 20)
    private String candidatePhonenumber;

    @Column(name = "candidate_dob")
    private LocalDate candidateDob;

    @Nationalized
    @Column(name = "candidate_cv_attachment", length = 100)
    private String candidateCvAttachment;

    @Column(name = "candidate_address")
    private String candidateAddress;

    @Column(name = "candidate_gender")
    private String candidateGender;

    @Column(name = "candidate_note")
    private String candidateNote;

    @Column(name = "candidate_status")
    private String candidateStatus;

    @Column(name = "candidate_highest_level")
    private String candidateHighestLevel;

    /// mapping recuiter
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "candidate_recruiter_id", foreignKey = @ForeignKey(name = "FK_CANDIDATE_USER_RECRUITER"))
    private User user;

    // mapping owner
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "candidate_owner_hr_id", foreignKey = @ForeignKey(name = "FK_CANDIDATE_USER_OWNER"))
    private User users;

// candidate vs candidate skill mapper
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "candidate")
    private List<CandidateSkillMapper> candidateSkillMappers;

// candidate vs interview
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "candidate")
    private List<Interview> interviews;

    // candidate vs candidate offer position
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "candidate_position_id", foreignKey = @ForeignKey(name = "FK_CANDIDATE_POSITION"))
    private CandidatePosition candidatePosition;

// candidate vs offer
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "candidate")
    private List<Offer> offers;
}
