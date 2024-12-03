package com.hung.EdumalProject.model;

import com.hung.EdumalProject.common.UserStatus;
import com.hung.EdumalProject.common.UserType;
import com.hung.EdumalProject.controller.request.AddressRequest;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name="tbl_user")
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", length=255 )
    private Long id;
    @Column(name = "first_name", length=255 )
    private String firstName;
    @Column(name = "last_name", length=255 )
    private String lastName;
    @Column(name = "username",unique=true, nullable=false, length=255 )
    private String username;
    @Column(name = "password",nullable = true, length=255 )
    private String password;
    @Column(name = "date_of_birth")
    @Temporal(TemporalType.DATE)
    @CreationTimestamp
    private Date birthday;
    @Column(name = "gender", length=255 )
    private String gender;
    @Column(name = "email", length=255 )
    private String email;
    @Column(name = "phone", length=15 )
    private String phone;
    @Column(name = "address", length=255 )
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<AddressEntity> addresses=new HashSet<>();
    @Column(name = "type", length=255 )
    @Enumerated(EnumType.STRING)
    private UserType userType;
    @Column(name = "status", length=255 )
    @Enumerated(EnumType.STRING)
    private UserStatus status;
    @Column(name = "created_at")
    @Temporal(TemporalType.TIMESTAMP)
    @CreationTimestamp
    private Date createdAt;
    @Column(name = "updated_at")
    @Temporal(TemporalType.TIMESTAMP)
    @CreationTimestamp
    private Date updatedAt;
    public void saveAddresses(AddressEntity addressEntity) {
        if(addressEntity!=null){
            if(addresses==null){
                addresses=new HashSet<>();
            }
            addresses.add(addressEntity);
            addressEntity.setUser(this);
        }
    }
}
