package com.example.courseservice.controller;

import com.example.courseservice.dto.MycourseDTO;
import com.example.courseservice.service.MycourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/mycourses")
public class MycourseController {

    @Autowired
    private MycourseService mycourseService;

    @GetMapping
    public ResponseEntity<List<MycourseDTO>> getAllMycourses() {
        List<MycourseDTO> mycourses = mycourseService.getAllMycourses();
        return new ResponseEntity<>(mycourses, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MycourseDTO> getMycourseById(@PathVariable Long id) {
        MycourseDTO mycourse = mycourseService.getMycourseById(id);
        return new ResponseEntity<>(mycourse, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<MycourseDTO> addMycourse(@RequestBody MycourseDTO mycourseDTO) {
        MycourseDTO mycourse = mycourseService.addMycourse(mycourseDTO);
        return new ResponseEntity<>(mycourse, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<MycourseDTO> updateMycourse(@RequestBody MycourseDTO mycourseDTO, @PathVariable Long id) {
        MycourseDTO mycourse = mycourseService.updateMycourse(mycourseDTO, id);
        return new ResponseEntity<>(mycourse, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMycourse(@PathVariable Long id) {
        mycourseService.deleteMycourse(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}

