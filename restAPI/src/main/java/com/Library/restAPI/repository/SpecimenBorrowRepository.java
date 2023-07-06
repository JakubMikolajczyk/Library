package com.Library.restAPI.repository;

import com.Library.restAPI.model.SpecimenBorrow;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SpecimenBorrowRepository extends JpaRepository<SpecimenBorrow, Long> {
    //Select all borrowed specimens
    List<SpecimenBorrow> findAllByUserIsNotNull();
    //Select borrowed specimen by id if exist
    Optional<SpecimenBorrow> findByIdAndUserIsNotNull(Long id);
    //Select not borrowed specimen by id if exist
    Optional<SpecimenBorrow> findByIdAndUserIsNull(Long id);

    //Select all specimens
    List<SpecimenBorrow> findAllByBookId(Long bookId);
    //Select borrowed specimen by userId
    List<SpecimenBorrow> findAllByUserId(Long userId);

    //Select borrowed specimens by bookId
    List<SpecimenBorrow> findAllByBookIdAndUserIsNotNull(Long bookId);
}
