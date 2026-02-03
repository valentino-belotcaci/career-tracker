package VaLocaProject.Controllers;

import java.util.Arrays;
import java.util.List;
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
        UUID id1 = UUID.fromString("00000000-0000-0000-0000-000000000001");
        UUID id2 = UUID.fromString("00000000-0000-0000-0000-000000000002");
        JobApplication application1 = new JobApplication();
        application1.setApplicationId(id1);

        JobApplication application2 = new JobApplication();
        application2.setApplicationId(id2);

        List<JobApplication> mockApplications = Arrays.asList(application1, application2);

        when(jobApplicationService.getAllApplications()).thenReturn(mockApplications);

        ResponseEntity<List<JobApplication>> response = jobApplicationController.getAllApplications();

        assertEquals(200, response.getStatusCode().value());
        assertEquals(2, response.getBody().size());
        assertEquals(id1, response.getBody().get(0).getApplicationId());
        assertSame(application2, response.getBody().get(1));
    }

    @Test
    void testInsertApplication() {
        UUID id1 = UUID.fromString("00000000-0000-0000-0000-000000000001");
        JobApplication application1 = new JobApplication();
        application1.setApplicationId(id1);

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
        UUID id1 = UUID.fromString("00000000-0000-0000-0000-000000000001");
        UUID postid = UUID.fromString("00000000-0000-0000-0000-000000000002");
        JobApplication application1 = new JobApplication();
        application1.setApplicationId(id1);
        application1.setPostId(postid);

        UUID id2 = UUID.fromString("00000000-0000-0000-0000-000000000001");

        JobApplication application2 = new JobApplication();
        application2.setApplicationId(id2);
        application2.setPostId(postid);


        List<JobApplication> mockApplications = Arrays.asList(application1, application2);

        when(jobApplicationService.getApplicationsByPostId(postid)).thenReturn(mockApplications);

        ResponseEntity<List<JobApplication>> response = jobApplicationController.getApplicationsByPostId(postid);

        assertEquals(200, response.getStatusCode().value());
        assertEquals(2, response.getBody().size());
        assertEquals(id1, response.getBody().get(0).getApplicationId());
        assertSame(application2, response.getBody().get(1));
    }


    @Test
    void testGetApplicationsByUserId(){
        UUID id1 = UUID.fromString("00000000-0000-0000-0000-000000000001");
        UUID id2 = UUID.fromString("00000000-0000-0000-0000-000000000002");
        UUID userId = UUID.fromString("00000000-0000-0000-0000-000000000020");
        JobApplication application1 = new JobApplication();
        application1.setApplicationId(id1);
        application1.setUserId(userId);

        JobApplication application2 = new JobApplication();
        application2.setApplicationId(id2);
        application2.setUserId(userId);

        List<JobApplication> mockApplications = Arrays.asList(application1, application2);

        when(jobApplicationService.getApplicationsByUserId(userId)).thenReturn(mockApplications);

        ResponseEntity<List<JobApplication>> response = jobApplicationController.getApplicationByUserId(userId);

        assertEquals(200, response.getStatusCode().value());
        assertEquals(2, response.getBody().size());
        assertEquals(id1, response.getBody().get(0).getApplicationId());
        assertSame(application2, response.getBody().get(1));

    }

    @Test
    void testGetApplicationById(){
        JobApplication application1 = new JobApplication();
        UUID id1 = UUID.fromString("00000000-0000-0000-0000-000000000001");
        application1.setApplicationId(id1);  

        when(jobApplicationService.getApplicationById(id1)).thenReturn(application1);    

        ResponseEntity<JobApplication> response = jobApplicationController.getApplicationById(id1);
    
        assertEquals(200, response.getStatusCode().value());
        assertSame(application1, response.getBody());
    }

}
