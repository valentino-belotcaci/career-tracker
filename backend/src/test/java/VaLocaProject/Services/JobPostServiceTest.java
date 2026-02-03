package VaLocaProject.Services;


import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
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
        UUID id1 = UUID.fromString("00000000-0000-0000-0000-000000000001");
        UUID id2 = UUID.fromString("00000000-0000-0000-0000-000000000002");
        JobPost post1 = new JobPost();
        post1.setPostId(id1);
        post1.setName("Cacca");

        JobPost post2 = new JobPost();
        post2.setPostId(id2);
        post2.setName("Pipi");

        List<JobPost> jobPosts = Arrays.asList(post1, post2);

        when(jobPostRepository.findAll()).thenReturn(jobPosts);

        List<JobPost> response = jobPostService.getAllPosts();


        assertEquals(2, response.size());
        assertEquals("Cacca", response.get(0).getName());

    }


    @Test
    void testInsertPost() {
        UUID id1 = UUID.fromString("00000000-0000-0000-0000-000000000001");
        UUID companyId = UUID.fromString("00000000-0000-0000-0000-000000000100");
        JobPost post1 = new JobPost();
        post1.setPostId(id1);
        post1.setCompanyId(companyId);
        post1.setName("job 1");
        

        when(jobPostRepository.save(post1)).thenReturn(post1);
        JobPost response = jobPostService.insertPost(post1);
        
        // .longValue() is necessary to compare longs(primitive), 
        // without it it would return a Long(object wrapper)
        assertEquals(companyId, response.getCompanyId());

        verify(jobPostRepository, times(1)).save(post1);
    }


    @Test
    void testGetPostsByCompanyId() {
        UUID id1 = UUID.fromString("00000000-0000-0000-0000-000000000001");
        UUID id2 = UUID.fromString("00000000-0000-0000-0000-000000000002");
        UUID companyId = UUID.fromString("00000000-0000-0000-0000-000000000100");
        JobPost post1 = new JobPost();
        post1.setPostId(id1);
        post1.setCompanyId(companyId);

        JobPost post2 = new JobPost();
        post2.setPostId(id2);
        post2.setName("job 2");
        post2.setCompanyId(companyId);

        when(jobPostRepository.findPostsByCompanyId(companyId)).thenReturn(Arrays.asList(post1, post2));

        
        List<JobPost> response = jobPostService.getPostsByCompanyId(companyId);

        assertEquals(2, response.size());
        assertEquals("job 2", response.get(1).getName());
    }

    @Test
    void testGetPostByPostId() {
        UUID id1 = UUID.fromString("00000000-0000-0000-0000-000000000001");
        JobPost post1 = new JobPost();
        post1.setPostId(id1);
        post1.setName("Lavoro loca");


        when(jobPostRepository.findById(id1)).thenReturn(Optional.of(post1));

        JobPost response = jobPostService.getPostByPostId(id1);

        assertEquals("Lavoro loca", response.getName());
    }

    @Test
    void testDeletePost() {
        UUID postId = UUID.fromString("00000000-0000-0000-0000-000000000001");

        // We don't need to mock return value for deleteById because it returns void
        jobPostService.deletePost(postId);

        // Verify that the repository's deleteById was called once
        verify(jobPostRepository, times(1)).deleteById(postId);
    }


    @Test
    void testDeleteAllPosts() {
        jobPostService.deleteAllPosts();
        verify(jobPostRepository, times(1)).deleteAll();
    }

    @Test
    void testUpdatePost() {
        UUID postId = UUID.fromString("00000000-0000-0000-0000-000000000001");
        UUID companyId1 = UUID.fromString("00000000-0000-0000-0000-000000000100");
        // Existing post in DB
        JobPost existingPost = new JobPost();
        existingPost.setPostId(postId);
        existingPost.setCompanyId(companyId1);
        existingPost.setName("Original Job");

        UUID companyId2 = UUID.fromString("00000000-0000-0000-0000-000000000200");
        // Incoming update
        JobPost update = new JobPost();
        update.setCompanyId(companyId2);
        update.setName("Updated Job");

        // Mock repository
        when(jobPostRepository.findById(postId)).thenReturn(Optional.of(existingPost));

        // Call service (ignore Redis)
        JobPost updatedPost = jobPostService.updatePost(postId, update);

        // Assertions
        assertEquals(companyId2, updatedPost.getCompanyId());
        assertEquals("Updated Job", updatedPost.getName());

        // Verify repository method was called
        verify(jobPostRepository, times(1)).findById(postId);
    }

}
