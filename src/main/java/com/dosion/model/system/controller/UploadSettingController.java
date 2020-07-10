package com.dosion.model.system.controller;

import com.dosion.annotation.permission.Permission;
import com.dosion.back.R;
import com.dosion.model.system.entity.UploadSetting;
import com.dosion.model.system.service.UploadSettingService;
import io.swagger.annotations.Api;
import lombok.AllArgsConstructor;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@Api(value = "upload-setting", tags = "上传设置")
@RequestMapping(value = "${controller.prefix}/upload-setting")
public class UploadSettingController {

    private final UploadSettingService uploadSettingService;

    /**
     * 获取上传设置
     *
     * @return
     */
    @RequestMapping("get")
    @Permission("upload:setting:view")
    public R<String> get() {
        UploadSetting uploadSetting = uploadSettingService.getById(1);
        if (uploadSetting != null) {
            return new R(uploadSetting);
        } else {
            return new R("找不到资源");
        }
    }

    /**
     * 更新设置
     *
     * @param uploadSetting
     * @return
     */
    @PutMapping
    @Permission("upload:setting:edit")
    @Transactional(isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public R<String> update(UploadSetting uploadSetting) {
        uploadSetting.setId(1);
        uploadSettingService.save(uploadSetting);
        return new R("更新文件上传配置成功!");
    }
}
