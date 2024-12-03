package com.hung.EdumalProject.service.Implement;

import com.hung.EdumalProject.common.UserStatus;
import com.hung.EdumalProject.controller.request.AddressRequest;
import com.hung.EdumalProject.controller.request.ChangePasswordRequest;
import com.hung.EdumalProject.controller.request.UserCreationRequest;
import com.hung.EdumalProject.controller.request.UserUpdateRequest;
import com.hung.EdumalProject.controller.response.UserPageResponse;
import com.hung.EdumalProject.controller.response.UserResponse;
import com.hung.EdumalProject.exception.ResourceNotFoundException;
import com.hung.EdumalProject.model.AddressEntity;
import com.hung.EdumalProject.model.UserEntity;
import com.hung.EdumalProject.repository.AddressRepository;
import com.hung.EdumalProject.repository.UserRepository;
import com.hung.EdumalProject.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@Slf4j(topic = "USER-SERVICE")
@RequiredArgsConstructor
public class UserServiceImplement implements UserService {
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final AddressRepository addressRepository;

    @Override
    public void changePassword(ChangePasswordRequest req) {
        UserEntity userEntity = getUserById(req.getId());
        if (req.getConfirmPassword().equals(req.getNewPassword())) {
            userEntity.setPassword(passwordEncoder.encode(req.getNewPassword()));
            userRepository.save(userEntity);
        }

    }

    @Override
    public UserPageResponse findAll(String keyword, String sort, int page, int size) {

        Sort.Order order = new Sort.Order(Sort.Direction.ASC, "id");
        if (StringUtils.hasLength(sort)) {
            Pattern pattern = Pattern.compile("(\\w+?)(:)(.*)");// tencot:asc|desc
            Matcher matcher = pattern.matcher(sort);
            if (matcher.find()) {
                String columnName = matcher.group(1);
                if (matcher.group(3).equalsIgnoreCase("asc")) {
                    order = new Sort.Order(Sort.Direction.ASC, columnName);
                } else {
                    order = new Sort.Order(Sort.Direction.DESC, columnName);
                }
            }
        }
        //Paging
        Pageable pageable = PageRequest.of(page, size, Sort.by(order));
        Page<UserEntity> entityPage=null;
        //Keyword
        if(StringUtils.hasLength(keyword)) {
            keyword = "%"+ keyword.toLowerCase()+ "%";
            entityPage= userRepository.searchByKeyWord(keyword,pageable);
        }else{
            entityPage = userRepository.findAll(pageable);
        }
        UserPageResponse pageResponse = getUserPageResponse(page, size, entityPage);
        return pageResponse;
    }



    @Override
    public UserResponse findById(Long id) {
        UserEntity userEntity = getUserById(id);
        return UserResponse.builder()
                .id(id)
                .firstName(userEntity.getFirstName())
                .lastName(userEntity.getLastName())
                .email(userEntity.getEmail())
                .phone(userEntity.getPhone())
                .birthday(userEntity.getBirthday())
                .gender(userEntity.getGender())
                .build();
    }

    @Override
    public UserEntity findByEmail(String email) {
        return null;
    }

    @Override
    public UserEntity findByUsername(String username) {
        return null;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(UserUpdateRequest req) {
        //Get User By ID
        log.info("updating user");
        UserEntity user = getUserById(req.getId());
        user.setUsername(req.getUsername());
        user.setEmail(req.getEmail());
        user.setFirstName(req.getFirstName());
        user.setLastName(req.getLastName());
        user.setPhone(req.getPhone());
        user.setGender(req.getGender());
        user.setUserType(req.getUserType());
        req.getAddressEntityList().forEach(address -> {
            user.saveAddresses(convertToAddressEntity(address));
        });
        userRepository.save(user);
        //save address
        Set<AddressEntity> addresses = new HashSet<>();
        req.getAddressEntityList().forEach(address -> {
            log.info("adding address" + req.getId() + address.getAddressType());
            AddressEntity addressEntity = addressRepository.findByUserIdAndAddressType(req.getId(), address.getAddressType());
            if (addressEntity != null) {
                addressEntity = new AddressEntity();
            }
            addressEntity.setAddressType(address.getAddressType());
            addressEntity.setCity(address.getCity());
            addressEntity.setCountry(address.getCountry());
            addressEntity.setSchoolName(address.getSchoolName());
            addressEntity.setStreet(address.getStreet());
            addressEntity.setStreetNumber(address.getStreetNumber());
            addresses.add(addressEntity);
        });
        addressRepository.saveAll(addresses);

        log.info("updated user");
    }

    @Override
    public void delete(Long idUser) {
        log.info("deleting user");
        UserEntity user = getUserById(idUser);
        user.setStatus(UserStatus.DELETED);
        userRepository.save(user);
        log.info("deleted user");
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public long save(UserCreationRequest req) {
        log.info("Saving user {}", req);
        UserEntity userEntity = new UserEntity();
        userEntity.setEmail(req.getEmail());
        userEntity.setUsername(req.getUsername());
        userEntity.setGender(req.getGender());
        userEntity.setBirthday(req.getBirthday());
        userEntity.setFirstName(req.getFirstName());
        userEntity.setLastName(req.getLastName());
        userEntity.setPhone(req.getPhone());
        userEntity.setUserType(req.getUserType());
        userEntity.setStatus(UserStatus.NONE);
        req.getAddressEntityList().forEach(address -> {
            userEntity.saveAddresses(convertToAddressEntity(address));
        });
        userRepository.save(userEntity);
        return userEntity.getId();
    }


    private UserEntity getUserById(Long id) {
        return userRepository.findById(Math.toIntExact(id)).orElseThrow(() -> new ResourceNotFoundException("User not found"));
    }

    private AddressEntity convertToAddressEntity(AddressRequest addressRequest) {

        AddressEntity addressEntity = new AddressEntity();
        addressEntity.setCity(addressRequest.getCity());
        addressEntity.setCountry(addressRequest.getCountry());
        addressEntity.setStreet(addressRequest.getStreet());
        addressEntity.setStreetNumber(addressRequest.getStreetNumber());
        addressEntity.setAddressType(addressRequest.getAddressType());
        return addressEntity;
    }
    private static UserPageResponse getUserPageResponse(int page, int size, Page<UserEntity> userEntities) {
        List<UserResponse> userList = userEntities.stream().map(entity -> UserResponse.builder().
                id(entity.getId()).
                firstName(entity.getFirstName()).
                lastName(entity.getLastName()).
                birthday(entity.getBirthday()).
                gender(entity.getGender()).
                email(entity.getEmail()).
                phone(entity.getPhone()).
                build()
        ).toList();
        UserPageResponse pageResponse = new UserPageResponse();
        pageResponse.setPageNumber(page);
        pageResponse.setPageSize(size);
        pageResponse.setTotalElements(userEntities.getTotalElements());
        pageResponse.setTotalPages(userEntities.getTotalPages());
        pageResponse.setUsers(userList);
        return pageResponse;
    }
}
