package fa.training.service;

import fa.training.model.Job;

import java.util.List;

public interface JobService {
    int createJob(Job job);

    int updateJob(Job job);

    List<Job> getAll();

    Job getJobById(int id);

    int deleteJob(int id);
}
