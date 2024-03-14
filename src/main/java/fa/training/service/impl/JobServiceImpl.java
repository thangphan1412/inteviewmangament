package fa.training.service.impl;

import fa.training.model.Job;
import fa.training.repository.JobRepository;
import fa.training.service.JobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class JobServiceImpl implements JobService {
    @Autowired
    JobRepository jobRepository;


    @Override
    public int createJob(Job job) {
        try {
            Job jobResult = jobRepository.save(job);
        } catch (Exception ex) {
            ex.printStackTrace();
            return -1;
        }
        return 1;
    }

    @Override
    public int updateJob(Job job) {
        try {
            Optional<Job> jobOptional = jobRepository.findById(job.getJobId());
            if (jobOptional.isPresent()) {
                // Cập nhật thông tin của candidate
                Job jobUpdate = jobOptional.get();
                jobUpdate.setTitle(job.getTitle());
                jobUpdate.setStartDate(job.getStartDate());
                jobUpdate.setEndDate(job.getEndDate());
                jobUpdate.setSkill(job.getSkill());
                jobUpdate.setBenefit(job.getBenefit());
                jobUpdate.setSalaryRangeFrom(job.getSalaryRangeFrom());
                jobUpdate.setSalaryRangeTo(job.getSalaryRangeTo());
                jobUpdate.setAddress(job.getAddress());
                jobUpdate.setDescription(job.getDescription());
                jobUpdate.setActive(job.getActive());
//                jobUpdate.setOffers(job.getOffers());
                jobUpdate.setCandidate(job.getCandidate());

                jobRepository.save(job);


            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return -1;
        }
        return 1;
    }

    @Override
    public List<Job> getAll() {
        List<Job> jobList = new ArrayList<>();
        try {
            jobList = jobRepository.findByActiveIsTrue();
            return jobList;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return Collections.emptyList();
    }

    @Override
    public Job getJobById(int id) {
        return jobRepository.findById(id).orElse(null);
    }

    @Override
    public int deleteJob(int id) {
        try {
            jobRepository.deleteByID(id);
            return 1;
        } catch (Exception ex) {
            ex.printStackTrace();
            return -1;
        }
    }
}
