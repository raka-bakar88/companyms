package com.raka.companyms.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "REVIEWMS",
url = "${reviewms.url}")
public interface ReviewClient {
    @GetMapping("/reviews/averageRating")
    Double getAverageRatingForCompany(@RequestParam("companyId") Long companyId);
}
