package VaLocaProject.Controllers;

import java.util.Arrays;
import java.util.List;

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
import org.springframework.http.ResponseEntity;

import VaLocaProject.Models.JobApplication;
import VaLocaProject.Services.JobApplicationService;


public class JobApplicationControllerTest {

    @Mock
    JobApplicationService jobApplicationService;

    @InjectMocks
    JobApplicationController jobApplicationController;

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

        when(jobApplicationService.getAllApplications()).thenReturn(mockApplications);

        ResponseEntity<List<JobApplication>> response = jobApplicationController.getAllApplications();

        assertEquals(200, response.getStatusCode().value());
        assertEquals(2, response.getBody().size());
        assertEquals(1L, response.getBody().get(0).getApplicationId());
        assertSame(application2, response.getBody().get(1));
    }

    @Test
    void testInsertApplication() {
        JobApplication application1 = new JobApplication();
        application1.setApplicationId(1L);

        when(jobApplicationService.insertApplication(application1)).thenReturn(application1);

        ResponseEntity<JobApplication> response = jobApplicationController.insertApplication(application1);

        assertEquals(200, response.getStatusCode().value());
        assertSame(application1, response.getBody());

        verify(jobApplicationService, times(1)).insertApplication(application1);
    }


    @Test
    void testDeleteAllApplications() {
        ResponseEntity<String> response = jobApplicationController.deleteAll();

        assertEquals(200, response.getStatusCode().value());
        assertEquals("All job applications deleted", response.getBody());
        verify(jobApplicationService, times(1)).deleteAllApplications();
    }

    @Test
    void testGetApplicationsByPostId(){
        JobApplication application1 = new JobApplication();
        application1.setApplicationId(1L);
        application1.setPostId(10L);

        JobApplication application2 = new JobApplication();
        application2.setApplicationId(2L);
        application2.setPostId(10L);


        List<JobApplication> mockApplications = Arrays.asList(application1, application2);

        when(jobApplicationService.getApplicationsByPostId(10L)).thenReturn(mockApplications);

        ResponseEntity<List<JobApplication>> response = jobApplicationController.getApplicationsByPostId(10L);

        assertEquals(200, response.getStatusCode().value());
        assertEquals(2, response.getBody().size());
        assertEquals(1L, response.getBody().get(0).getApplicationId());
        assertSame(application2, response.getBody().get(1));
    }


    @Test
    void testGetApplicationsByUserId(){
        JobApplication application1 = new JobApplication();
        application1.setApplicationId(1L);
        application1.setUserId(20L);

        JobApplication application2 = new JobApplication();
        application2.setApplicationId(2L);
        application2.setUserId(20L);

        List<JobApplication> mockApplications = Arrays.asList(application1, application2);

        when(jobApplicationService.getApplicationsByUserId(20L)).thenReturn(mockApplications);

        ResponseEntity<List<JobApplication>> response = jobApplicationController.getApplicationByUserId(20L);

        assertEquals(200, response.getStatusCode().value());
        assertEquals(2, response.getBody().size());
        assertEquals(1L, response.getBody().get(0).getApplicationId());
        assertSame(application2, response.getBody().get(1));

    }

    @Test
    void testGetApplicationById(){
        JobApplication application1 = new JobApplication();
        application1.setApplicationId(1L);  

        when(jobApplicationService.getApplicationById(1L)).thenReturn(application1);    

        ResponseEntity<JobApplication> response = jobApplicationController.getApplicationById(1L);
    
        assertEquals(200, response.getStatusCode().value());
        assertSame(application1, response.getBody());
    }

}
