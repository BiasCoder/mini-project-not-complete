package com.example.courseservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "Mycourse not found")
public class MycourseNotFoundException {
    public MycourseNotFoundException(String message) {
        super();
    }
}
