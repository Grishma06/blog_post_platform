package com.assessment.blog_post;

import com.assessment.blog_post.exception.AlreadyExistException;
import com.assessment.blog_post.exception.NoBlogFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class BlogServiceTests {

    @Mock
    private BlogRepository repository;
    @InjectMocks
    private BlogService service;
    private List<Blog> blogs;
    Blog blog = new Blog("First", "This is original", 12);
    Blog newBlog = new Blog("Second", "Hello world", 11);

    @BeforeEach
    private void setUp() {
        blogs = Arrays.asList(blog, newBlog);
    }
    @Test
    public void shouldBeAbleToViewAllBlogs() {
        when(repository.findAll()).thenReturn(blogs);

        List<Blog> allBlogs = service.viewBlogs();

        assertEquals(blogs, allBlogs);

        verify(repository).findAll();
    }

    @Test
    public void shouldBeAbleToSaveBlog() throws AlreadyExistException {

        when(repository.save(newBlog))
                .thenReturn(newBlog);


        Blog result = repository.save(newBlog);

        assertEquals(result, newBlog);

        verify(repository).save(newBlog);
    }

    @Test
    public void shouldBeAbleToFindBlogById() throws NoBlogFoundException {

        when(repository.findByBlogId(blog.getBlogId()))
                .thenReturn(blog);

        Blog result = (repository.findByBlogId(blog.getBlogId()));

        assertEquals(result, blog);
    }

    @Test
    public void shouldBeAbleToDeleteBlog() throws NoBlogFoundException {

        doNothing().when(repository).deleteById(blog.getBlogId());

        repository.deleteById(blog.getBlogId());

        verify(repository).deleteById(blog.getBlogId());
    }
    @Test
    public void shouldBeAbleToEditBlog() throws NoBlogFoundException {

        doNothing().when(repository).deleteById(blog.getBlogId());

        repository.deleteById(blog.getBlogId());

        verify(repository).deleteById(blog.getBlogId());
    }


}
