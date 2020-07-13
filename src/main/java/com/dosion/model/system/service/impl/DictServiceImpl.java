
package com.dosion.model.system.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dosion.enums.DictTypeEnum;
import com.dosion.model.system.entity.Dict;
import com.dosion.model.system.entity.DictItem;
import com.dosion.model.system.mapper.DictItemMapper;
import com.dosion.model.system.mapper.DictMapper;
import com.dosion.model.system.service.DictService;
import com.dosion.utils.R;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 字典表
 *
 * @author cdw
 * @date 2019/03/19
 */
@Service
@AllArgsConstructor
public class DictServiceImpl extends ServiceImpl<DictMapper, Dict> implements DictService {
    private final DictItemMapper dictItemMapper;

    /**
     * 根据ID 删除字典
     *
     * @param id 字典ID
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public R removeDict(Integer id) {
        Dict dict = this.getById(id);
        // 系统内置
        if (DictTypeEnum.SYSTEM.getType().equals(dict.getSystem())) {
            return R.failed("系统内置字典不能删除");
        }

        baseMapper.deleteById(id);
        dictItemMapper.delete(Wrappers.<DictItem>lambdaQuery()
                .eq(DictItem::getDictId, id));
        return R.ok();
    }

    /**
     * 更新字典
     *
     * @param dict 字典
     * @return
     */
    @Override
    public R updateDict(Dict dict) {
        dict = this.getById(dict.getId());
        // 系统内置
        if (DictTypeEnum.SYSTEM.getType().equals(dict.getSystem())) {
            return R.failed("系统内置字典不能修改");
        }
        return R.ok(this.updateById(dict));
    }
}
