package com.assessment.blog_post;

import com.assessment.blog_post.exception.AlreadyExistException;
import com.assessment.blog_post.exception.NoBlogFoundException;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class BlogRepository {

    private List<Blog> blogs = new ArrayList<>();

    public BlogRepository(List<Blog> blogs) {
        this.blogs.add(new Blog("abc","first blog", 23));
        this.blogs.add(new Blog("xyz","second blog", 456));
        this.blogs.add(new Blog("pqr","another blog", 844));
    }

    public List<Blog> findAll() {
        return blogs;
    }

    public Blog save(Blog blog) throws AlreadyExistException {
        if (exists(blog))
            throw new AlreadyExistException();
        else {
            this.blogs.add(blog);
            return blog;
        }
    }

    public Blog findByBlogId(int id) throws NoBlogFoundException{
        for (Blog blog: blogs)
            if (blog.getBlogId()==id)
                return blog;
        throw new NoBlogFoundException();
    }

    public void deleteById(int id) throws NoBlogFoundException {
        blogs.remove(findByBlogId(id));
    }

    public boolean exists(Blog blog) {
        return blogs.contains(blog);
    }

    public Blog updateById(int id, Blog blog) throws NoBlogFoundException, AlreadyExistException {

        deleteById(id);
        return save(blog);
    }
}
