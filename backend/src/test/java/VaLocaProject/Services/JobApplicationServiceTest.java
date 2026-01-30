package VaLocaProject.Services;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;

import VaLocaProject.Models.JobApplication;
import VaLocaProject.Repositories.JobApplicationRepository;
import VaLocaProject.Security.RedisService;

public class JobApplicationServiceTest {
  
    @Mock
    JobApplicationRepository jobApplicationRepository;

    @Mock
    RedisService redisService;

    @InjectMocks
    JobApplicationService jobApplicationService;
    // Initialize mocks
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);  
    }


    @Test
    void testGetAllApplications() {
        JobApplication application1 = new JobApplication();
        application1.setApplicationId(1L);

        JobApplication application2 = new JobApplication();
        application2.setApplicationId(2L);

        List<JobApplication> mockApplications = Arrays.asList(application1, application2);

        when(jobApplicationRepository.findAll()).thenReturn(mockApplications);

        List<JobApplication> response = jobApplicationService.getAllApplications();

        assertEquals(2, response.size());
        assertEquals(1L, response.get(0).getApplicationId());
        assertSame(application2, response.get(1));
    }

    @Test
    void testInsertApplication() {
        JobApplication application1 = new JobApplication();
        application1.setApplicationId(1L);
        application1.setUserId(10L);
        application1.setPostId(20L);
        
        when(jobApplicationRepository.save(application1)).thenReturn(application1);

        JobApplication response = jobApplicationService.insertApplication(application1);

        assertSame(application1, response);
        verify(jobApplicationRepository, times(1)).save(application1);
    }

    @Test
    void testGetApplicationById() {
        JobApplication application1 = new JobApplication();
        application1.setApplicationId(1L);
        application1.setUserId(10L);
        application1.setPostId(20L);
        
        when(jobApplicationRepository.findById(1L)).thenReturn(Optional.of(application1));

        JobApplication response = jobApplicationService.getApplicationById(1L);

        assertSame(application1, response);
        assertEquals(10L, response.getUserId());
        assertEquals(20L, response.getPostId());
        verify(jobApplicationRepository, times(1)).findById(1L);
    }


    @Test
    void testDeleteAllApplications() {
        jobApplicationService.deleteAllApplications();
        verify(jobApplicationRepository, times(1)).deleteAll();
    }


    @Test
    void testGetApplicationsByPostId() { 
        JobApplication application1 = new JobApplication();
        application1.setApplicationId(1L);
        application1.setPostId(10L);

        JobApplication application2 = new JobApplication();
        application2.setApplicationId(2L);
        application2.setPostId(10L);

        List<JobApplication> mockApplications = Arrays.asList(application1, application2);

        when(jobApplicationRepository.findApplicationsByPostId(10L)).thenReturn(mockApplications);

        List<JobApplication> response = jobApplicationService.getApplicationsByPostId(10L);

        assertEquals(2, response.size());
        assertEquals(1L, response.get(0).getApplicationId());
        assertSame(application2, response.get(1));
        
    }

    @Test
    void testGetApplicationByIds() {
        JobApplication application1 = new JobApplication();
        application1.setApplicationId(1L);
        application1.setPostId(10L);
        application1.setUserId(20L);

        when(jobApplicationRepository.findByPostIdAndUserId(10L, 20L))
        .thenReturn(Optional.of(application1));

        JobApplication response = jobApplicationService.getApplicationByIds(10L, 20L);

        assertSame(application1, response);
        assertEquals(1L, response.getApplicationId());
        verify(jobApplicationRepository, times(1))
        .findByPostIdAndUserId(10L, 20L);

    }

    @Test
    void testGetApplicationsByUserId() { 
        JobApplication application1 = new JobApplication();
        application1.setApplicationId(1L);
        application1.setUserId(20L);    

        JobApplication application2 = new JobApplication();
        application2.setApplicationId(1L);
        application2.setUserId(20L);   

        List<JobApplication> mockApplications = Arrays.asList(application1, application2);

        when(jobApplicationService.getApplicationsByUserId(20L)).thenReturn(mockApplications);

        List<JobApplication> response = jobApplicationService.getApplicationsByUserId(20L);
        assertEquals(2, response.size());
        assertEquals(1L, response.get(0).getApplicationId());
        assertSame(application2, response.get(1));
    }

}
