package com.Library.restAPI.service.impl;

import com.Library.restAPI.model.SpecimenBorrow;
import com.Library.restAPI.repository.SpecimenBorrowRepository;
import com.Library.restAPI.service.SpecimenService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class SpecimenServiceImpl implements SpecimenService {

    private final SpecimenBorrowRepository specimenBorrowRepository;

    @Override
    public SpecimenBorrow getSpecimenById(Long id) {
        return specimenBorrowRepository.findById(id)
                .orElseThrow(RuntimeException::new);    //TODO exception
    }


    @Override
    public List<SpecimenBorrow> getAllSpecimen() {
        return specimenBorrowRepository.findAll();
    }

    @Override
    public List<SpecimenBorrow> getSpecimenByBookId(Long bookId) {
        return specimenBorrowRepository.findAllByBookId(bookId);
    }

    @Override
    public void createSpecimen(SpecimenBorrow specimenBorrow) {
        specimenBorrowRepository.save(specimenBorrow);
    }

    @Override
    public void deleteSpecimenById(Long id) {
        specimenBorrowRepository.findByIdAndUserIsNull(id)
                .orElseThrow(RuntimeException::new);    //TODO exception (specimen might be borrowed or not exist)
        specimenBorrowRepository.deleteById(id);
    }
}
