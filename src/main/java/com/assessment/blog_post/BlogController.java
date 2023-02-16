package com.assessment.blog_post;

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
        private ResponseEntity<Blog> addBlogs(@RequestBody Blog blog) {
            Blog result = service.addBlogs(blog);
            return ResponseEntity.status(OK).body(result);
        }

        @GetMapping("/library/view-blogs")
        public List<Blog> viewBlogs() {
            return service.viewBlogs();
        }

        @DeleteMapping("/library/remove-blog/{id}")
        ResponseEntity<Void> removeBlog(@PathVariable int id) {
            service.removeBlog(id);
            return new ResponseEntity<>(NO_CONTENT);
        }

        @PutMapping("/library/edit-blog/{id}")
        ResponseEntity<Void> updateBlog(@PathVariable int id,@RequestBody Blog blog){
            System.out.println(blog);
            service.updateBlog(id,blog);
            return ResponseEntity.status(ACCEPTED).build();
        }

    }
}
