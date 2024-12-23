package com.raka.companyms.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CompanyDTO {
    private Long companyId;
    private String name;
    private String description;
    private Double rating;
}
