package com.Library.restAPI.service;

import com.Library.restAPI.model.SpecimenBorrow;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface SpecimenService {

    public SpecimenBorrow getSpecimenById(Long id);
    public List<SpecimenBorrow> getAllSpecimen();

    public List<SpecimenBorrow> getSpecimenByBookId(Long bookId);

    public void createSpecimen(SpecimenBorrow specimenBorrow);
    public void deleteSpecimenById(Long id);
}
