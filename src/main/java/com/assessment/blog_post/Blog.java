package com.assessment.blog_post;

import java.util.Objects;


public class Blog {
    private String title;
    private String content;

    private int blogId;

    public Blog(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getBlogId() {
        return blogId;
    }

    public void setBlogId(int blogId) {
        this.blogId = blogId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Blog blog = (Blog) o;
        return blogId == blog.blogId && Objects.equals(title, blog.title) && Objects.equals(content, blog.content);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, content, blogId);
    }
}

