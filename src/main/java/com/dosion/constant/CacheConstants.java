package com.dosion.constant;

/**
 * @author cdw
 * @date 2019-04-28
 * <p>
 * 缓存的key 常量
 */
public interface CacheConstants {

	/**
	 * 验证码前缀
	 */
	String DEFAULT_CODE_KEY = "JINGLE_CAT_DEFAULT_CODE_KEY:";

	/**
	 *验证码保存时间 10 分钟
	 */
	Integer CODE_TIME = 10;

	/**
	 * 用户信息缓存
	 */
	String USER_DETAILS = "jingle_cat_user_details";

	/**
	 * 用户缓存时间 2小时
	 */
	Integer USER_DETAILS_TIME = 2;

	/**
	 * 菜单信息缓存
	 */
	String MENU_DETAILS = "jingle_cat_menu_details";

	/**
	 * 字典信息缓存
	 */
	String DICT_DETAILS = "dict_details";

	/**
	 * redis reload 事件
	 */
	String ROUTE_REDIS_RELOAD_TOPIC = "gateway_redis_route_reload_topic";


}
