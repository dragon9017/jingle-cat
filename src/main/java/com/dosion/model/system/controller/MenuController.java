

package com.dosion.model.system.controller;

import com.dosion.annotation.log.SysLog;
import com.dosion.annotation.permission.Permission;
import com.dosion.model.system.entity.Menu;
import com.dosion.model.system.entity.User;
import com.dosion.model.system.service.MenuService;
import com.dosion.model.system.vo.MenuVO;
import com.dosion.utils.R;
import com.dosion.utils.SecurityUtils;
import io.swagger.annotations.Api;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author cdw
 * @date 2017/10/31
 */
@RestController
@AllArgsConstructor
@RequestMapping("${controller.prefix}/${controller.system.prefix}/menu")
@Api(value = "menu", tags = "菜单")
public class MenuController {
    private final MenuService menuService;

    /**
     * 返回当前用户的树形菜单集合
     *
     * @param type     类型
     * @param parentId 父节点ID
     * @return 当前用户的树形菜单
     */
    @GetMapping
    public R getUserMenu(String type, Integer parentId) {
        // 获取符合条件的菜单
        Set<MenuVO> all = new HashSet<>();
        User user = SecurityUtils.getUser();
        List<MenuVO> menuList = menuService.findMenuByRoleId(user.getRoleId());
        all.addAll(menuList);
        return R.ok(menuService.filterMenu(all, type, parentId));
    }

    /**
     * 返回树形菜单集合
     *
     * @param lazy     是否是懒加载
     * @param parentId 父节点ID
     * @return 树形菜单
     */
    @GetMapping(value = "/tree")
    public R getTree(boolean lazy, Integer parentId) {
        return R.ok(menuService.treeMenu(lazy, parentId));
    }

    /**
     * 返回角色的菜单集合
     *
     * @param roleId 角色ID
     * @return 属性集合
     */
    @GetMapping("/tree/{roleId}")
    public R getRoleTree(@PathVariable Integer roleId) {
        return R.ok(menuService.findMenuByRoleId(roleId)
                .stream()
                .map(MenuVO::getId)
                .collect(Collectors.toList()));
    }

    /**
     * 通过ID查询菜单的详细信息
     *
     * @param id 菜单ID
     * @return 菜单详细信息
     */
    @GetMapping("/{id}")
    public R getById(@PathVariable Integer id) {
        return R.ok(menuService.getById(id));
    }

    /**
     * 新增菜单
     *
     * @param menu 菜单信息
     * @return success/false
     */
    @SysLog("新增菜单")
    @PostMapping
    @Permission("sys_menu_add")
    public R save(@Valid @RequestBody Menu menu) {
        return R.ok(menuService.save(menu));
    }

    /**
     * 删除菜单
     *
     * @param id 菜单ID
     * @return success/false
     */
    @SysLog("删除菜单")
    @DeleteMapping("/{id}")
    @Permission("sys_menu_del")
    public R removeById(@PathVariable Integer id) {
        return menuService.removeMenuById(id);
    }

    /**
     * 更新菜单
     *
     * @param menu
     * @return
     */
    @SysLog("更新菜单")
    @PutMapping
    @Permission("sys_menu_edit")
    public R update(@Valid @RequestBody Menu menu) {
        return R.ok(menuService.updateMenuById(menu));
    }

}
