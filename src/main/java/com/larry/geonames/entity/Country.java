package com.larry.geonames.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.larry.geonames.dto.CountryDto;
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
public class Country extends CountryDto {

    private Integer level;
}
