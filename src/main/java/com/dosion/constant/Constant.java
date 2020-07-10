package com.dosion.constant;

/**
 * 系统常量
 *
 * @author cdw
 */
public interface Constant {
    /**
     * 开发环境
     */
    String DEV = "dev";
    /**
     * 生产环境
     */
    String PROD = "prod";

    /**
     * 正常
     */
    String STATUS_NORMAL = "0";

    /**
     * 成功标记
     */
    Integer SUCCESS = 0;

    /**
     * 失败标记
     */
    Integer FAIL = 1;

    /**
     * 默认存储bucket
     */
    String BUCKET_NAME = "jinglecat";

    /**
     * 菜单树根节点
     */
    Integer MENU_TREE_ROOT_ID = -1;
}
