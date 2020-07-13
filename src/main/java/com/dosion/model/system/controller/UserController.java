package com.dosion.model.system.controller;

import com.dosion.annotation.log.SysLog;
import com.dosion.annotation.permission.Permission;
import com.dosion.annotation.validate.ValidateFiled;
import com.dosion.annotation.validate.ValidateGroup;
import com.dosion.model.system.entity.User;
import com.dosion.model.system.service.DictService;
import com.dosion.model.system.service.RoleService;
import com.dosion.model.system.service.UserService;
import com.dosion.utils.R;
import com.dosion.utils.SecurityUtils;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 用户 controller
 *
 * @author 陈登文
 */
@RestController
@AllArgsConstructor
@Api(value = "manager", tags = "用户管理")
@Slf4j
@RequestMapping("${controller.prefix}/${controller.system.prefix}/manager")
public class UserController {
    private final UserService service;
    private final RoleService roleService;
    private final DictService dictService;

    @ApiOperation("分页查询管理员")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "limit", defaultValue = "1", dataType = "int", paramType = "query", required = true),
            @ApiImplicitParam(name = "page", defaultValue = "1", dataType = "int", paramType = "query", required = true)
    })
    @RequestMapping(value = "list")
    @Permission("sys:manager:view")
    public R<List<User>> fondManagerByPage(User model, Integer page, Integer limit, HttpServletRequest request) {
        PageInfo<User> managerByPage = service.page(null);
        return R.ok(managerByPage.getList());//put("count", managerByPage.getTotal());
    }


    /**
     * 查询所有人员
     *
     * @param request
     * @param model
     * @return
     */
    @RequestMapping(value = "findAll", method = RequestMethod.POST)
    @Permission("sys:manager:view")
    public R<List<User>> findAll(User model, HttpServletRequest request) {
        User user = SecurityUtils.getUser();
        //如果当前登录用户不是超级管理员
        //查询的数据则不包含管理员
        model.setId(user.getId());
        model.setRole(user.getRole());
        List<User> list = service.list(null);
        return R.ok(list);
    }


    /**
     * @param @param model
     * @param @param request
     * @param @param response    设定文件
     * @return void    返回类型
     * @throws
     * @Title: deleteManager
     * @Description: TODO(删除管理员)
     * @author 陈登文
     * @date 2018年1月18日 下午5:45:47
     */
    @SysLog("删除管理员")
    @RequestMapping(value = "delete", method = RequestMethod.POST)
    @ValidateGroup(fileds = {
            @ValidateFiled(index = 0, notNull = true, filedName = "id", msg = "缺少id参数！")
    })
    @Permission("sys:manager:delete")
    @Transactional(isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public R deleteManager(User model, HttpServletRequest request) {
        if (model.getId() == 1) {
            return R.failed().setMsg("超级管理员不得删除");
        }
        service.removeById(model);
        return R.failed().setMsg("删除成功！");
    }
}
