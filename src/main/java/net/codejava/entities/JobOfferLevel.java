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
import lombok.ToString;

/**
 *
 * @author thangphan
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "JobOfferLevels")
public class JobOfferLevel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long levelId;

    @Column(name = "level_title")
    private String levelTitle;

    //  Level vs MapperLevelJob
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "jobOfferLevel")
    @ToString.Exclude
    private List<JobLevelMapper> jobLevelMappers;

    // offer vs level
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "jobOfferLevel")
    @ToString.Exclude
    private List<Offer> offers;
}
