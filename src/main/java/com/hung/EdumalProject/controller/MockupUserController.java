package com.hung.EdumalProject.controller;


import com.hung.EdumalProject.controller.request.UserCreationRequest;
import com.hung.EdumalProject.controller.request.UserUpdateRequest;
import com.hung.EdumalProject.controller.response.UserPageResponse;
import com.hung.EdumalProject.controller.response.UserResponse;
import com.hung.EdumalProject.service.Implement.UserServiceImplement;
import com.hung.EdumalProject.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/mockup/user")
@Tag(name="Mockup User Controller")
@Slf4j(topic = "User Controller")
@RequiredArgsConstructor
public class MockupUserController {


    private final UserService userService;

    @Operation(summary = "Create new user",description = "API retrieve user from database")
    @PostMapping("/add")
    public ResponseEntity<Map<String,Object>> createUser(@RequestBody UserCreationRequest req) {
        Map<String,Object> results = new LinkedHashMap<>();
        results.put("status", HttpStatus.CREATED.value());
        results.put("message", "User Created Successfully");
        results.put("data", userService.save(req));

        return new ResponseEntity<>(results,HttpStatus.CREATED);
    }
    @Operation(summary = "Update user",description = "API retrieve user from database")
    @PutMapping("/update")
    public Map<String,Object> updateUser(@RequestBody UserUpdateRequest req){
        userService.update(req);
        log.info("Updating user {}" ,req);
        Map<String,Object> results = new LinkedHashMap<>();
        results.put("status", HttpStatus.OK.value());
        results.put("message", "User Updated Successfully");
        return results;
    }
    @Operation(summary = "Delete user",description = "API retrieve user from database")
    @DeleteMapping("/delete")
    public Map<String,Object> deleteUser(@RequestParam Long id){
        userService.delete(id);
        log.info("Deleting user {}" ,id);
        Map<String,Object> results = new LinkedHashMap<>();
        results.put("status", HttpStatus.OK.value());
        results.put("message", "User Deleted Successfully");
        return results;
    }
    @Operation(summary = "Find user by Id",description = "API retrieve user from database")
    @GetMapping("/find")
    public ResponseEntity<Map<String,Object>> findUserByID(@RequestParam Long req) {
        Map<String,Object> results = new LinkedHashMap<>();
        results.put("status", HttpStatus.CREATED.value());
        results.put("message", "Find User Successfully");
        results.put("data", userService.findById(req));

        return new ResponseEntity<>(results,HttpStatus.OK);
    }
    @Operation(summary = "Find user by Id",description = "API retrieve user from database")
    @GetMapping("/GetList")
    public UserPageResponse getListUser(@RequestParam(required = false) String keyword,
                                        @RequestParam(required = false) String sort,
                                        @RequestParam(defaultValue = "0") int page,
                                        @RequestParam(defaultValue = "20") int size ){
        log.info("Get user list");
        return userService.findAll(keyword,sort, page , size);
    }

}
