package com.larry.geonames.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.larry.geonames.dto.CountryDto;
import com.larry.geonames.dto.CountryGetIdByNameDto;
import com.larry.geonames.entity.Country;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface CountryMapper extends BaseMapper<Country> {

    @Select("select geonameid from alternate_names where isolanguage = #{language} and alternateName = #{name}")
    List<Integer> getGeonameIdsByNameAndLanguage(@Param("name") String name, @Param("language") String language);

    @Select("select geonameid, name, asciiname, alternatenames, latitude, longitude, feature_class, feature_code, country_code, cc2, admin1_code, admin2_code, admin3_code, admin4_code, population, elevation, dem, timezone, modification_date from allcountries where geonameid = #{geonameid}")
    CountryDto getDetailedInfoWithLanguage(@Param("geonameid")Integer geonameid, @Param("language") String language);

    @Select("select alternateName from alternate_names where isolanguage = #{language} and geonameid = #{geonameid} limit 1")
    String getChildrenByIdsWithLanguage(@Param("geonameid") Integer geonameid, @Param("language") String language);
}
