package com.hung.EdumalProject.controller;


import com.hung.EdumalProject.controller.response.UserResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/user")
@Tag(name="User Controller")
public class UserController {
    @Operation(summary = "Get List User",description = "API retrieve user from database")
    @GetMapping("/list")
    public Map<String,Object> getListUser(@RequestParam(required = false) String keyWord,
                                          @RequestParam(defaultValue = "0") int page,
                                          @RequestParam(defaultValue = "20") int pageSize) {
        UserResponse response = new UserResponse();
        response.setId(1l);
        response.setFirstName("John");
        response.setLastName("Doe");
        response.setEmail("john@doe.com");
        response.setGender("Male");
        response.setPhone("0327090869");

        UserResponse response1 = new UserResponse();
        response1.setId(2l);
        response1.setFirstName("Mc");
        response1.setLastName("Gregor");
        response1.setEmail("Gregor@Mac.com");
        response1.setGender("FeMale");
        response1.setPhone("0327090869");
        List<UserResponse> usersResponse = List.of(response,response1);

        Map<String,Object> results = new LinkedHashMap<>();
        results.put("status", HttpStatus.OK.value());
        results.put("message", "User List");
        results.put("data", usersResponse);


        return results;
    }
    @Operation(summary = "Get User By Id",description = "API retrieve user from database")
    @GetMapping("/{userId}")
    public Map<String,Object> getUserDetailById(@PathVariable Long userId) {
        UserResponse response1 = new UserResponse();
        response1.setId(userId);
        response1.setFirstName("Mc");
        response1.setLastName("Gregor");
        response1.setEmail("Gregor@Mac.com");
        response1.setGender("FeMale");
        response1.setPhone("0327090869");
        List<UserResponse> usersResponse = List.of(response1);

        Map<String,Object> results = new LinkedHashMap<>();
        results.put("status", HttpStatus.OK.value());
        results.put("message", "User List");
        results.put("data", usersResponse);
        return results;
    }
}
