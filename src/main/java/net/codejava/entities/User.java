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
import java.util.ArrayList;
import java.util.List;


import lombok.Getter;

import lombok.ToString;
//import org.springframework.security.core.GrantedAuthority;

/**
 *
 * @author thangphan
 */
@Entity
@Table(name = "Users")
@Getter
public class User
//        extends org.springframework.security.core.userdetails.User
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @Column(name = "user_full_name")
    private String userFullName;

    @Column(name = "user_dob")
    private LocalDate userDob;

    @Column(name = "user_phone_number")
    private String userPhoneNumber;

    @Column(name = "user_address")
    private String userAddress;

    @Column(name = "user_email")
    private String userEmail;

    @Column(name = "user_gender")
    private String userGender;

    @Column(name = "user_note")
    private String userNote;

    @Column(name = "user_status")
    private String userStatus;

    // mapping user role
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "roleId", foreignKey = @ForeignKey(name = "FK_USER_ROLE"))
    private UserRole userRole;

    // mapping user offer department
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "departmentId", foreignKey = @ForeignKey(name = "FK_USER_OFFER_DEPARTMENT"))
    private UserOfferDepartment userOfferDepartment;

    //mapping interview mapper
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
    @ToString.Exclude
    private List<InterviewMapper> interviewMappers;

    // mapping candidate recuiter
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
    private List<Candidate> candidates;

    // mapping candidate owner
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "users")
    private List<Candidate> candidate;

    // mapping  offerRecruiter
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "recruiter")
    private List<Offer> offer;

    // mapping offer approver
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "approver")
    private List<Offer> offers;

    // mapping interview
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
    private List<Interview> interviews;

//    public User(String username, String password, Collection<? extends GrantedAuthority> authorities, Long userId, String userFullName, LocalDate userDob, String userPhoneNumber, String userAddress, String userEmail, String userGender, String userNote, String userStatus, UserRole userRole, UserOfferDepartment userOfferDepartment, List<InterviewMapper> interviewMappers, List<Candidate> candidates, List<Candidate> candidate, List<Offer> offer, List<Offer> offers, List<Interview> interviews) {
//        super(username, password, authorities);
//        this.userId = userId;
//        this.userFullName = userFullName;
//        this.userDob = userDob;
//        this.userPhoneNumber = userPhoneNumber;
//        this.userAddress = userAddress;
//        this.userEmail = userEmail;
//        this.userGender = userGender;
//        this.userNote = userNote;
//        this.userStatus = userStatus;
//        this.userRole = userRole;
//        this.userOfferDepartment = userOfferDepartment;
//        this.interviewMappers = interviewMappers;
//        this.candidates = candidates;
//        this.candidate = candidate;
//        this.offer = offer;
//        this.offers = offers;
//        this.interviews = interviews;
//    }

//    public User() {
//        super("abc", "abc", new ArrayList<>());
//    }

//    public User(String username, String password, List<GrantedAuthority> grantedAuthorities) {
//        super(username, password, grantedAuthorities);
//    }
}
