package com.dosion.model.system.controller;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.dosion.annotation.log.SysLog;
import com.dosion.model.system.entity.Role;
import com.dosion.model.system.service.RoleMenuService;
import com.dosion.model.system.service.RoleService;
import com.dosion.model.system.vo.RoleVo;
import com.dosion.utils.R;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
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
    private final RoleService roleService;
    private final RoleMenuService roleMenuService;

    /**
     * 通过ID查询角色信息
     *
     * @param id ID
     * @return 角色信息
     */
    @GetMapping("/{id}")
    public R getById(@PathVariable Integer id) {
        return R.ok(roleService.getById(id));
    }

    /**
     * 添加角色
     *
     * @param role 角色信息
     * @return success、false
     */
    @SysLog("添加角色")
    @PostMapping
    @PreAuthorize("@pms.hasPermission('sys_role_add')")
    public R save(@Valid @RequestBody Role role) {
        return R.ok(roleService.save(role));
    }

    /**
     * 修改角色
     *
     * @param role 角色信息
     * @return success/false
     */
    @SysLog("修改角色")
    @PutMapping
    @PreAuthorize("@pms.hasPermission('sys_role_edit')")
    public R update(@Valid @RequestBody Role role) {
        return R.ok(roleService.updateById(role));
    }

    /**
     * 删除角色
     *
     * @param id
     * @return
     */
    @SysLog("删除角色")
    @DeleteMapping("/{id}")
    @PreAuthorize("@pms.hasPermission('sys_role_del')")
    public R removeById(@PathVariable Integer id) {
        return R.ok(roleService.removeRoleById(id));
    }

    /**
     * 获取角色列表
     *
     * @return 角色列表
     */
    @GetMapping("/list")
    public R listRoles() {
        return R.ok(roleService.list(Wrappers.emptyWrapper()));
    }

    /**
     * 分页查询角色信息
     *
     * @param page 分页对象
     * @return 分页对象
     */
    @GetMapping("/page")
    public R<Page<Role>> getRolePage(Page page, String name) {
        QueryWrapper<Role> query = Wrappers.query();
        if (StrUtil.isNotEmpty(name)) {
            query = query.like("role_name", name);
        }
        return R.ok(roleService.page(page, query));
    }

    /**
     * 更新角色菜单
     *
     * @param roleVo 角色对象
     * @return success、false
     */
    @SysLog("更新角色菜单")
    @PutMapping("/menu")
    @PreAuthorize("@pms.hasPermission('sys_role_perm')")
    public R saveRoleMenus(@RequestBody RoleVo roleVo) {
        Role role = roleService.getById(roleVo.getRoleId());
        return R.ok(roleMenuService.saveRoleMenus(role.getRoleCode()
                , roleVo.getRoleId(), roleVo.getMenuIds()));
    }

    /**
     * 通过角色ID 查询角色列表
     *
     * @param roleIdList 角色ID
     * @return
     */
    @PostMapping("/getRoleList")
    public R getRoleList(@RequestBody List<String> roleIdList) {
        return R.ok(roleService.listByIds(roleIdList));
    }
}
