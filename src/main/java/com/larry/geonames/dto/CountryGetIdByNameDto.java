package com.larry.geonames.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CountryGetIdByNameDto {
    private Integer geonameId;
    private String name;
}
