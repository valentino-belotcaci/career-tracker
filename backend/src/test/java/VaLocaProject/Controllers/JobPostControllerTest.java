package VaLocaProject.Controllers;

import java.util.Arrays;
import java.util.List;
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
        UUID id1 = UUID.fromString("00000000-0000-0000-0000-000000000001");
        JobPost post1 = new JobPost();
        post1.setPostId(id1);
        post1.setName("Software Engineer");

        UUID id2 = UUID.fromString("00000000-0000-0000-0000-000000000002");
        JobPost post2 = new JobPost();
        post2.setPostId(id2);
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

        UUID id1 = UUID.fromString("00000000-0000-0000-0000-000000000001");
        JobPost newPost = new JobPost();
        newPost.setPostId(id1);
        newPost.setName("Backend Developer");

        when(jobPostService.insertPost(newPost)).thenReturn(newPost);

        ResponseEntity<JobPost> response = jobPostController.insertPost(newPost);
        

        assertEquals(200, response.getStatusCode().value());
        assertEquals("Backend Developer", response.getBody().getName());
        assertEquals(id1, response.getBody().getPostId());

        // Verify the service method was called once
        // This check should only be done on methods that return void 
        // or we need to check if multiple call might happen and we don't notice (like here the insert)
        // (We use it in every non-GET method test))
        verify(jobPostService, times(1)).insertPost(newPost);
    }

    @Test 
    void testDeletePost() {
        UUID postId = UUID.fromString("00000000-0000-0000-0000-000000000001");

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
        UUID companyId = UUID.fromString("00000000-0000-0000-0000-000000000100");
        UUID postId1 = UUID.fromString("00000000-0000-0000-0000-000000000001");
        UUID postId2 = UUID.fromString("00000000-0000-0000-0000-000000000002");

        JobPost post1 = new JobPost();
        post1.setPostId(postId1);
        post1.setName("CIAOO");
        post1.setCompanyId(companyId);

        JobPost post2 = new JobPost();
        post2.setPostId(postId2);
        post2.setCompanyId(companyId);
        post2.setDescription("SPINGEREEE");

        List<JobPost> mockPosts = Arrays.asList(post1, post2);

        when(jobPostService.getPostsByCompanyId(companyId)).thenReturn(mockPosts);

        ResponseEntity<List<JobPost>> response = jobPostController.getPostsByCompanyId(companyId);

        assertEquals(200, response.getStatusCode().value());
        assertEquals(2, response.getBody().size());
        assertEquals("CIAOO", response.getBody().get(0).getName());
        assertEquals("SPINGEREEE", response.getBody().get(1).getDescription());
        assertEquals(post2.getPostId(), response.getBody().get(1).getPostId());
        // Here we dont need .longValue() because java is using the Long(object wrapper) for both values
        assertEquals(companyId, response.getBody().get(0).getCompanyId());
    }


    @Test
    void testGetPostById(){
        UUID postId = UUID.fromString("00000000-0000-0000-0000-000000000001");
        JobPost post1 = new JobPost();
        post1.setPostId(postId);
        post1.setName("Tester");

        when(jobPostService.getPostByPostId(postId)).thenReturn(post1);

        ResponseEntity<JobPost> response = jobPostController.getPostByPostId(postId);

        assertEquals(200, response.getStatusCode().value());
        assertEquals("Tester", response.getBody().getName());
        assertEquals(postId, response.getBody().getPostId());
    }

    @Test
    void testUpdatePost(){
        UUID postId = UUID.fromString("00000000-0000-0000-0000-000000000001");
        JobPost updatedPost = new JobPost();
        updatedPost.setPostId(postId);
        updatedPost.setName("Updated Tester");  

        when(jobPostService.updatePost(postId, updatedPost)).thenReturn(updatedPost);   

        ResponseEntity<JobPost> response = jobPostController.updatePost(postId, updatedPost);   

        assertEquals(200, response.getStatusCode().value());
        assertEquals("Updated Tester", response.getBody().getName());
        assertEquals(postId, response.getBody().getPostId());

        verify(jobPostService, times(1)).updatePost(postId, updatedPost);
    }
}
