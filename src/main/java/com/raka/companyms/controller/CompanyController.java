package com.raka.companyms.controller;


import com.raka.companyms.payload.CompanyDTO;
import com.raka.companyms.service.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/companies")
public class CompanyController {

    @Autowired
    CompanyService companyService;

    @GetMapping
    public ResponseEntity<List<CompanyDTO>> getAllCompanies(){
        List<CompanyDTO> companyDTOS = companyService.findAll();
        return new ResponseEntity<>(companyDTOS, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<CompanyDTO> createCompany(@RequestBody CompanyDTO companyDTO){
        CompanyDTO savedCompanyDTO = companyService.createCompany(companyDTO);
        return new ResponseEntity<>(savedCompanyDTO, HttpStatus.OK);
    }

    @GetMapping(value = "/{companyId}")
    public ResponseEntity<CompanyDTO> getCompanyById(@PathVariable Long companyId){
        CompanyDTO savedCompany = companyService.getCompanyById(companyId);
        return new ResponseEntity<>(savedCompany, HttpStatus.OK);
    }

    @PutMapping(value = "/{companyId}")
    public ResponseEntity<CompanyDTO> createCompany(@RequestBody CompanyDTO companyDTO, @PathVariable Long companyId){
        CompanyDTO savedCompanyDTO = companyService.updateCompanyRating(companyDTO, companyId);
        return new ResponseEntity<>(savedCompanyDTO, HttpStatus.OK);
    }

    @DeleteMapping(value = "/{companyId}")
    public ResponseEntity<String> deleteCompanyById(@PathVariable Long companyId){
        String status = companyService.deleteCompanyById(companyId);
        return new ResponseEntity<>(status, HttpStatus.OK);
    }
}
