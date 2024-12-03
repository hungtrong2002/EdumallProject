package com.hung.EdumalProject.repository;

import com.hung.EdumalProject.model.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Integer> {
    @Query(value="select u from UserEntity  u where u.status ='ACTIVE' and (lower(u.firstName) like :keyword or lower(u.lastName) like :keyword or lower(u.phone)  like :keyword or lower(u.email) like :keyword)")
    Page<UserEntity> searchByKeyWord(String username, Pageable pageable);
}
