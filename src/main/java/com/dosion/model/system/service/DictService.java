package com.dosion.model.system.service;

import com.dosion.base.BaseService;
import com.dosion.model.system.mapper.DictDao;
import com.dosion.model.system.entity.Dict;
import org.springframework.stereotype.Service;

@Service
public class DictService extends BaseService<DictDao, Dict> {
	public Dict getDict(Dict dict){
	  return dao.getDict(dict);
  };
}
