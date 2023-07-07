package com.Library.restAPI.mapper;

import com.Library.restAPI.dto.response.UserDto;
import com.Library.restAPI.dto.request.UserEditAdminRequest;
import com.Library.restAPI.dto.request.UserEditRequest;
import com.Library.restAPI.model.User;
import com.Library.restAPI.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class UserMapper {


    private final UserRepository userRepository;
    public UserDto toDto(User entity){
        return UserDto.builder()
                .username(entity.getUsername())
                .email(entity.getEmail())
                .name(entity.getName())
                .surname(entity.getSurname())
                .address(entity.getAddress())
                .city(entity.getCity())
                .role(entity.getRole())
                .deleteDate(entity.getDeleteDate())
                .build();
    }

    private User toEntity(User user, UserEditRequest userEditRequest){
        user.setEmail(userEditRequest.email());
        user.setName(userEditRequest.name());
        user.setSurname(userEditRequest.surname());
        user.setAddress(userEditRequest.address());
        user.setCity(userEditRequest.city());

        return user;
    }

    public User toEntity(String username, UserEditRequest userEditRequest){
        User userFromDB = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found")); //TODO exception
        return toEntity(userFromDB, userEditRequest);
    }

    public User toEntity(Long id, UserEditRequest userEditRequest){
        User userFromDB = userRepository.findById(id)
                .orElseThrow(() -> new UsernameNotFoundException("User not found")); //TODO exception
        return toEntity(userFromDB, userEditRequest);
    }

    private User toEntity(User user, UserEditAdminRequest userEditAdminRequest){
        user.setEmail(userEditAdminRequest.email());
        user.setName(userEditAdminRequest.name());
        user.setSurname(userEditAdminRequest.surname());
        user.setAddress(userEditAdminRequest.address());
        user.setCity(userEditAdminRequest.city());
        user.setRole(userEditAdminRequest.role());
        user.setDeleteDate(userEditAdminRequest.deleteDate());

        return user;
    }

    public User toEntity(Long id, UserEditAdminRequest userEditAdminRequest){
        User userFromDB = userRepository.findById(id)
                .orElseThrow(() -> new UsernameNotFoundException("User not found")); //TODO exception
        return toEntity(userFromDB, userEditAdminRequest);
    }
}
