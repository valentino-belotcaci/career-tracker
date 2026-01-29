package VaLocaProject.Services;


import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;

import VaLocaProject.Models.JobPost;
import VaLocaProject.Repositories.JobPostRepository;

public class JobPostServiceTest {

    @Mock
    private JobPostRepository jobPostRepository;

    @InjectMocks
    private JobPostService jobPostService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);  // Initialize mocks
    }

    @Test
    void testGetAllPosts() {
        JobPost post1 = new JobPost();
        post1.setPostId(1L);
        post1.setName("Cacca");

        JobPost post2 = new JobPost();
        post1.setPostId(2L);
        post1.setName("Pipi");

        List<JobPost> jobPosts = Arrays.asList(post1, post2);

        when(jobPostRepository.findAll()).thenReturn(jobPosts);

        List<JobPost> response = jobPostService.getAllPosts();


        assertEquals(2, response.size());
        assertEquals("Cacca", response.get(0).getName());
    }

    @Test
    void testInsertPost() {
        JobPost post1 = new JobPost();
        post1.setPostId(1L);
        post1.setCompanyId(100L);
        post1.setName("job 1");
        
    }
}   
