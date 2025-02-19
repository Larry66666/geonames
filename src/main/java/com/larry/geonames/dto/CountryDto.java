package com.larry.geonames.dto;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.util.Date;

@Data
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
@TableName("allcountries")
public class CountryDto {

    private Integer geonameId;
    private String name;
    private String asciiname;
    private String alternatenames;
    private BigDecimal latitude;
    private BigDecimal longitude;
    private String featureClass;
    private String featureCode;
    private String countryCode;
    private String cc2;
    private String admin1Code;
    private String admin2Code;
    private String admin3Code;
    private String admin4Code;
    private BigDecimal population;
    private String elevation;
    private BigDecimal dem;
    private String timezone;
    private Date modificationDate;
}

