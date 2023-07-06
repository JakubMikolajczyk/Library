package com.Library.restAPI.service;

import com.Library.restAPI.model.SpecimenBorrow;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface BorrowService {

    public List<SpecimenBorrow> getAllBorrows();
    public SpecimenBorrow getBorrowById(Long id);

    public List<SpecimenBorrow> getAllBorrowsByUserId(Long userId);
    public List<SpecimenBorrow> getAllBorrowsByBookId(Long bookId);

    public void createBorrow(SpecimenBorrow specimenBorrow);

    public void deleteBorrowById(Long id);
}
