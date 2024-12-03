package com.hung.EdumalProject.controller.request;

import com.hung.EdumalProject.common.UserType;
import lombok.Getter;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Getter
@ToString
public class UserUpdateRequest implements Serializable {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String username;
    private String gender;
    private Date birthday;
    private String phone;
    private UserType userType;
    private Set<AddressRequest> addressEntityList;
}
