package com.example.courseservice.service;

import com.example.courseservice.dto.MycourseDTO;

import java.util.List;

public interface MycourseService {
    List<MycourseDTO> getAllMycourses();
    MycourseDTO getMycourseById(Long id);
    MycourseDTO addMycourse(MycourseDTO mycourseDTO);
    MycourseDTO updateMycourse(MycourseDTO mycourseDTO, Long id);
    void deleteMycourse(Long id);
}
