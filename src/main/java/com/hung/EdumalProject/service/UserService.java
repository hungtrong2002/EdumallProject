package com.hung.EdumalProject.service;

import com.hung.EdumalProject.controller.request.ChangePasswordRequest;
import com.hung.EdumalProject.controller.request.UserCreationRequest;
import com.hung.EdumalProject.controller.request.UserUpdateRequest;
import com.hung.EdumalProject.controller.response.UserPageResponse;
import com.hung.EdumalProject.controller.response.UserResponse;
import com.hung.EdumalProject.model.UserEntity;

import java.util.List;

public interface UserService  {
    UserPageResponse findAll(String keyword, String sort, int page, int size);
    UserResponse findById(Long id);
    UserEntity findByEmail(String email);
    UserEntity findByUsername(String username);
    void update(UserUpdateRequest req);
    void delete(Long userId);
    long save(UserCreationRequest req);
    void changePassword(ChangePasswordRequest req);
}
