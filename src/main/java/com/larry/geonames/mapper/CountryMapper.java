package com.larry.geonames.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.larry.geonames.dto.CountryDto;
import com.larry.geonames.dto.CountryGetIdByNameDto;
import com.larry.geonames.entity.Country;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface CountryMapper extends BaseMapper<Country> {

    @Select("select geonameid, name from allcountries where name = #{name}")
    List<CountryGetIdByNameDto> getGeonameIdsByName(String name);

    @Select("select geonameid, name, asciiname, alternatenames, latitude, longitude, feature_class, feature_code, country_code, cc2, admin1_code, admin2_code, admin3_code, admin4_code, population, elevation, dem, timezone, modification_date from allcountries where geonameid = #{geonameid}")
    CountryDto getDetailedInfo(Integer geonameid);
}
