package com.example.courseservice.service.implementation;

import com.example.courseservice.dto.CourseDTO;
import com.example.courseservice.dto.MycourseDTO;
import com.example.courseservice.dto.UserDTO;
import com.example.courseservice.entity.Mycourse;
import com.example.courseservice.repository.MycourseRepository;
import com.example.courseservice.service.MycourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MycourseServiceImpl implements MycourseService {
    @Autowired
    private MycourseRepository mycourseRepository;

    @Autowired
    private DiscoveryClient discoveryClient;


    @Override
    public List<MycourseDTO> getAllMycourses() {
        List<Mycourse> mycourses = mycourseRepository.findAll();

        // Convert the mycourses entities to DTOs
        return mycourses.stream()
                .map(mycourse -> MycourseDTO.builder()
                        .id(mycourse.getId())
                        .userId(mycourse.getUserId())
                        .courseId(mycourse.getCourseId())
                        //.createdAt(mycourse.getCreatedAt())
                        //.updatedAt(mycourse.getUpdatedAt())
                        .build())
                .collect(Collectors.toList());
    }

    @Override
    public MycourseDTO getMycourseById(Long id) {
        // Retrieve the mycourse from the repository
        Mycourse mycourse = mycourseRepository.findById(id)
                .orElseThrow();

        // Convert the mycourse entity to a DTO
        return MycourseDTO.builder()
                .id(mycourse.getId())
                .userId(mycourse.getUserId())
                .courseId(mycourse.getCourseId())
                //.createdAt(mycourse.getCreatedAt())
                //.updatedAt(mycourse.getUpdatedAt())
                .build();
    }

    @Override
    public MycourseDTO addMycourse(MycourseDTO mycourseDTO) {
        // Retrieve the user and course entities using the service discovery tool
        ServiceInstance authServiceInstance = discoveryClient.getInstances("auth-service").get(0);
        ServiceInstance courseServiceInstance = discoveryClient.getInstances("course-service").get(0);

        UserDTO userDTO = new RestTemplate().getForObject(authServiceInstance.getUri() + "/users/" + mycourseDTO.getUserId(), UserDTO.class);
        CourseDTO courseDTO = new RestTemplate().getForObject(courseServiceInstance.getUri() + "/courses/" + mycourseDTO.getCourseId(), CourseDTO.class);

        // Create a new mycourse entity and set the user and course entities
        Mycourse mycourse = Mycourse.builder()
                .userId(Long.valueOf(userDTO.getId()))
                .courseId(courseDTO.getId())
                .build();

        // Persist the mycourse to the database
        mycourseRepository.save(mycourse);
        return mycourseDTO;
    }

    @Override
    public MycourseDTO updateMycourse(MycourseDTO mycourseDTO, Long id) {
        // Retrieve the mycourse from the repository
        Mycourse mycourse = mycourseRepository.findById(id)
                .orElseThrow();
        // Update the mycourse entity with the new information
        mycourse.setUserId(mycourseDTO.getUserId());
        mycourse.setCourseId(mycourseDTO.getCourseId());
        // Retrieve the user and course entities using the service discovery tool
        ServiceInstance authServiceInstance = discoveryClient.getInstances("auth-service").get(0);
        ServiceInstance courseServiceInstance = discoveryClient.getInstances("course-service").get(0);

        UserDTO userDTO = new RestTemplate().getForObject(authServiceInstance.getUri() + "/users/" + mycourseDTO.getUserId(), UserDTO.class);
        CourseDTO courseDTO = new RestTemplate().getForObject(courseServiceInstance.getUri() + "/courses/" + mycourseDTO.getCourseId(), CourseDTO.class);

        // set the user and course entities
        mycourse.setUserId(Long.valueOf(userDTO.getId()));
        mycourse.setCourseId(courseDTO.getId());

        // Persist the updated mycourse to the database
        mycourseRepository.save(mycourse);
        return mycourseDTO;
    }

    @Override
    public void deleteMycourse(Long id) {
        // Retrieve the mycourse from the repository
        Mycourse mycourse = mycourseRepository.findById(id)
                .orElseThrow();

        // Delete the mycourse from the repository

    }
}

