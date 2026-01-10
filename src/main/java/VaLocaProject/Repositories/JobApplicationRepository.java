package VaLocaProject.Repositories;

import VaLocaProject.Models.JobApplication;

import org.springframework.data.jpa.repository.JpaRepository;


public interface  JobApplicationRepository extends JpaRepository<JobApplication, Long>{
    
}
