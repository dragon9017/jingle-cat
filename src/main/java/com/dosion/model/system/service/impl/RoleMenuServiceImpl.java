

package com.dosion.model.system.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dosion.constant.CacheConstants;
import com.dosion.model.system.entity.RoleMenu;
import com.dosion.model.system.mapper.RoleMenuMapper;
import com.dosion.model.system.service.RoleMenuService;
import lombok.AllArgsConstructor;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 角色菜单表 服务实现类
 * </p>
 *
 * @author cdw
 * @since 2017-10-29
 */
@Service
@AllArgsConstructor
public class RoleMenuServiceImpl extends ServiceImpl<RoleMenuMapper, RoleMenu> implements RoleMenuService {
    private final CacheManager cacheManager;

    /**
     * @param role
     * @param roleId  角色
     * @param menuIds 菜单ID拼成的字符串，每个id之间根据逗号分隔
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    @CacheEvict(value = CacheConstants.MENU_DETAILS, key = "#roleId + '_menu'")
    public Boolean saveRoleMenus(String role, Integer roleId, String menuIds) {
        this.remove(Wrappers.<RoleMenu>query().lambda()
                .eq(RoleMenu::getRoleId, roleId));

        if (StrUtil.isBlank(menuIds)) {
            return Boolean.TRUE;
        }
        List<RoleMenu> roleMenuList = Arrays
                .stream(menuIds.split(","))
                .map(menuId -> {
                    RoleMenu roleMenu = new RoleMenu();
                    roleMenu.setRoleId(roleId);
                    roleMenu.setMenuId(Integer.valueOf(menuId));
                    return roleMenu;
                }).collect(Collectors.toList());

        //清空userinfo
        cacheManager.getCache(CacheConstants.USER_DETAILS).clear();
        return this.saveBatch(roleMenuList);
    }
}
