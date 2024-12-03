package com.hung.EdumalProject.model;

import com.hung.EdumalProject.common.AddressType;
import com.hung.EdumalProject.common.UserStatus;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.Date;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name="tbl_adress")
public class AddressEntity
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", length=255 )
    private Long id;
    @Column(name = "country", length=255 )
    private String country;
    @Column(name = "city", length=255 )
    private String city;
    @Column(name = "street", length=255 )
    private String street;
    @Column(name = "street_number", length=255 )
    private String streetNumber;
    @Column(name = "school_name", length=255 )
    private String schoolName;
    @Column(name = "address_type", length=255 )
    @Enumerated(EnumType.STRING)
    private AddressType addressType;
    @Column(name = "created_at")
    @Temporal(TemporalType.TIMESTAMP)
    @CreationTimestamp
    private Date createdAt;
    @Column(name = "updated_at")
    @Temporal(TemporalType.TIMESTAMP)
    @CreationTimestamp
    private Date updatedAt;
    // Raw userId field
    @Column(name = "user_id", insertable = false, updatable = false)
    private Long userId;

    // Relationship mapping to UserEntity
    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;

}
