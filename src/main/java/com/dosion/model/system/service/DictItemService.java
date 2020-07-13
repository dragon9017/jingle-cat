
package com.dosion.model.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.dosion.model.system.entity.DictItem;
import com.dosion.utils.R;

import java.util.List;

/**
 * 字典项
 *
 * @author cdw
 * @date 2019/03/19
 */
public interface DictItemService extends IService<DictItem> {

    /**
     * 删除字典项
     *
     * @param id 字典项ID
     * @return
     */
    R removeDictItem(Integer id);

    /**
     * 更新字典项
     *
     * @param item 字典项
     * @return
     */
    R updateDictItem(DictItem item);

    /**
     * 根据id批量查询
     *
     * @param ids
     * @return
     */
    List<DictItem> findInids(List<Integer> ids);
}
