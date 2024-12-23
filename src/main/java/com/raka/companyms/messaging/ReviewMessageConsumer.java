package com.raka.companyms.messaging;

import com.raka.companyms.payload.ReviewMessage;
import com.raka.companyms.service.CompanyService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
public class ReviewMessageConsumer {
    private final CompanyService companyService;

    public ReviewMessageConsumer(CompanyService companyService) {
        this.companyService = companyService;
    }

    @RabbitListener(queues = "companyRatingQueue")
    public void consumeMessage(ReviewMessage reviewMessage){
        companyService.updateCompanyRating(reviewMessage);
    }
}
