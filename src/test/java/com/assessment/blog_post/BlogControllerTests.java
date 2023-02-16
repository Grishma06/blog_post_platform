package com.assessment.blog_post;


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
import java.util.Random;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(BlogController.class)
public class BlogControllerTests {
    Blog blog = new Blog("First", "This is original");
    Blog newBlog = new Blog("Second", "Hello world");

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private BlogService service;
    @MockBean
    private IdGenerator idGenerator;
    private List<Blog> blogs;

    @BeforeEach
    private void setUp() {
        blogs = Arrays.asList(blog, newBlog);
        int id = new Random().nextInt(1000);
        when(idGenerator.generateId()).thenReturn(id);
        blog.setBlogId(id);

        id = new Random().nextInt(1000);
        when(idGenerator.generateId()).thenReturn(id);
        newBlog.setBlogId(id);
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
    public void shouldBeAbleToAddItemInCart() throws Exception {
        Blog fashionBlog = new Blog("Fashion", "fashionBlog,pants");
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
    public void shouldBeAbleToDeleteItemFromCart() throws Exception {

        when(service.findByBlogId(blog.getBlogId()))
                .thenReturn(blog);

        mockMvc.perform(delete("/library/remove-blog/" + blog.getBlogId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        verify(service).removeBlog(blog.getBlogId());
    }

    @Test
    public void shouldNotBeAbleToDeleteItemFromCart() throws Exception {

        when(service.findByBlogId(20))
                .thenReturn(null);

        mockMvc.perform(delete("/library/remove-blog/20")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

}
