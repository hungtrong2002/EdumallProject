package com.hung.EdumalProject.controller.response;



import com.hung.EdumalProject.model.AddressEntity;
import lombok.*;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserResponse implements Serializable {
    private Long id;
    private String firstName;
    private String lastName;
    private Date birthday;
    private String gender;
    private String email;
    private String phone;
}

