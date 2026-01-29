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
        post2.setPostId(2L);
        post2.setName("Pipi");

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
        

        when(jobPostRepository.save(post1)).thenReturn(post1);
        JobPost response = jobPostService.insertPost(post1);
        
        // .longValue() is necessary to compare longs(primitive), 
        // without it it would return a Long(object wrapper)
        assertEquals(100L, response.getCompanyId().longValue());
    }


    @Test
    public void testGetPostsByCompanyId() {
        JobPost post1 = new JobPost();
        post1.setPostId(1L);
        post1.setCompanyId(100L);

        JobPost post2 = new JobPost();
        post2.setPostId(2L);
        post2.setName("job 2");
        post2.setCompanyId(100L);

        when(jobPostRepository.findByCompanyId(100L)).thenReturn(Arrays.asList(post1, post2));

        
        List<JobPost> response = jobPostService.getPostsByCompanyId(100L);

        assertEquals(2, response.size());
        assertEquals("job 2", response.get(1).getName());
        
    
    }
}
