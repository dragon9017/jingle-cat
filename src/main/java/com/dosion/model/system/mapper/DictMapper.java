

package com.dosion.model.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dosion.model.system.entity.Dict;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 字典表 Mapper 接口
 * </p>
 *
 * @author cdw
 * @since 2017-11-19
 */
@Mapper
public interface DictMapper extends BaseMapper<Dict> {

}
