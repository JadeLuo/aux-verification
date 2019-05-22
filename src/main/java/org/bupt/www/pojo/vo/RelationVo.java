package org.bupt.www.pojo.vo;

import lombok.Data;
import org.bupt.www.pojo.po.RelationMark;
import org.bupt.www.pojo.po.RelationReflect;
import org.springframework.beans.BeanUtils;

import java.util.List;
import java.util.Map;

@Data
public class RelationVo extends RelationMark {

    private List<RelationReflect> reflects;

    private String relationName;

    private Long relationNo;

    private long totalCount;

    private long passedCount;

    private long deniedCount;

    private long reviewingCount;

    public RelationVo(RelationMark mark){
        BeanUtils.copyProperties(mark, this);
    }

}
