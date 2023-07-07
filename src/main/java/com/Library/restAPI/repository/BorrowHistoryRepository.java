package com.Library.restAPI.repository;

import com.Library.restAPI.model.BorrowHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BorrowHistoryRepository extends JpaRepository<BorrowHistory, Long> {
    List<BorrowHistory> findAllByBookId(Long bookId);
    List<BorrowHistory> findAllByUserId(Long userId);
    List<BorrowHistory> findAllByUserIdAndHiddenIsFalse(Long userId);
}
