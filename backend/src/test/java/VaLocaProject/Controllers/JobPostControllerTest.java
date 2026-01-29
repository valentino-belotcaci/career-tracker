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

    // Initialize mocks
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);  
    }

    @Test
    void testGetAllPosts() {
        // Mock data
        JobPost post1 = new JobPost();
        post1.setPostId(1L);
        post1.setName("Software Engineer");

        JobPost post2 = new JobPost();
        post2.setPostId(2L);
        post2.setName("Data Scientist");

        List<JobPost> mockPosts = Arrays.asList(post1, post2);

        // To make the service return our mock data
        when(jobPostService.getAllPosts()).thenReturn(mockPosts);

        // Act
        ResponseEntity<List<JobPost>> response = jobPostController.getAllPosts();

        // Assert
        assertEquals(200, response.getStatusCode().value());
        assertEquals(2, response.getBody().size());
        assertEquals("Software Engineer", response.getBody().get(0).getName());
        assertEquals("Data Scientist", response.getBody().get(1).getName());
    }


    @Test
    void testInsertPost() {

        JobPost newPost = new JobPost();
        newPost.setPostId(1L);
        newPost.setName("Backend Developer");

        when(jobPostService.insertPost(newPost)).thenReturn(newPost);

        ResponseEntity<JobPost> response = jobPostController.insertPost(newPost);
        

        assertEquals(200, response.getStatusCode().value());
        assertEquals("Backend Developer", response.getBody().getName());

        // Verify the service method was called once
        // This check should only be done on methods that return void 
        // or we need to check if multiple call might happen and we don't notice (like here the insert)
        // (We use it in every non-GET method test))
        verify(jobPostService, times(1)).insertPost(newPost);
    }

    @Test 
    void testDeletePost() {
        Long postId = 1L;

        ResponseEntity<String> response = jobPostController.deletePost(postId);

        assertEquals(200, response.getStatusCode().value());
        assertEquals("The post has been deleted", response.getBody());

        verify(jobPostService, times(1)).deletePost(postId);
    }


    @Test 
    void testDeleteAllPosts() {
        ResponseEntity<String> response = jobPostController.deleteAllPosts();

        assertEquals(200, response.getStatusCode().value());
        assertEquals("All posts have been deleted", response.getBody());

        verify(jobPostService, times(1)).deleteAllPosts();
    }

    @Test
    void testGetPostsByCompanyId() {
        Long companyId = 100L;
        JobPost post1 = new JobPost();
        post1.setPostId(1L);
        post1.setName("CIAOO");
        post1.setCompanyId(companyId);

        JobPost post2 = new JobPost();
        post2.setPostId(2L);
        post2.setCompanyId(companyId);
        post2.setDescription("SPINGEREEE");

        List<JobPost> mockPosts = Arrays.asList(post1, post2);

        when(jobPostService.getPostsByCompanyId(100L)).thenReturn(mockPosts);

        ResponseEntity<List<JobPost>> response = jobPostController.getPostsByCompanyId(companyId);

        assertEquals(200, response.getStatusCode().value());
        assertEquals(2, response.getBody().size());
        assertEquals("CIAOO", response.getBody().get(0).getName());
        assertEquals("SPINGEREEE", response.getBody().get(1).getDescription());
        // Here we dont need .longValue() because java is using the Long(object wrapper) for both values
        assertEquals(100L, response.getBody().get(0).getCompanyId());
    }


    @Test
    void testGetPostById(){
        Long postId = 1L;
        JobPost post1 = new JobPost();
        post1.setPostId(postId);
        post1.setName("Tester");

        when(jobPostService.getPostByPostId(postId)).thenReturn(post1);

        ResponseEntity<JobPost> response = jobPostController.getPostByPostId(postId);

        assertEquals(200, response.getStatusCode().value());
        assertEquals("Tester", response.getBody().getName());
    }

    @Test
    void testUpdatePost(){
        Long postId = 1L;
        JobPost updatedPost = new JobPost();
        updatedPost.setPostId(1L);
        updatedPost.setName("Updated Tester");  

        when(jobPostService.updatePost(postId, updatedPost)).thenReturn(updatedPost);   

        ResponseEntity<JobPost> response = jobPostController.updatePost(postId, updatedPost);   

        assertEquals(200, response.getStatusCode().value());
        assertEquals("Updated Tester", response.getBody().getName());

        verify(jobPostService, times(1)).updatePost(postId, updatedPost);
    }
}
