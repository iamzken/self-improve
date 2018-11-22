package com.d3sq.center.service;

import javax.core.common.ResultMsg;

public interface ICommonService {
	/**
	 * 获取地市(parentId为空时,查询所有省)
	 * @param local
	 * @param parentId
	 * @param enc
	 * @return
	 */
	ResultMsg<?> getCity(String local, Long parentId, String enc);

}
