package com.example.courseservice.repository;

import com.example.courseservice.entity.Mycourse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MycourseRepository extends JpaRepository <Mycourse, Long> {
}
