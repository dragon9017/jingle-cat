package com.dosion.model.cat.controller;

import com.dosion.model.cat.entity.Device;
import com.dosion.model.cat.service.DeviceService;
import com.dosion.utils.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@Api(value = "device", tags = "设备管理")
@RequestMapping("${controller.prefix}/device")
public class DeviceController {
    private final DeviceService deviceService;

    @ApiOperation("分页查询合同表")
    @GetMapping("/page")
    public R<Device> page() {
        Device byId = deviceService.getById(1);
        return R.ok(byId);
    }
}
