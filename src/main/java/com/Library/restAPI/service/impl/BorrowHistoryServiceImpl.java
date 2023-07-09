package com.Library.restAPI.service.impl;

import com.Library.restAPI.exception.BorrowHistoryNotFoundException;
import com.Library.restAPI.model.BorrowHistory;
import com.Library.restAPI.repository.BorrowHistoryRepository;
import com.Library.restAPI.service.BorrowHistoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class BorrowHistoryServiceImpl implements BorrowHistoryService {

    private final BorrowHistoryRepository borrowHistoryRepository;

    @Override
    public List<BorrowHistory> getAllHistory() {
        return borrowHistoryRepository.findAll();
    }

    @Override
    public List<BorrowHistory> getAllHistoryByUserId(Long userId, boolean showHidden) {
        return showHidden ?
                borrowHistoryRepository.findAllByUserId(userId) :
                borrowHistoryRepository.findAllByUserIdAndHiddenIsFalse(userId);
    }

    @Override
    public List<BorrowHistory> getAllHistoryByBookId(Long bookId) {
        return borrowHistoryRepository.findAllByBookId(bookId);
    }

    @Override
    public BorrowHistory getHistoryById(Long id) {
        return borrowHistoryRepository.findById(id)
                .orElseThrow(BorrowHistoryNotFoundException::new);
    }

    @Override
    public void unHideHistoryById(Long id) {
        BorrowHistory fromDb = borrowHistoryRepository.findById(id)
                .orElseThrow(BorrowHistoryNotFoundException::new);    //TODO exception
        fromDb.setHidden(false);
        borrowHistoryRepository.save(fromDb);
    }

    @Override
    public void hideHistoryById(Long id) {
        borrowHistoryRepository.deleteById(id);
    }
}
