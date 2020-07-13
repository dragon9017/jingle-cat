
package com.dosion.model.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dosion.model.system.entity.DictItem;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 字典项
 *
 * @author cdw
 * @date 2019/03/19
 */
@Mapper
public interface DictItemMapper extends BaseMapper<DictItem> {

    /**
     * 根据id批量查询
     *
     * @param ids
     * @return
     */
    List<DictItem> findInids(List<Integer> ids);
}
