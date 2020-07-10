package com.dosion.model.system.controller;

import com.github.pagehelper.PageInfo;
import com.dosion.back.R;
import com.dosion.model.system.entity.SysLog;
import com.dosion.model.system.service.LogService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 日志 controller
 * @author 陈登文
 */
@RestController
@AllArgsConstructor
@RequestMapping("${controller.prefix}/${controller.system.prefix}/log")
public class LogController {
    private final LogService logService;

    /**
     * 获取日志列表
     *
     * @param req
     * @param model
     * @param rep
     * @return
     */
    @RequestMapping(value = "list",method = RequestMethod.GET)
    public R<List<SysLog>> list(SysLog model, Integer page, Integer limit, HttpServletRequest req, HttpServletResponse rep) {
        PageInfo<SysLog> byPage = logService.findByPage(model, page, limit);
        return new R<List<SysLog>>(byPage.getList()).put("count", byPage.getTotal());
    }
}
