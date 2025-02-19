package com.larry.geonames.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.larry.geonames.dto.CountryDto;
import com.larry.geonames.dto.CountryGetIdByNameDto;
import com.larry.geonames.entity.Country;
import com.larry.geonames.mapper.CountryMapper;
import com.larry.geonames.mapper.HierarchyMapper;
import com.larry.geonames.service.ICountryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class CountryServiceImpl extends ServiceImpl<CountryMapper, Country> implements ICountryService {

    @Resource
    private HierarchyMapper hierarchyMapper;
    @Resource
    private CountryMapper countryMapper;
    /**
     * 输入名字获取国家或地区信息，如果有下层关系则输出下层关系的名称列表，否则返回详细信息
     * @param name
     * @return
     */
    @Override
    public Object getCountryInfoByName(String name) {
        // 根据名字查询geonameId和name防止重名
        List<CountryGetIdByNameDto> countries = getGeonameIdsByName(name);
        List<Object> result = new ArrayList<>();
        for (CountryGetIdByNameDto country : countries) {
            Integer geonameId = country.getGeonameId();
            if (isParent(country.getGeonameId())) {
                // 如果是父节点就返回所以子节点的名字
                List<String> childNames = hierarchyMapper.getChildrenById(geonameId);
                result.addAll(childNames);
            } else {
                // 否则返回该地点的详细信息
                CountryDto countryDto = countryMapper.getDetailedInfo(geonameId);
                result.add(countryDto);
            }
        }
        return result;
    }

    /**
     * 通过代号查找国家/地区
     * @param geonameId
     * @return
     */
    @Override
    public CountryDto searchCountryById(Integer geonameId) {
        return countryMapper.getDetailedInfo(geonameId);
    }

    @Override
    public List<String> getChildrenById(Integer geonameId) {
        return hierarchyMapper.getChildrenById(geonameId);
    }

    /**
     * 判断该geonameId对应的国家是否还有下层关系
     * @param geonameId
     * @return
     */
    private boolean isParent(Integer geonameId) {
        int count = hierarchyMapper.isParent(geonameId);
        log.info("isParent: geonameId={}, count={}", geonameId, count);
        return count != 0;
    }

    /**
     * 根据名字获取geonameId
     * @param name
     * @return
     */
    private List<CountryGetIdByNameDto> getGeonameIdsByName(String name) {
        return countryMapper.getGeonameIdsByName(name);
    }
}
