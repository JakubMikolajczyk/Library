package com.Library.restAPI.service.impl;

import com.Library.restAPI.exception.BorrowNotFoundException;
import com.Library.restAPI.exception.DeleteBorrowedException;
import com.Library.restAPI.model.BorrowHistory;
import com.Library.restAPI.model.SpecimenBorrow;
import com.Library.restAPI.repository.BorrowHistoryRepository;
import com.Library.restAPI.repository.SpecimenBorrowRepository;
import com.Library.restAPI.service.BorrowService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class BorrowServiceImpl implements BorrowService {

    private final SpecimenBorrowRepository specimenBorrowRepository;
    private final BorrowHistoryRepository borrowHistoryRepository;

    @Override
    public List<SpecimenBorrow> getAllBorrows() {
        return specimenBorrowRepository.findAllByUserIsNotNull();
    }

    @Override
    public SpecimenBorrow getBorrowById(Long id) {
        return specimenBorrowRepository.findByIdAndUserIsNotNull(id)
                .orElseThrow(BorrowNotFoundException::new);
    }

    @Override
    public List<SpecimenBorrow> getAllBorrowsByUserId(Long userId) {
        return specimenBorrowRepository.findAllByUserId(userId);
    }

    @Override
    public List<SpecimenBorrow> getAllBorrowsByBookId(Long bookId) {
        return specimenBorrowRepository.findAllByBookIdAndUserIsNotNull(bookId);
    }

    @Override
    public void createBorrow(SpecimenBorrow specimenBorrow) {
        specimenBorrowRepository.save(specimenBorrow);
    }

    @Override
    @Transactional
    public void deleteBorrowById(Long id) {
        SpecimenBorrow deletedBorrow = specimenBorrowRepository.findByIdAndUserIsNotNull(id)
                .orElseThrow(BorrowNotFoundException::new);

        borrowHistoryRepository.save(
                BorrowHistory.builder()
                        .book(deletedBorrow.getBook())
                        .user(deletedBorrow.getUser())
                        .startTime(deletedBorrow.getStartTime())
                        .build());

        deletedBorrow.setUser(null);
        deletedBorrow.setStartTime(null);
        specimenBorrowRepository.save(deletedBorrow);
    }
}
