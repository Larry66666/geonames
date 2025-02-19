package com.larry.geonames.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.larry.geonames.dto.CountryDto;
import com.larry.geonames.entity.Hierarchy;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface HierarchyMapper extends BaseMapper<Hierarchy> {

    @Select("SELECT c.name " +
            "FROM allcountries c " +
            "JOIN hierarchy h ON c.geonameid = h.child_id " +
            "WHERE h.parent_id = ( " +
            "    SELECT geonameid " +
            "    FROM allcountries " +
            "    WHERE name = #{name}" +
            ")")
    List<String> findChildrenByName(@Param("name") String name);

    @Select("SELECT c.name " +
            "FROM allcountries c " +
            "JOIN hierarchy h ON c.geonameid = h.child_id " +
            "WHERE h.parent_id = #{geonameId}")
    List<String> getChildrenById(Integer geonameId);

    @Select("select COUNT(1) FROM hierarchy WHERE parent_id = #{geonameId}")
    int isParent(Integer geonameId);
}
