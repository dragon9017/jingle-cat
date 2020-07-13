

package com.dosion.model.system.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dosion.constant.CacheConstants;
import com.dosion.enums.DictTypeEnum;
import com.dosion.model.system.entity.PublicParam;
import com.dosion.model.system.mapper.PublicParamMapper;
import com.dosion.model.system.service.PublicParamService;
import com.dosion.utils.R;
import lombok.AllArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

/**
 * 公共参数配置
 *
 * @author Lucky
 * @date 2019-04-29
 */
@Service
@AllArgsConstructor
public class PublicParamServiceImpl extends ServiceImpl<PublicParamMapper, PublicParam> implements PublicParamService {

	@Override
	@Cacheable(value = CacheConstants.PARAMS_DETAILS, key = "#publicKey", unless = "#result == null ")
	public String getSysPublicParamKeyToValue(String publicKey) {
		PublicParam sysPublicParam = this.baseMapper
				.selectOne(Wrappers.<PublicParam>lambdaQuery()
						.eq(PublicParam::getPublicKey, publicKey));

		if (sysPublicParam != null) {
			return sysPublicParam.getPublicValue();
		}
		return null;
	}

	/**
	 * 更新参数
	 *
	 * @param publicParam
	 * @return
	 */
	@Override
	@CacheEvict(value =CacheConstants.PARAMS_DETAILS, key = "#sysPublicParam.publicKey")
	public R updateParam(PublicParam publicParam) {
		PublicParam param = this.getById(publicParam.getPublicId());
		// 系统内置
		if (DictTypeEnum.SYSTEM.getType().equals(param.getSystem())) {
			return R.failed("系统内置参数不能删除");
		}
		return R.ok(this.updateById(publicParam)).setMsg("修改成功！");
	}

	/**
	 * 删除参数
	 *
	 * @param publicId
	 * @return
	 */
	@Override
	@CacheEvict(value =  CacheConstants.PARAMS_DETAILS, allEntries = true)
	public R removeParam(Long publicId) {
		PublicParam param = this.getById(publicId);
		// 系统内置
		if (DictTypeEnum.SYSTEM.getType().equals(param.getSystem())) {
			return R.failed("系统内置参数不能删除");
		}
		return R.ok(this.removeById(publicId)).setMsg("删除成功！");
	}
}
