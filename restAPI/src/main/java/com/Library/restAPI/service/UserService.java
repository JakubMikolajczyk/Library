package com.Library.restAPI.service;

import com.Library.restAPI.model.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService {

    User getUserById(Long id);
    List<User> getAllUsers();
    List<User> getAllUsersWithoutDeleted();
    void updateUser(User user);

    void deleteUserById(Long id);

}
