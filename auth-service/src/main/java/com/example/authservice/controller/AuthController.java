package com.example.authservice.controller;

import com.example.authservice.dto.ResponseDTO;
import com.example.authservice.dto.UserDTO;
import com.example.authservice.entity.User;
import com.example.authservice.service.TokenService;

import com.example.authservice.service.impl.JpaUserDetailService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/auth")
@RequiredArgsConstructor
public class AuthController {
    private final TokenService tokenService;
    private final AuthenticationManager authenticationManager;
    private final JpaUserDetailService jpaUserDetailService;
    @PostMapping("/register")
    public ResponseEntity<ResponseDTO> addUser(@RequestBody UserDTO userDTO) {
        tokenService.addUser(userDTO);
        return new ResponseEntity<ResponseDTO>(ResponseDTO.builder()
                .httpStatus(HttpStatus.CREATED)
                .message("success add user")
                .data(userDTO).build(), HttpStatus.CREATED);
    }
    @PostMapping("/login")
    public ResponseEntity<ResponseDTO<String>> token(@RequestBody User user) {

        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));
        String token = tokenService.generatedToken(authentication);
        return new ResponseEntity(ResponseDTO.builder().httpStatus(HttpStatus.OK).message("token created").data(token).build(), HttpStatus.OK);

    }
    @GetMapping("/user-data")
    public ResponseEntity<ResponseDTO<Object>> userInfo(@RequestHeader(name = "Authorization") String tokenBearer) {

        UserDTO user = tokenService.decodeToken(tokenBearer);

        //add deeper structure
        return new ResponseEntity(ResponseDTO.builder().httpStatus(HttpStatus.OK).message("token found").data(user).build(), HttpStatus.OK);
        // return user;
    }
    //user
    @GetMapping("/user-data-2")
    public ResponseEntity<UserDTO> userInfo2(@RequestHeader(name = "Authorization") String tokenBearer) {
        UserDTO user = tokenService.decodeToken(tokenBearer);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }
    @GetMapping("/all-users")
    public ResponseEntity<List<UserDTO>> allUsers() {
        return new ResponseEntity<>(jpaUserDetailService.getAll(), HttpStatus.OK);
    }
}
