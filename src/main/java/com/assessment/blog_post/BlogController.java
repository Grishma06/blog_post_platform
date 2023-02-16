package com.assessment.blog_post;

import com.assessment.blog_post.exception.AlreadyExistException;
import com.assessment.blog_post.exception.NoBlogFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.*;

@RestController
public class BlogController {


    @RestController
    public class Controller {
        @Autowired
        private BlogService service;

        @PostMapping("/library/add-blogs")
        private ResponseEntity<Blog> addBlogs(@RequestBody Blog blog) throws Exception {
            try{
                Blog result = service.addBlogs(blog);
                return ResponseEntity.status(OK).body(result);
            }
            catch (AlreadyExistException exception){
                return new ResponseEntity<>(NOT_ACCEPTABLE);
            }
        }

        @GetMapping("/library/view-blogs")
        public List<Blog> viewBlogs() {
            return service.viewBlogs();
        }

        @DeleteMapping("/library/remove-blog/{id}")
        ResponseEntity<Void> removeBlog(@PathVariable int id) throws Exception {
            try {
                service.removeBlog(id);
                return new ResponseEntity<>(NO_CONTENT);
            }
            catch (NoBlogFoundException exception){
                return new ResponseEntity<>(NOT_FOUND);
            }
        }

        @PutMapping("/library/edit-blog/{id}")
        ResponseEntity<Void> updateBlog(@PathVariable int id,@RequestBody Blog blog) throws Exception {
            try{
                service.updateBlog(id, blog);
                return ResponseEntity.status(ACCEPTED).build();
            }
            catch (NoBlogFoundException exception){
                return new ResponseEntity<>(NOT_FOUND);
            }
            catch (AlreadyExistException exception){
                return new ResponseEntity<>(NOT_ACCEPTABLE);
            }
        }

    }
}
