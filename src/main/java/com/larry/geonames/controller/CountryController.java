package com.larry.geonames.controller;

import com.larry.geonames.dto.CountryDto;
import com.larry.geonames.dto.Result;
import com.larry.geonames.service.ICountryService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/countries")
public class CountryController {

    @Resource
    private ICountryService countryService;

    /**
     * 输入名字获取国家或地区信息，如果有下层关系则输出下层关系的名称列表，否则返回详细信息
     * @param name
     * @return
     */
    @GetMapping("/search")
    public Result searchCountryInfo(@RequestParam("name") String name,
                                    @RequestParam(value = "language", required = false, defaultValue = "en") String language) {
        Object result = countryService.getCountryInfoByName(name, language);
        return Result.ok(result);
    }

    /**
     * 根据国家/地区编号查询
     * @param geonameId
     * @return
     */
    @GetMapping("/{geonameId}")
    public Result searchCountryById(@PathVariable Integer geonameId,
                                    @RequestParam(value = "language", required = false, defaultValue = "en") String language) {
        CountryDto countryDto = countryService.searchCountryById(geonameId, language);
        return Result.ok(countryDto);
    }

    @GetMapping("/children")
    public Result getInfoById(@RequestParam Integer geonameId,
                              @RequestParam(value = "language", required = false, defaultValue = "en") String language) {
        List<String> childrenNames = countryService.getChildrenById(geonameId, language);
        return Result.ok(childrenNames);
    }
}
