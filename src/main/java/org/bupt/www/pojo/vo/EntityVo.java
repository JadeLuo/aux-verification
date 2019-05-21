package org.bupt.www.pojo.vo;

import lombok.Data;
import org.bupt.www.pojo.po.EntityMark;
import org.springframework.beans.BeanUtils;

@Data
public class EntityVo extends EntityMark {

    private long totalCount;

    private long passedCount;

    private long deniedCount;

    public EntityVo(EntityMark mark){
        BeanUtils.copyProperties(mark, this);
    }

}
