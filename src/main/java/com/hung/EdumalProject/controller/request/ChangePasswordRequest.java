package com.hung.EdumalProject.controller.request;

import lombok.Getter;

@Getter
public class ChangePasswordRequest {
    private Long id;
    private String oldPassword;
    private String newPassword;
    private String confirmPassword;
}
