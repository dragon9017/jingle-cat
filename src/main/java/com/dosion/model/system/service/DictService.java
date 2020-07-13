
package com.dosion.model.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.dosion.model.system.entity.Dict;
import com.dosion.utils.R;

/**
 * 字典表
 *
 * @author cdw
 * @date 2019/03/19
 */
public interface DictService extends IService<Dict> {

    /**
     * 根据ID 删除字典
     *
     * @param id
     * @return
     */
    R removeDict(Integer id);

    /**
     * 更新字典
     *
     * @param dict 字典
     * @return
     */
    R updateDict(Dict dict);
}
