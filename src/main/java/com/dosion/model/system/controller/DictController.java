

package com.dosion.model.system.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.dosion.annotation.log.SysLog;
import com.dosion.model.system.entity.Dict;
import com.dosion.model.system.entity.DictItem;
import com.dosion.model.system.service.DictItemService;
import com.dosion.model.system.service.DictService;
import com.dosion.utils.R;
import io.swagger.annotations.Api;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * <p>
 * 字典表 前端控制器
 * </p>
 *
 * @author cdw
 * @since 2019-03-19
 */
@RestController
@AllArgsConstructor
@RequestMapping("${controller.prefix}/${controller.system.prefix}/dict")
@Api(value = "dict", tags = "字典管理")
public class DictController {
    private final DictService sysDictService;
    private final DictItemService sysDictItemService;

    /**
     * 通过ID查询字典信息
     *
     * @param id ID
     * @return 字典信息
     */
    @GetMapping("/{id}")
    public R getById(@PathVariable Integer id) {
        return R.ok(sysDictService.getById(id));
    }

    /**
     * 分页查询字典信息
     *
     * @param page 分页对象
     * @return 分页对象
     */
    @GetMapping("/page")
    public R<IPage> getDictPage(Page page, Dict dict) {
        return R.ok(sysDictService.page(page, Wrappers.query(dict)));
    }

    /**
     * 通过字典类型查找字典
     *
     * @param type 类型
     * @return 同类型字典
     */
    @GetMapping("/type/{type}")
    public R getDictByType(@PathVariable String type) {

        return R.ok(sysDictItemService.list(Wrappers
                .<DictItem>query().lambda()
                .eq(DictItem::getType, type)));
    }


    /**
     * 添加字典
     *
     * @param dict 字典信息
     * @return success、false
     */
    @SysLog("添加字典")
    @PostMapping
    @PreAuthorize("@pms.hasPermission('sys_dict_add')")
    public R save(@Valid @RequestBody Dict dict) {
        return R.ok(sysDictService.save(dict));
    }

    /**
     * 删除字典，并且清除字典缓存
     *
     * @param id ID
     * @return R
     */
    @SysLog("删除字典")
    @DeleteMapping("/{id}")
    @PreAuthorize("@pms.hasPermission('sys_dict_del')")
    public R removeById(@PathVariable Integer id) {
        return sysDictService.removeDict(id);
    }

    /**
     * 修改字典
     *
     * @param dict 字典信息
     * @return success/false
     */
    @PutMapping
    @SysLog("修改字典")
    @PreAuthorize("@pms.hasPermission('sys_dict_edit')")
    public R updateById(@Valid @RequestBody Dict dict) {
        return sysDictService.updateDict(dict);
    }

    /**
     * 分页查询
     *
     * @param page        分页对象
     * @param dictItem 字典项
     * @return
     */
    @GetMapping("/item/page")
    public R getSysDictItemPage(Page page, DictItem dictItem) {
        return R.ok(sysDictItemService.page(page, Wrappers.query(dictItem)));
    }


    /**
     * 通过id查询字典项
     *
     * @param id id
     * @return R
     */
    @GetMapping("/item/{id}")
    public R getDictItemById(@PathVariable("id") Integer id) {
        return R.ok(sysDictItemService.getById(id));
    }

    /**
     * 新增字典项
     *
     * @param dictItem 字典项
     * @return R
     */
    @SysLog("新增字典项")
    @PostMapping("/item")
    public R save(@RequestBody DictItem dictItem) {
        return R.ok(sysDictItemService.save(dictItem));
    }

    /**
     * 修改字典项
     *
     * @param dictItem 字典项
     * @return R
     */
    @SysLog("修改字典项")
    @PutMapping("/item")
    public R updateById(@RequestBody DictItem dictItem) {
        return sysDictItemService.updateDictItem(dictItem);
    }

    /**
     * 通过id删除字典项
     *
     * @param id id
     * @return R
     */
    @SysLog("删除字典项")
    @DeleteMapping("/item/{id}")
    public R removeDictItemById(@PathVariable Integer id) {
        return sysDictItemService.removeDictItem(id);
    }
}
