package VaLocaProject.Controllers;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import VaLocaProject.Models.JobPost;
import VaLocaProject.Services.JobPostService;

public class JobPostControllerTest {
    
    @Mock
    private JobPostService jobPostService;

    @InjectMocks
    private JobPostController jobPostController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);  // Initialize mocks
    }

    @Test
    void testGetAllPosts() {
        // Arrange
        JobPost post1 = new JobPost();
        post1.setPostId(1L);
        post1.setName("Software Engineer");

        JobPost post2 = new JobPost();
        post2.setPostId(2L);
        post2.setName("Data Scientist");

        List<JobPost> mockPosts = Arrays.asList(post1, post2);

        when(jobPostService.getAllPosts()).thenReturn(mockPosts);

        // Act
        ResponseEntity<List<JobPost>> response = jobPostController.getAllPosts();

        // Assert
        assertEquals(200, response.getStatusCode().value());
        assertEquals(2, response.getBody().size());
        assertEquals("Software Engineer", response.getBody().get(0).getName());

        // Verify the service method was called once
        verify(jobPostService, times(1)).getAllPosts();
    }
}
