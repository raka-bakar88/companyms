package com.raka.companyms.service;


import com.raka.companyms.payload.CompanyDTO;
import com.raka.companyms.payload.ReviewMessage;

import java.util.List;

public interface CompanyService {
    List<CompanyDTO> findAll();

    CompanyDTO createCompany(CompanyDTO companyDTO);

    CompanyDTO getCompanyById(Long companyId);

    CompanyDTO updateCompanyRating(CompanyDTO companyDTO, Long companyId);

    String deleteCompanyById(Long companyId);

    public void updateCompanyRating(ReviewMessage reviewMessage);
}
