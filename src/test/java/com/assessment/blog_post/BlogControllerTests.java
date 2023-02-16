package com.assessment.blog_post;


import com.assessment.blog_post.exception.AlreadyExistException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(BlogController.class)
public class BlogControllerTests {
    Blog blog = new Blog("First", "This is original", 12);
    Blog newBlog = new Blog("Second", "Hello world", 11);

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private BlogService service;
    private List<Blog> blogs;

    @BeforeEach
    private void setUp() {
        blogs = Arrays.asList(blog, newBlog);
    }

    @Test
    public void shouldDisplayListOfAllItemsInShoppingCart() throws Exception {
        System.out.println(blog.getBlogId());
        System.out.println(newBlog.getBlogId());

        when(service.viewBlogs())
                .thenReturn(blogs);

        mockMvc.perform(get("/library/view-blogs"))
                .andExpect(content().json(objectMapper.writeValueAsString(blogs)))
                .andExpect(status().isOk());

        verify(service).viewBlogs();

    }

    @Test
    public void shouldBeAbleToSaveBlog() throws Exception {
        Blog fashionBlog = new Blog("Fashion", "fashionBlog,pants", 13);
        when(service.addBlogs(fashionBlog))
                .thenReturn(fashionBlog);

        MockHttpServletRequestBuilder mock = post("/library/add-blogs")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(fashionBlog));

        mockMvc.perform(mock)
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(fashionBlog)));

        verify(service).addBlogs(fashionBlog);
    }
    @Test
    public void shouldNotBeAbleToAddBlog() throws Exception{
        Blog fashionBlog = new Blog("Fashion", "fashionBlog,pants", 13);
        when(service.addBlogs(fashionBlog))
                .thenThrow(AlreadyExistException.class);

        MockHttpServletRequestBuilder mock = post("/library/add-blogs")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(fashionBlog));

        mockMvc.perform(mock)
                .andExpect(status().isNotAcceptable());

        verify(service).addBlogs(fashionBlog);
    }

    @Test
    public void shouldBeAbleToDeleteBlog() throws Exception {

        when(service.findByBlogId(blog.getBlogId()))
                .thenReturn(blog);

        mockMvc.perform(delete("/library/remove-blog/" + blog.getBlogId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        verify(service).removeBlog(blog.getBlogId());
    }

}
