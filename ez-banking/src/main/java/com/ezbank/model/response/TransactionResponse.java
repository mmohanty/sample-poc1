package com.ezbank.model.response;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TransactionResponse {

    private String id;
    private String status;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Integer remainingAttempt;
}
