/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package net.codejava.entities;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @author thangphan
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "CandidatePositions")
public class CandidatePosition  {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long positionId;
    @Column
    private String positionTitle;
// candidate offer positions vs candidate
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "candidatePosition")
    private List<Candidate> candidates;

// candidateOfferPosition  vs Offer
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "candidatePosition")
    private List<Offer> offers;

}
