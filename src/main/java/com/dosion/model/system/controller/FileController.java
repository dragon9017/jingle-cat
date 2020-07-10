package com.dosion.model.system.controller;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.IoUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.dosion.constant.Constant;
import com.dosion.minio.service.MinioTemplate;
import com.dosion.model.system.entity.UploadSetting;
import com.dosion.model.system.service.UploadSettingService;
import com.dosion.model.system.entity.File;
import com.dosion.model.system.service.FileService;
import com.dosion.utils.FileUtils;
import com.dosion.utils.R;
import io.swagger.annotations.Api;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
@AllArgsConstructor
@Api(value = "file", tags = "文件管理")
@RequestMapping("${controller.prefix}/${controller.system.prefix}/file")
public class FileController {
    private final FileService service;
    private final UploadSettingService uploadSettingService;
    private final MinioTemplate minioTemplate;

    /**
     * 上传
     *
     * @param file
     * @return
     */
    @PostMapping("/upload")
    public R<Map<String, String>> uploadFile(@RequestParam(value = "file", required = false) MultipartFile file) {
        // 源文件名
        String oldFileName = file.getOriginalFilename();
        if (oldFileName.indexOf(".") == -1) {
            return R.failed("上传文件类型不支持");
        }
        UploadSetting sett = uploadSettingService.getById(1);
        // 文件后缀名
        String fileType = sett.getFileType();
        String videoType = sett.getVideoType();
        String type = "";
        if (fileType != null && fileType != "") {
            type += fileType + ",";
        }
        if (videoType != null && videoType != "") {
            type += videoType;
        }
        String ext = oldFileName.substring(oldFileName.lastIndexOf(".") + 1);
        if (!FileUtils.checkExt(ext, type)) {
            return R.failed("不允许上传的文件类型!");
        }
        double size = FileUtils.getFileSizeGb(file.getSize());
        // 上传文件限制
        String[] str = sett.getFileType().split(",");
        boolean b = false;
        for (String s : str) {
            if (s.equals(ext)) {
                b = true;
                if (size > sett.getImgSize()) {
                    return R.failed("上传图片太大!");
                }
            }
        }
        String[] voidStr = sett.getVideoType().split(",");
        boolean a = false;
        if (!b) {
            // 视频文件限制
            for (String voidS : voidStr) {
                if (voidS.equals(ext)) {
                    a = true;
                    if (size > sett.getVideoSize()) {
                        return R.failed("上传视视频太大!");
                    }
                }
            }
            if (!a) {
                return R.failed("上传文件不符合规则!");
            }
        }
        // 文件新名
        String fileName = IdUtil.simpleUUID() + StrUtil.DOT + FileUtil.extName(file.getOriginalFilename());
        Map<String, String> resultMap = new HashMap<>(4);
        resultMap.put("bucketName", Constant.BUCKET_NAME);
        resultMap.put("fileName", fileName);
        resultMap.put("name", file.getOriginalFilename());
        try {
            minioTemplate.putObject(Constant.BUCKET_NAME, fileName, file.getInputStream());
        } catch (Exception e) {
            log.error("上传失败", e);
            return R.failed(e.getLocalizedMessage());
        }
        //保存文件
        File filedo = new File();
        filedo.setUrl(Constant.BUCKET_NAME + "-" + fileName);
        filedo.setExt(ext);
        filedo.setName(oldFileName);
        service.save(filedo);
        return R.ok(resultMap);
    }


    /**
     * 获取文件
     *
     * @param fileName 文件空间/名称
     * @param response
     * @return
     */
    @GetMapping("/{fileName:.*}")
    public void file(@PathVariable String fileName, HttpServletResponse response) {
        Integer separator = fileName.lastIndexOf(StrUtil.DASHED);
        try (InputStream inputStream = minioTemplate.getObject(fileName.substring(0, separator), fileName.substring(separator + 1, fileName.length()))) {
            File queryFile = new File();
            queryFile.setUrl(fileName);
            File file = service.getOne(Wrappers.query(queryFile));
            if(file!=null){
                response.setHeader("Content-Disposition", "attachment; filename="+file.getName());
            }
            response.setContentType("application/octet-stream; charset=UTF-8");
            IoUtil.copy(inputStream, response.getOutputStream());
        } catch (Exception e) {
            log.error("文件读取异常", e);
        }
    }

}
