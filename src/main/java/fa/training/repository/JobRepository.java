package fa.training.repository;

import fa.training.model.Job;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface JobRepository extends JpaRepository<Job, Integer> {
    List<Job> findByActiveIsTrue();

    @Modifying
    @Transactional
    @Query("UPDATE Job SET active = FALSE WHERE jobId = :id")
    int deleteByID(int id);
}
