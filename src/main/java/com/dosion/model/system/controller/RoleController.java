package com.dosion.model.system.controller;

import com.dosion.annotation.log.SysLog;
import com.dosion.annotation.permission.Permission;
import com.dosion.annotation.validate.ValidateFiled;
import com.dosion.annotation.validate.ValidateGroup;
import com.dosion.back.R;
import com.dosion.model.system.entity.User;
import com.dosion.model.system.entity.Role;
import com.dosion.model.system.service.RoleService;
import com.dosion.utils.SecurityUtils;
import lombok.AllArgsConstructor;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 角色 Controller
 *
 * @author cdw
 */
@AllArgsConstructor
@RestController
@RequestMapping("${controller.prefix}/${controller.system.prefix}/role")
public class RoleController {

    private final RoleService service;

    /**
     * 角色列表
     *
     * @param request
     * @param role
     * @return
     */
    @RequestMapping("list")
    @Permission("sys:role:view")
    public R<List<Role>> list(HttpServletRequest request, Role role) {
        User user = SecurityUtils.getUser();
        role.setCreateBy(user);
        List<Role> list = service.list(null);
        return new R(list);
    }

    /**
     * 根据id查询角色
     *
     * @param request
     * @param role
     * @return
     */
    @RequestMapping(value = "form")
    @ValidateGroup(fileds = {
            @ValidateFiled(index = 0, notNull = true, filedName = "id", msg = "缺少id参数")
    })
    @Permission("sys:role:view")
    public R<Role> form(Role role, HttpServletRequest request) {
        role = service.getById(role);
        return new R(role);
    }

    /**
     * 编辑角色
     *
     * @param request
     * @param role
     * @return
     */
    @SysLog("编辑角色")
    @PostMapping(value = "save")
    @ValidateGroup(fileds = {
            @ValidateFiled(index = 0, notNull = true, filedName = "name", maxLen = 30, msg = "缺少name参数或参数不合法！"),
            @ValidateFiled(index = 0, notNull = true, filedName = "enName", maxLen = 30, msg = "缺少enname参数或参数不合法！"),
            @ValidateFiled(index = 0, notNull = true, filedName = "level", minVal = 0, msg = "缺少levle参数或参数不合法！")
    })
    @Permission("sys:role:edit")
    public R<String> save(@RequestBody Role role, HttpServletRequest request, HttpServletResponse response) {

       /* if (role.getId() == null && !"true".equals(checkName(role.getOldName(), role.getName()))) {
            return new R<String>().error("保存角色" + role.getName() + "失败, 角色名已存在");
        }
        if (role.getId() == null && !"true".equals(checkEnname(role.getOldEnname(), role.getEnName()))) {
            return new R<String>().error("保存角色" + role.getName() + "失败, 英文名已存在");
        }*/
        User user = SecurityUtils.getUser();
        service.save(role);
        return new R().msg("保存角色" + role.getName() + "成功");
    }

    /**
     * 删除角色
     *
     * @param request
     * @param role
     * @return
     */
    @SysLog("删除角色")
    @RequestMapping(value = "delete")
    @ValidateGroup(fileds = {
            @ValidateFiled(index = 0, notNull = true, filedName = "id", msg = "缺少id参数")
    })
    @Permission("sys:role:delete")
    @Transactional(isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED)
    public R<String> delete(Role role, HttpServletRequest request, HttpServletResponse response) {
        role = service.getById(role);
        service.removeById(role.getId());
        return new R<String>().msg("删除角色成功");
    }

    /**
     * 验证角色名是否有效
     *
     * @param oldName
     * @param name
     * @return
     */
    @RequestMapping(value = "checkName")
    public String checkName(String oldName, String name) {
        /*if (name != null && name.equals(oldName)) {
            return "true";
        } else if (name != null && service.getRoleCountByName(name) <= 0) {
            return "true";
        }*/
        return "false";
    }

    /**
     * 验证角色英文名是否有效
     *
     * @param oldEnname
     * @param enName
     * @return
     */
    @RequestMapping(value = "check-enName")
    public String checkEnname(String oldEnname, String enName) {
        /*if (enName != null && enName.equals(oldEnname)) {
            return "true";
        } else if (enName != null && service.getRoleCountByEnName(enName) <= 0) {
            return "true";
        }*/
        return "false";
    }
}
