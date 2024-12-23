package com.raka.companyms.service;

import com.raka.companyms.clients.ReviewClient;
import com.raka.companyms.exception.ResourceNotFoundException;
import com.raka.companyms.model.Company;
import com.raka.companyms.payload.CompanyDTO;
import com.raka.companyms.payload.ReviewMessage;
import com.raka.companyms.repository.CompanyRepository;
import jakarta.ws.rs.NotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CompanyServiceImpl implements CompanyService {
    @Autowired
    CompanyRepository companyRepository;

    private ReviewClient reviewClient;

    public CompanyServiceImpl(CompanyRepository companyRepository,
                              ReviewClient reviewClient,
                              ModelMapper modelMapper) {
        this.companyRepository = companyRepository;
        this.reviewClient = reviewClient;
        this.modelMapper = modelMapper;
    }

    @Autowired
    ModelMapper modelMapper;

    @Override
    public List<CompanyDTO> findAll() {
        List<Company> companies = companyRepository.findAll();
        return companies.stream()
                .map(company -> modelMapper.map(company, CompanyDTO.class))
                .toList();
    }

    @Override
    public CompanyDTO createCompany(CompanyDTO companyDTO) {
        Company company = modelMapper.map(companyDTO, Company.class);
        Company savedCompany = companyRepository.save(company);

        return modelMapper.map(savedCompany, CompanyDTO.class);
    }

    @Override
    public CompanyDTO getCompanyById(Long companyId) {
        Company company = companyRepository.findById(companyId)
                .orElseThrow(
                        () -> new ResourceNotFoundException("Company","Company Id",companyId)
                );

        return modelMapper.map(company, CompanyDTO.class);
    }

    @Override
    public CompanyDTO updateCompanyRating(CompanyDTO companyDTO, Long companyId) {
        Company savedCompany = companyRepository.findById(companyId)
                .orElseThrow(
                        () -> new ResourceNotFoundException("Company","Company Id",companyId)
                );
        savedCompany.setDescription(companyDTO.getDescription());
        savedCompany.setName(companyDTO.getName());
        companyRepository.save(savedCompany);
        return companyDTO;
    }

    @Override
    public String deleteCompanyById(Long companyId) {
        Company savedCompany = companyRepository.findById(companyId)
                .orElseThrow(
                        () -> new ResourceNotFoundException("Company","Company Id",companyId)
                );
        companyRepository.delete(savedCompany);
        return "delete is successfull";
    }

    @Override
    public void updateCompanyRating(ReviewMessage reviewMessage) {
        Company company =companyRepository.findById(reviewMessage.getCompanyId())
                .orElseThrow(() -> new ResourceNotFoundException("Company ID", "companyId", reviewMessage.getCompanyId()));
        double averageRating = reviewClient.getAverageRatingForCompany(reviewMessage.getCompanyId());
        company.setRating(averageRating);
        companyRepository.save(company);
    }
}
