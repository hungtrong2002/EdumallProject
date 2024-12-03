package com.hung.EdumalProject.controller.request;

import com.hung.EdumalProject.common.AddressType;
import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;

import java.io.Serializable;

@Getter
@Builder
public class AddressRequest implements Serializable {

    private AddressType addressType;
    @NotBlank(message = "Country mustn't be blank")
    private String country;
    @NotBlank(message = "City mustn't be blank")
    private String city;
    @NotBlank(message = "Street mustn't be blank")
    private String street;
    @NotBlank(message = "Streetnumber mustn't be blank")
    private String streetNumber;
    private String schoolName;
}
