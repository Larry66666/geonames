package com.larry.geonames.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.larry.geonames.dto.CountryDto;
import com.larry.geonames.entity.Country;

import java.util.List;

public interface ICountryService extends IService<Country> {
     

    List<String> getChildrenById(Integer geonameId, String language);

    Object getCountryInfoByName(String name, String language);

    CountryDto searchCountryById(Integer geonameId, String language);
}
