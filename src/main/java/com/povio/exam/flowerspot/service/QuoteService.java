package com.povio.exam.flowerspot.service;

import com.povio.exam.flowerspot.dto.QuoteResponseDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class QuoteService {

    @Value("${quote.api.link}")
    private String quoteApiLink;

    public String retrieveQuoteOfTheDay() {
        RestTemplate restTemplate = new RestTemplate();
        QuoteResponseDTO result = restTemplate.getForObject(quoteApiLink, QuoteResponseDTO.class);
        return result.getQuote();
    }

}
