package com.dosion.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.DefaultResourceLoader;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;

/**
 * 下载工具类
 * @author 陈登文
 */
@Slf4j
public class DownLoadUtil {

    private static final String FIREFOX = "Firefox";
    private static final String CHROME = "Chrome";

    /**
     * @param @param  request
     * @param @param  pFileName
     * @param @return
     * @param @throws UnsupportedEncodingException
     * @return String
     * @throws
     * @Title: encodeChineseDownloadFileName
     */


    public static String encodeChineseDownloadFileName(HttpServletRequest request, String pFileName)
            throws UnsupportedEncodingException {

        String filename = null;
        String agent = request.getHeader("USER-AGENT");
        if (null != agent) {
            if (-1 != agent.indexOf(FIREFOX)) {
                filename = "=?UTF-8?B?" + (new String(org.apache.commons.codec.binary.Base64.encodeBase64(pFileName.getBytes("UTF-8")))) + "?=";
            } else if (-1 != agent.indexOf(CHROME)) {
                filename = new String(pFileName.getBytes(), "ISO8859-1");
            } else {
                filename = java.net.URLEncoder.encode(pFileName, "UTF-8");
                filename = StringUtils.replace(filename, "+", "%20");
            }
        } else {
            filename = pFileName;
        }

        return filename;
    }

    public static String getPysicalPath(String virtualPath, HttpServletRequest request) {
        //获得根绝对路径
        String physicalPath = getProjectPath();
        //获得项目路径
        String basePath = request.getContextPath();
        if (virtualPath.startsWith(basePath)) {
            virtualPath = virtualPath.substring(basePath.length());
        }
        return physicalPath + virtualPath;
    }

    /**
     * @param @param url文件url
     * @param @param fileName  文件名
     * @param @param response
     * @return void
     * @throws
     * @Title: downFile
     * @Description:
     */
    public static void downFile(String url, String fileName, HttpServletRequest request, HttpServletResponse response) {
        FileInputStream in = null;
        OutputStream out = null;
        try {
            //获得文件
            File file = new File(url);
            if (file.exists()) {
                //1.定义ContentType为("multipart/form-data")让浏览器自己解析文件格式
                response.setContentType("multipart/form-data");
                if (StringUtils.isNotBlank(fileName)) {
                    //2.中文名转码
                    response.setHeader("Content-disposition", "attachment; filename=\"" + encodeChineseDownloadFileName(request, fileName + ".xlsx") + "\"");
                } else {
                    response.setHeader("Content-disposition", "attachment; filename=" + file.getName() + "");
                }
                in = new FileInputStream(file);
                //3.将文件写入缓冲区OutputStream(out)
                out = new BufferedOutputStream(response.getOutputStream());

                int b = 0;
                byte[] buffer = new byte[2048];
                while ((b = in.read(buffer)) != -1) {
                    //4.将缓冲区文件输出到客户端(out)
                    out.write(buffer, 0, b);
                }
                in.close();
                out.flush();
                out.close();
            } else {
                log.error("文件不存在");
            }
        } catch (Exception e) {
            log.error("下载文件出错：" + e.getMessage());
            e.printStackTrace();
        } finally {
            if (in != null) {
                in = null;
            }
            if (out != null) {
                out = null;
            }
        }
    }

    /**
     * 获取工程项目根路径
     *
     * @return
     */
    public static String getProjectPath() {
        // 如果配置了工程路径，则直接返回，否则自动获取。
        //BaseConfig.PROJECT_PATH;
        String projectPath = null;
        if (StringUtils.isNotBlank(projectPath)) {
            return projectPath;
        }
        try {
            File file = new DefaultResourceLoader().getResource("").getFile();
            if (file != null) {
                while (true) {
                    File f = new File(file.getPath() + File.separator + "src" + File.separator + "main");
                    if (f == null || f.exists()) {
                        break;
                    }
                    if (file.getParentFile() != null) {
                        file = file.getParentFile();
                    } else {
                        break;
                    }
                }
                projectPath = file.toString();
            }
        } catch (IOException e) {
            log.error("获取项目根目录出错：" + e.getMessage());
            e.printStackTrace();
        }
        return projectPath;
    }

    public static void main(String[] arg) {
        System.out.println(getProjectPath());
    }
}
