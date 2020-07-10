package com.dosion.model.system.controller;

import cn.hutool.core.util.ArrayUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.dosion.constant.CacheConstants;
import com.dosion.model.system.entity.Role;
import com.dosion.model.system.entity.User;
import com.dosion.model.system.service.MenuService;
import com.dosion.model.system.service.RoleService;
import com.dosion.model.system.service.SessionService;
import com.dosion.model.system.service.UserService;
import com.dosion.model.system.vo.MenuVO;
import com.dosion.utils.R;
import com.dosion.utils.SecurityUtils;
import com.dosion.utils.StringUtils;
import com.wf.captcha.ArithmeticCaptcha;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Api(value = "登录相关API", tags = {"登录接口"})
@RestController
@Slf4j
@AllArgsConstructor
@RequestMapping("${controller.prefix}/${controller.system.prefix}/login")
public class LoginController {
    private static final PasswordEncoder ENCODER = new BCryptPasswordEncoder();
    private static final Integer DEFAULT_IMAGE_WIDTH = 100;
    private static final Integer DEFAULT_IMAGE_HEIGHT = 40;
    private final RedisTemplate redisTemplate;
    private final SessionService<User> sessionService;
    private final UserService service;
    private final RoleService roleService;
    private final MenuService menuService;

    @GetMapping("/code/{randomStr}")
    public void registerImage(@PathVariable String randomStr, HttpServletResponse response) throws IOException {
        ArithmeticCaptcha captcha = null;
        String result = null;
        do {
            captcha = new ArithmeticCaptcha(DEFAULT_IMAGE_WIDTH, DEFAULT_IMAGE_HEIGHT);
            result = captcha.text();
        } while (Integer.valueOf(result) <= 0);
        //保存验证码信息
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.opsForValue().set(CacheConstants.DEFAULT_CODE_KEY + randomStr, result
                , CacheConstants.CODE_TIME, TimeUnit.MINUTES);
        // 转换流信息写出
        response.setContentType("image/jpeg");
        captcha.out(response.getOutputStream());
    }


    @ApiOperation("用户登录")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "loginName", value = "loginName", defaultValue = "1", dataType = "string", paramType = "query", required = true),
            @ApiImplicitParam(name = "password", value = "password", defaultValue = "1", dataType = "string", paramType = "query", required = true)
    })
    @GetMapping(value = "login/{randomStr}")
   /* @ValidateGroup(fileds = {
            @ValidateFiled(index = 0, notNull = true, filedName = "loginName", msg = "缺少loginName参数"),
            @ValidateFiled(index = 0, notNull = true, filedName = "password", msg = "缺少password参数")
    })*/
    public R<String> login(@PathVariable String randomStr, String username, String password, String captcha, HttpServletRequest request) {
        Object codeObj = redisTemplate.opsForValue().get(CacheConstants.DEFAULT_CODE_KEY + randomStr);
        if (codeObj == null) {
            return R.failed("验证码错误！");
        }
        if (!codeObj.equals(captcha)) {
            return R.failed("验证码错误！");
        }
        User queryUser = new User();
        queryUser.setUsername(username);
        User user = service.getOne(Wrappers.query(queryUser));
        if (user == null) {
            return R.failed("用户名不存在！");
        } else if (!ENCODER.matches(password, user.getPassword())) {
            return R.failed("用户名密码错误");
        } else if (!user.getLoginFlag()) {
            return R.failed("用户被禁用");
        }
        //赋值角色
        Role role = roleService.getById(user.getRoleId());
        user.setRole(role);

        //设置权限列表（menu.permission）
        Set<String> permissions = new HashSet<>();
        List<String> collect = menuService.findMenuByRoleId(user.getRoleId()).stream()
                .filter(menuVo -> org.apache.commons.lang.StringUtils.isNotEmpty(menuVo.getPermission()))
                .map(MenuVO::getPermission)
                .collect(Collectors.toList());
        permissions.addAll(collect);
        user.setPermissions(ArrayUtil.toArray(permissions, String.class));


        //更新用户登录ip登录日期
        String login_ip = StringUtils.getRemoteAddr(request);
        user.setLoginDate(new Date());
        user.setLoginIp(login_ip);
        service.updateById(user);
        //设置token
        String token = SecurityUtils.setUser(user);
        return R.ok(token);
    }


    @ApiOperation("用户信息")
    @GetMapping(value = "info")
    public R<User> info() {
        User session = SecurityUtils.getUser();
        if (session != null) {
            session.setPassword(null);
        }
        return R.ok(session);
    }


    @ApiOperation("登出")
    @RequestMapping(value = "out-login", method = RequestMethod.POST)
    public R outLogin(HttpServletRequest request) {
        sessionService.removeSession(request);
        return R.ok().setMsg("已安全退出！");
    }


}
