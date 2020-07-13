package com.dosion.model.system.controller;

import com.dosion.annotation.log.SysLog;
import com.dosion.annotation.permission.Permission;
import com.dosion.annotation.validate.ValidateFiled;
import com.dosion.annotation.validate.ValidateGroup;
import com.dosion.model.system.entity.Dict;
import com.dosion.model.system.entity.User;
import com.dosion.model.system.service.DictService;
import com.dosion.utils.R;
import com.dosion.utils.SecurityUtils;
import com.dosion.utils.StringUtils;
import com.github.pagehelper.PageInfo;
import lombok.AllArgsConstructor;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("${controller.prefix}/${controller.system.prefix}/dict")
public class DictController {
    private final DictService service;

    /**
     * 查询字典
     *
     * @param req
     * @param dict
     * @param rep
     * @return
     */
    @RequestMapping("/list")
    public R<List<Dict>> list(Dict dict, Integer page, Integer limit, HttpServletRequest req, HttpServletResponse rep) {
        PageInfo<Dict> byPage = service.findByPage(dict, page, limit);
        return R.ok(byPage.getList());//.put("count", byPage.getTotal());
    }

    /**
     * 查询字典
     *
     * @param req
     * @param dict
     * @param rep
     * @return
     */
    @RequestMapping("/findAll")
    public R<List<Dict>> findAll(HttpServletRequest req, Dict dict, HttpServletResponse rep) {
        List<Dict> list = service.findAll(dict);
        return R.ok(list);
    }

    /**
     * 保存字典
     *
     * @param req
     * @param dict
     * @param rep
     * @return
     */
    @SysLog("编辑字典")
    @PostMapping("save")
    @Permission("sys:dict:edit")
    @Transactional(isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED)
    public R save(@RequestBody Dict dict, HttpServletRequest req, HttpServletResponse rep) {
        User user = SecurityUtils.getUser();
        service.save(dict, user);
        return R.ok().setMsg("编辑字典成功");
    }


    /**
     * 删除字典
     *
     * @param request
     * @param dict
     * @param response
     * @return
     */
    @SysLog("删除字典")
    @RequestMapping(value = "delete")
    @Permission("sys:dict:edit")
    @Transactional(isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED)
    public R delete(HttpServletRequest request, Dict dict, HttpServletResponse response) {
        service.delete(dict);
        return R.ok().setMsg("删除字典成功");
    }

    /**
     * 获得type的字典列表
     *
     * @param dict
     * @param request
     * @param response
     */
    @RequestMapping(value = "getDictByType")
    public R<List<Dict>> getDictByType(HttpServletRequest request, Dict dict, HttpServletResponse response) {
        List<Dict> list = new ArrayList();
        if (StringUtils.isNotBlank(dict.getType())) {
            list = service.findAll(dict);
        }
        return R.ok(list);
    }

    /**
     * 根据类型获取字典信息
     */
    @GetMapping(value = "getByTypeDict")
    @ValidateGroup(fileds = {
            @ValidateFiled(index = 0, notNull = true, filedName = "type", msg = "缺少type参数！")
    })
    public R<Dict> getByTypeDic(Dict dict, HttpServletRequest request, HttpServletResponse response) {
        Dict dict1 = service.getDict(dict);
        return R.ok(dict1).setMsg("获取字典信息成功!");
    }

}
