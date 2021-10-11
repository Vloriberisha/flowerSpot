package com.povio.exam.flowerspot.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class QuoteResponseDTO {

    private Contents contents;

    public String getQuote() {
        if (contents == null || contents.getQuotes() == null || contents.getQuotes() == null
                || contents.getQuotes().isEmpty()) {
            return null;
        }
        return contents.getQuotes().get(0).getQuote();
    }

    @Data
    static class Contents {
        private List<Quote> quotes;

        public Contents() {
        }
    }

    @Data
    static public class Quote {
        private String quote;

        public Quote() {
        }
    }
}

