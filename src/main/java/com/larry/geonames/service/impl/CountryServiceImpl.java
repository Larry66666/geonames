package com.larry.geonames.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.larry.geonames.dto.CountryDto;
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
     *
     * @param name
     * @param language
     * @return
     */
    @Override
    public Object getCountryInfoByName(String name, String language) {
        // 根据名字查询geonameId和name防止重名
        List<Integer> ids = getGeonameIdsByNameAndLanguage(name,language);
        List<Object> result = new ArrayList<>();
        for (Integer id : ids) {
            if (isParent(id)) {
                // 如果是父节点就返回所有子节点的名字
                List<Integer> childrenIds = hierarchyMapper.getChildrenIdsById(id);
                List<String> childrenNames = new ArrayList<>();
                for (Integer childrenId : childrenIds) {
                    String childrenName = countryMapper.getChildrenByIdsWithLanguage(childrenId, language);
                    childrenNames.add(childrenName);
                }
                result.addAll(childrenNames);
            } else {
                // 否则返回该地点的详细信息
                CountryDto countryDto = countryMapper.getDetailedInfoWithLanguage(id, language);
                result.add(countryDto);
            }
        }
        return result;
    }

    /**
     * 通过代号查找国家/地区
     *
     * @param geonameId
     * @param language
     * @return
     */
    @Override
    public CountryDto searchCountryById(Integer geonameId, String language) {
        CountryDto dto = countryMapper.getDetailedInfoWithLanguage(geonameId, language);
        dto.setName(countryMapper.getChildrenByIdsWithLanguage(geonameId, language));
        return dto;
    }

    @Override
    public List<String> getChildrenById(Integer geonameId, String language) {
        List<Integer> childrenIds = hierarchyMapper.getChildrenById(geonameId);
        List<String> childrenNames = new ArrayList<>();
        for (Integer childrenId : childrenIds) {
            String childrenName = countryMapper.getChildrenByIdsWithLanguage(childrenId, language);
            childrenNames.add(childrenName);
        }
        return childrenNames;
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
    private List<Integer> getGeonameIdsByNameAndLanguage(String name, String language) {
        return countryMapper.getGeonameIdsByNameAndLanguage(name, language);
    }
}
