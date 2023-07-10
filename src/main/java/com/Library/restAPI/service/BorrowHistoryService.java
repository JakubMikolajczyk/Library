package com.Library.restAPI.service;

import com.Library.restAPI.model.BorrowHistory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface BorrowHistoryService {

    List<BorrowHistory> getAllHistory();
    List<BorrowHistory> getAllHistoryByUserId(Long userId, boolean showHidden);
    List<BorrowHistory> getAllHistoryByBookId(Long bookId);

    BorrowHistory getHistoryById(Long id);

    void unHideHistoryById(Long historyId, Long userId);
    void hideHistoryById(Long historyId, Long userId);
}
