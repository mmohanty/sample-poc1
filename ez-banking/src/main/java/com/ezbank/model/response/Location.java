package com.ezbank.model.response;

import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Location {

    private String country;
    private String city;
}
