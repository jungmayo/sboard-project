package com.sboard.dto;

import com.sboard.entity.Terms;
import lombok.*;

@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TermsDTO {

    private String terms;
    private String privacy;

    public Terms toEntity() {
        return Terms.builder()
                .terms(terms)
                .privacy(privacy)
                .build();
    }
}
