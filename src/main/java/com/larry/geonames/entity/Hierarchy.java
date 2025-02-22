package com.larry.geonames.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("hierarchy")
public class Hierarchy {

    private Integer parentId;

    private Integer childId;

    private String type;
}
