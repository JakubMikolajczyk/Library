package com.Library.restAPI.service;

import com.Library.restAPI.model.SpecimenBorrow;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface SpecimenService {

    SpecimenBorrow getSpecimenById(Long id);
    List<SpecimenBorrow> getAllSpecimen();
    List<SpecimenBorrow> getSpecimenByBookId(Long bookId);

    void createSpecimen(SpecimenBorrow specimenBorrow);
    void deleteSpecimenById(Long id);
}
