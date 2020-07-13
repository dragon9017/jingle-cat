
package com.dosion.model.system.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dosion.constant.CacheConstants;
import com.dosion.enums.DictTypeEnum;
import com.dosion.model.system.entity.Dict;
import com.dosion.model.system.entity.DictItem;
import com.dosion.model.system.mapper.DictItemMapper;
import com.dosion.model.system.service.DictItemService;
import com.dosion.model.system.service.DictService;
import com.dosion.utils.R;
import lombok.AllArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 字典项
 *
 * @author cdw
 * @date 2019/03/19
 */
@Service
@AllArgsConstructor
public class DictItemServiceImpl extends ServiceImpl<DictItemMapper, DictItem> implements DictItemService {
    private final DictService dictService;

    /**
     * 删除字典项
     *
     * @param id 字典项ID
     * @return
     */
    @Override
    @CacheEvict(value = CacheConstants.DICT_DETAILS, allEntries = true)
    public R removeDictItem(Integer id) {
        //根据ID查询字典ID
        DictItem dictItem = this.getById(id);
        Dict dict = dictService.getById(dictItem.getDictId());
        // 系统内置
        if (DictTypeEnum.SYSTEM.getType().equals(dict.getSystem())) {
            return R.failed("系统内置字典项目不能删除");
        }
        return R.ok(this.removeById(id));
    }

    /**
     * 更新字典项
     *
     * @param item 字典项
     * @return
     */
    @Override
    @CacheEvict(value = CacheConstants.DICT_DETAILS, allEntries = true)
    public R updateDictItem(DictItem item) {
        //查询字典
        Dict dict = dictService.getById(item.getDictId());
        // 系统内置
        if (DictTypeEnum.SYSTEM.getType().equals(dict.getSystem())) {
            return R.failed("系统内置字典项目不能删除");
        }
        return R.ok(this.updateById(item));
    }

    /**
     * 根据id批量查询
     *
     * @param ids
     * @return
     */
    @Override
    public List<DictItem> findInids(List<Integer> ids) {
        return baseMapper.findInids(ids);
    }
}
