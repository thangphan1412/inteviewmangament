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
@Table(name = "JobCandidateSkills")
public class JobCandidateSkill {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long skillId;

    @Column(name = "skill_name")
    private String skillName;

// Job Skill Mapper vs Job Candidate Skill
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "jobCandidateSkill")
    private List<JobSkillMapper> jobSkillMappers;

// job candidate skill vs candidate skill mapper
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "jobCandidateSkill")
    private List<CandidateSkillMapper> candidateSkillMappers;
}

