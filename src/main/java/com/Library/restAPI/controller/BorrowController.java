package com.Library.restAPI.controller;

import com.Library.restAPI.dto.request.BorrowRequest;
import com.Library.restAPI.dto.request.BorrowUsernameRequest;
import com.Library.restAPI.dto.response.BorrowDto;
import com.Library.restAPI.mapper.BorrowMapper;
import com.Library.restAPI.service.BorrowService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/borrows")
public class BorrowController {
    private final BorrowService borrowService;
    private final BorrowMapper borrowMapper;

    @GetMapping
    public List<BorrowDto> getAllBorrows(){
        return borrowService
                .getAllBorrows()
                .stream()
                .map(borrowMapper::toDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public BorrowDto getBorrowById(@PathVariable Long id){
        return borrowMapper.toDto(borrowService.getBorrowById(id));
    }

    @PostMapping
    public void createBorrow(@RequestBody BorrowRequest borrowRequest){
        borrowService.createBorrow(borrowMapper.toEntity(borrowRequest));
    }

    @PostMapping("/byUsername")
    public void createBorrowByUsername(@RequestBody BorrowUsernameRequest borrowUsernameRequest){
        borrowService.createBorrow(borrowMapper.toEntity(borrowUsernameRequest));
    }
    @DeleteMapping({"/{id}"})
    public void deleteBorrow(@PathVariable Long id){
        borrowService.deleteBorrowById(id);
    }
}
