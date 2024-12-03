package com.hung.EdumalProject.repository;

import com.hung.EdumalProject.common.AddressType;
import com.hung.EdumalProject.model.AddressEntity;
import org.springframework.boot.autoconfigure.amqp.RabbitConnectionDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AddressRepository extends JpaRepository<AddressEntity, Long> {
    AddressEntity findByUserIdAndAddressType(Long userId, AddressType addressType);
}
