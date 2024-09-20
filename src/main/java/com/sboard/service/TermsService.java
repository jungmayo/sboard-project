package com.sboard.service;

import com.sboard.dto.TermsDTO;
import com.sboard.dto.UserDTO;
import com.sboard.entity.Terms;
import com.sboard.repository.TermsRepository;
import lombok.RequiredArgsConstructor;
import org.hibernate.action.internal.EntityIncrementVersionProcess;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TermsService {

    private final TermsRepository termsRepository;
    public List<TermsDTO> selectTerms(){

        List<Terms> terms = termsRepository.findAll();
        List<TermsDTO> term = terms.stream().map(entity -> entity.toDTO()).collect(Collectors.toList());
        return term;
    }
}
