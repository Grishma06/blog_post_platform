package com.assessment.blog_post;

import com.assessment.blog_post.exception.AlreadyExistException;
import com.assessment.blog_post.exception.NoBlogFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BlogService {

    @Autowired
    private BlogRepository repository;

    public List<Blog> viewBlogs() {
        return (List<Blog>) repository.findAll();
    }
    public Blog addBlogs(Blog blog) throws AlreadyExistException {
        Blog saved = repository.save(blog);
        return saved;
    }

    public Blog findByBlogId(int blogId) throws NoBlogFoundException {
        return repository.findByBlogId(blogId);
    }
    public void removeBlog(int id) throws NoBlogFoundException {

        if (repository.exists(findByBlogId(id))) {
            repository.deleteById(id);
        }
    }

    public void updateBlog(int id, Blog blog) throws NoBlogFoundException, AlreadyExistException {
        if (repository.exists(findByBlogId(id))) {
            repository.updateById(id, blog);
        }
    }

}
