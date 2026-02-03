package VaLocaProject.Services;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

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

public class JobApplicationServiceTest {
  
    @Mock
    JobApplicationRepository jobApplicationRepository;


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
        UUID id1 = UUID.fromString("00000000-0000-0000-0000-000000000001");
        UUID id2 = UUID.fromString("00000000-0000-0000-0000-000000000002");
        application1.setApplicationId(id1);

        JobApplication application2 = new JobApplication();
        application2.setApplicationId(id2);

        List<JobApplication> mockApplications = Arrays.asList(application1, application2);

        when(jobApplicationRepository.findAll()).thenReturn(mockApplications);

        List<JobApplication> response = jobApplicationService.getAllApplications();

        assertEquals(2, response.size());
        assertEquals(id1, response.get(0).getApplicationId());
        assertSame(application2, response.get(1));
    }

    @Test
    void testInsertApplication() {
        UUID id1 = UUID.fromString("00000000-0000-0000-0000-000000000001");
        UUID userId = UUID.fromString("00000000-0000-0000-0000-000000000010");
        UUID postId = UUID.fromString("00000000-0000-0000-0000-000000000100");

        JobApplication application1 = new JobApplication();
        application1.setApplicationId(id1);
        application1.setUserId(userId);
        application1.setPostId(postId);
        
        when(jobApplicationRepository.save(application1)).thenReturn(application1);

        JobApplication response = jobApplicationService.insertApplication(application1);

        assertSame(application1, response);
        verify(jobApplicationRepository, times(1)).save(application1);
    }

    @Test
    void testGetApplicationById() {
        UUID id1 = UUID.fromString("00000000-0000-0000-0000-000000000001");
        UUID userId = UUID.fromString("00000000-0000-0000-0000-000000000010");
        UUID postId = UUID.fromString("00000000-0000-0000-0000-000000000100");

        JobApplication application1 = new JobApplication();
        application1.setApplicationId(id1);
        application1.setUserId(userId);
        application1.setPostId(postId);
        
        when(jobApplicationRepository.findById(id1)).thenReturn(Optional.of(application1));

        JobApplication response = jobApplicationService.getApplicationById(id1);

        assertSame(application1, response);
        assertEquals(userId, response.getUserId());
        assertEquals(postId, response.getPostId());
        verify(jobApplicationRepository, times(1)).findById(id1);
    }


    @Test
    void testDeleteAllApplications() {
        jobApplicationService.deleteAllApplications();
        verify(jobApplicationRepository, times(1)).deleteAll();
    }


    @Test
    void testGetApplicationsByPostId() { 
        UUID id1 = UUID.fromString("00000000-0000-0000-0000-000000000001");
        UUID id2 = UUID.fromString("00000000-0000-0000-0000-000000000002");
        UUID postId = UUID.fromString("00000000-0000-0000-0000-000000000100");
        JobApplication application1 = new JobApplication();
        application1.setApplicationId(id1);
        application1.setPostId(postId);

        JobApplication application2 = new JobApplication();
        application2.setApplicationId(id2);
        application2.setPostId(postId);

        List<JobApplication> mockApplications = Arrays.asList(application1, application2);

        when(jobApplicationRepository.findApplicationsByPostId(postId)).thenReturn(mockApplications);

        List<JobApplication> response = jobApplicationService.getApplicationsByPostId(postId);

        assertEquals(2, response.size());
        assertEquals(id1, response.get(0).getApplicationId());
        assertSame(application2, response.get(1));
        
    }

    @Test
    void testGetApplicationByIds() {
        UUID id1 = UUID.fromString("00000000-0000-0000-0000-000000000001");
        UUID userId = UUID.fromString("00000000-0000-0000-0000-000000000010");
        UUID postId = UUID.fromString("00000000-0000-0000-0000-000000000100");

        JobApplication application1 = new JobApplication();
        application1.setApplicationId(id1);
        application1.setPostId(postId);
        application1.setUserId(userId);

        when(jobApplicationRepository.findByPostIdAndUserId(postId, userId))
        .thenReturn(Optional.of(application1));

        JobApplication response = jobApplicationService.getApplicationByIds(postId, userId);

        assertSame(application1, response);
        assertEquals(id1, response.getApplicationId());
        verify(jobApplicationRepository, times(1))
        .findByPostIdAndUserId(postId, userId);

    }

    @Test
    void testGetApplicationsByUserId() { 
        UUID id1 = UUID.fromString("00000000-0000-0000-0000-000000000001");
        UUID userId = UUID.fromString("00000000-0000-0000-0000-000000000010");

        JobApplication application1 = new JobApplication();
        application1.setApplicationId(id1);
        application1.setUserId(userId);    

        JobApplication application2 = new JobApplication();
        application2.setApplicationId(id1);
        application2.setUserId(userId);   

        List<JobApplication> mockApplications = Arrays.asList(application1, application2);

        when(jobApplicationService.getApplicationsByUserId(userId)).thenReturn(mockApplications);

        List<JobApplication> response = jobApplicationService.getApplicationsByUserId(userId);
        assertEquals(2, response.size());
        assertEquals(id1, response.get(0).getApplicationId());
        assertSame(application2, response.get(1));
    }

}
