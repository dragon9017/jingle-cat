

package com.dosion.model.system.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dosion.constant.CacheConstants;
import com.dosion.constant.Constant;
import com.dosion.enums.MenuTypeEnum;
import com.dosion.model.system.dto.MenuTree;
import com.dosion.model.system.entity.Menu;
import com.dosion.model.system.entity.RoleMenu;
import com.dosion.model.system.entity.User;
import com.dosion.model.system.mapper.MenuMapper;
import com.dosion.model.system.mapper.RoleMenuMapper;
import com.dosion.model.system.service.MenuService;
import com.dosion.model.system.vo.MenuVO;
import com.dosion.utils.R;
import com.dosion.utils.SecurityUtils;
import com.dosion.utils.TreeUtil;
import lombok.AllArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * <p>
 * 菜单权限表 服务实现类
 * </p>
 *
 * @author cdw
 * @since 2017-10-29
 */
@Service
@AllArgsConstructor
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu> implements MenuService {
    private final RoleMenuMapper roleMenuMapper;

    @Override
    @Cacheable(value = CacheConstants.MENU_DETAILS, key = "#roleId  + '_menu'", unless = "#result == null")
    public List<MenuVO> findMenuByRoleId(Integer roleId) {
        return baseMapper.listMenusByRoleId(roleId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @CacheEvict(value = CacheConstants.MENU_DETAILS, allEntries = true)
    public R removeMenuById(Integer id) {
        // 查询父节点为当前节点的节点
        List<Menu> menuList = this.list(Wrappers.<Menu>query()
                .lambda().eq(Menu::getParentId, id));
        if (CollUtil.isNotEmpty(menuList)) {
            return R.failed("菜单含有下级不能删除");
        }

        roleMenuMapper.delete(Wrappers.<RoleMenu>query()
                .lambda().eq(RoleMenu::getMenuId, id));
        //删除当前菜单及其子菜单
        return R.ok(this.removeById(id));
    }

    @Override
    @CacheEvict(value = CacheConstants.MENU_DETAILS, allEntries = true)
    public Boolean updateMenuById(Menu menu) {
        return this.updateById(menu);
    }

    /**
     * 构建树查询
     * 1. 不是懒加载情况，查询全部
     * 2. 是懒加载，根据parentId 查询
     * 2.1 父节点为空，则查询ID -1
     *
     * @param lazy     是否是懒加载
     * @param parentId 父节点ID
     * @return
     */
    @Override
    public List<MenuTree> treeMenu(boolean lazy, Integer parentId) {
        //获取用户角色
        User user = SecurityUtils.getUser();
        List<Integer> collect = null;
        if (!user.getRoleId().equals(1) && !user.getRoleId().equals(2)) {
            collect = baseMapper.listMenusByRoleId(user.getRoleId()).stream().map(MenuVO::getId).collect(Collectors.toList());
        }

        if (!lazy) {
            List<Menu> menu = baseMapper.selectList(Wrappers.<Menu>lambdaQuery().orderByAsc(Menu::getSort));
            if (user.getRoleId().equals(1) || user.getRoleId().equals(2)) {
                return TreeUtil.buildTree(menu, Constant.MENU_TREE_ROOT_ID);
            }
            List<Menu> menuList = new ArrayList<>();
            for (Menu sysMenu : menu) {
                if (collect.contains(sysMenu.getId())) {
                    menuList.add(sysMenu);
                }
            }
            return TreeUtil.buildTree(menuList, Constant.MENU_TREE_ROOT_ID);
        }

        Integer parent = parentId == null ? Constant.MENU_TREE_ROOT_ID : parentId;

        List<Menu> menu = baseMapper.selectList(Wrappers.<Menu>lambdaQuery().eq(Menu::getParentId, parent).orderByAsc(Menu::getSort));
        if (user.getRoleId().equals(1) || user.getRoleId().equals(2)) {
            return TreeUtil.buildTree(menu, parent);
        }

        List<Menu> menuList = new ArrayList<>();
        for (Menu sysMenu : menu) {
            if (collect.contains(sysMenu.getId())) {
                menuList.add(sysMenu);
            }
        }
        return TreeUtil.buildTree(menuList, parent);
    }

    /**
     * 查询菜单
     *
     * @param all      全部菜单
     * @param type     类型
     * @param parentId 父节点ID
     * @return
     */
    @Override
    public List<MenuTree> filterMenu(Set<MenuVO> all, String type, Integer parentId) {
        List<MenuTree> menuTreeList = all.stream()
                .filter(menuTypePredicate(type))
                .map(MenuTree::new)
                .sorted(Comparator.comparingInt(MenuTree::getSort))
                .collect(Collectors.toList());
        Integer parent = parentId == null ? Constant.MENU_TREE_ROOT_ID : parentId;
        return TreeUtil.build(menuTreeList, parent);
    }

    /**
     * menu 类型断言
     *
     * @param type 类型
     * @return Predicate
     */
    private Predicate<MenuVO> menuTypePredicate(String type) {
        return vo -> {
            if (MenuTypeEnum.TOP_MENU.getDescription().equals(type)) {
                return MenuTypeEnum.TOP_MENU.getType().equals(vo.getType());
            }
            // 其他查询 左侧 + 顶部
            return !MenuTypeEnum.BUTTON.getType().equals(vo.getType());
        };
    }
}
