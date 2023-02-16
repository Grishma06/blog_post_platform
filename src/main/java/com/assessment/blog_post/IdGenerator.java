package com.assessment.blog_post;

import java.util.Random;

public class IdGenerator {
    public Integer generateId() {
        return new Random().nextInt(1000);
    }
}

