package com.Library.restAPI.controller;

import com.Library.restAPI.dto.request.PasswordChangeRequest;
import com.Library.restAPI.dto.response.BorrowDto;
import com.Library.restAPI.dto.response.BorrowHistoryDto;
import com.Library.restAPI.dto.response.UserDto;
import com.Library.restAPI.dto.request.UserEditAdminRequest;
import com.Library.restAPI.dto.request.UserEditRequest;
import com.Library.restAPI.mapper.BorrowHistoryMapper;
import com.Library.restAPI.mapper.BorrowMapper;
import com.Library.restAPI.mapper.UserMapper;
import com.Library.restAPI.security.UsernameAndIdPrincipal;
import com.Library.restAPI.service.AuthService;
import com.Library.restAPI.service.BorrowHistoryService;
import com.Library.restAPI.service.BorrowService;
import com.Library.restAPI.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/users")
public class UserController {

    private final UserService userService;
    private final BorrowService borrowService;
    private final BorrowHistoryService borrowHistoryService;
    private final AuthService authService;
    private final UserMapper userMapper;
    private final BorrowMapper borrowMapper;

    @GetMapping("/me")
    public UserDto getMe(){
        return userMapper.toDto(userService.getUserById(UsernameAndIdPrincipal.getIdFromSecurityCtx()));
    }

    @PutMapping("/me")
    public void editMe(@RequestBody UserEditRequest userEditRequest){
        userService.updateUser(userMapper.toEntity(UsernameAndIdPrincipal.getIdFromSecurityCtx(), userEditRequest));
    }

    @GetMapping("/me/borrows")
    public List<BorrowDto> getMyBorrows(){
        return borrowService
                .getAllBorrowsByUserId(UsernameAndIdPrincipal.getIdFromSecurityCtx())
                .stream()
                .map(borrowMapper::toDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/me/borrow-histories")
    public List<BorrowHistoryDto> getMyHistory(@RequestParam(required = false) boolean showHidden){
        return borrowHistoryService
                .getAllHistoryByUserId(UsernameAndIdPrincipal.getIdFromSecurityCtx(),showHidden)
                .stream()
                .map(BorrowHistoryMapper::toDto)
                .collect(Collectors.toList());
    }

    @PostMapping("/me/borrow-histories/{historyId}/hide")
    public void hideMyHistory(@PathVariable Long historyId){
        borrowHistoryService.hideHistoryById(historyId, UsernameAndIdPrincipal.getIdFromSecurityCtx());
    }

    @PostMapping("/me/borrow-histories/{historyId}/unhide")
    public void unhideMyHistory(@PathVariable Long historyId){
        borrowHistoryService.unHideHistoryById(historyId, UsernameAndIdPrincipal.getIdFromSecurityCtx());
    }

    @PostMapping("/me/change-password")
    public void changePassword(HttpServletResponse response, PasswordChangeRequest passwordChangeRequest){
        authService.changePassword(
                UsernameAndIdPrincipal.getIdFromSecurityCtx(),
                passwordChangeRequest,
                response);
    }


    //STAFF and ADMIN
    @GetMapping
    public List<UserDto> getAllUsers(){
        return userService.getAllUsers().stream().map(userMapper::toDto).collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public UserDto getUser(@PathVariable Long id){
        return userMapper.toDto(userService.getUserById(id));
    }

    @GetMapping("/{userId}/borrows")
    public List<BorrowDto> getUserBorrows(@PathVariable Long userId){
        return borrowService
                .getAllBorrowsByUserId(userId)
                .stream()
                .map(borrowMapper::toDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/{userId}/borrow-histories")
    public List<BorrowHistoryDto> getUserHistories(@PathVariable Long userId){
        return borrowHistoryService
                .getAllHistoryByUserId(userId, true)
                .stream()
                .map(BorrowHistoryMapper::toDto)
                .collect(Collectors.toList());
    }
    @PutMapping("/{id}")
    public void editUser(@PathVariable Long id, @RequestBody UserEditAdminRequest userEditAdminRequest){
        userService.updateUser(userMapper.toEntity(id, userEditAdminRequest));
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable Long id){
        userService.deleteUserById(id);
    }
}
