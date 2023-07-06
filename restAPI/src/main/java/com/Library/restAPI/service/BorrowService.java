package com.Library.restAPI.service;

import com.Library.restAPI.model.SpecimenBorrow;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface BorrowService {

    List<SpecimenBorrow> getAllBorrows();
    SpecimenBorrow getBorrowById(Long id);

    List<SpecimenBorrow> getAllBorrowsByUserId(Long userId);
    List<SpecimenBorrow> getAllBorrowsByBookId(Long bookId);

    void createBorrow(SpecimenBorrow specimenBorrow);

    void deleteBorrowById(Long id);
}
