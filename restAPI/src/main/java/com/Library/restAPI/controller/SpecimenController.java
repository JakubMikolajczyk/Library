package com.Library.restAPI.controller;

import com.Library.restAPI.dto.request.SpecimenRequest;
import com.Library.restAPI.dto.response.SpecimenDto;
import com.Library.restAPI.mapper.SpecimenMapper;
import com.Library.restAPI.service.SpecimenService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/specimens")
public class SpecimenController {

    private final SpecimenService specimenService;
    private final SpecimenMapper specimenMapper;

    @GetMapping
    public List<SpecimenDto> getAllSpecimen(){
        return specimenService
                .getAllSpecimen()
                .stream()
                .map(specimenMapper::toDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public SpecimenDto getSpecimenById(@PathVariable Long id){
        return specimenMapper.toDto(specimenService.getSpecimenById(id));
    }

    @PostMapping
    public void createSpecimen(@RequestBody SpecimenRequest specimenRequest){
        specimenService.createSpecimen(specimenMapper.toEntity(specimenRequest));
    }

    @DeleteMapping("/{id}")
    public void deleteSpecimen(@PathVariable Long id){
        specimenService.deleteSpecimenById(id);
    }
}
