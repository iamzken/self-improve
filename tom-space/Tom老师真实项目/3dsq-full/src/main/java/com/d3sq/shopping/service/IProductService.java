package com.d3sq.shopping.service;

import java.util.List;

import javax.core.common.ResultMsg;

import com.d3sq.shopping.vo.AddProductVo;
import com.d3sq.shopping.vo.ProductVo;


public interface IProductService {
	/**
	 * 添加商品
	 * @param local
	 * @param userId
	 * @param productVo
	 * @param enc
	 * @return
	 */
	ResultMsg<?> addProduct(String local,Long userId,  AddProductVo productVo,String enc);

	/**
	 * 修改商品
	 * @param local
	 * @param id
	 * @param name
	 * @param shopId
	 * @param intro
	 * @param coverImg
	 * @param price
	 * @param sale
	 * @param stock
	 * @param enc
	 * @return
	 */
	ResultMsg<?> updateProduct(String local,Long id, String name, Long shopId,
			String intro, String coverImg, Float price, Float sale,
			int stock, String enc);

	/**
	 * 删除商品
	 * @param local
	 * @param id
	 * @param enc
	 * @return
	 */
	ResultMsg<?> deleteProduct(String local, Long id, String enc);

	
	/**
	 * 获取商品详情
	 * @param local
	 * @param domain
	 * @param productId
	 * @param enc
	 * @return
	 */
	ResultMsg<?> getProduct(String local, String domain, Long productId,
			String enc);
	
	
	/**
	 * 获取全部商品
	 * @param local
	 * @param domain
	 * @param enc
	 * @return
	 */
	ResultMsg<?> getProductList(String local, String domain,String enc);
	
	
	/**
	 * 获取全部商品(内部调用)
	 * @param local
	 * @param domain
	 * @param enc
	 * @return
	 */
	List<ProductVo> getProductList();

	/**
	 * 分页查询商品信息根据分类
	 * @param local
	 * @param domain
	 * @param catalogueId
	 * @param lastProductId
	 * @param pageSize
	 * @param enc
	 * @return
	 */
	ResultMsg<?> getProductByCatalogueId(String local, String domain,Double lon,Double lat,
			String catalogueId, Long lastProductId, Integer pageSize, String enc);

	/**
	 * 根据商品类型查询(内部调用)
	 * @param productPtypeCommod
	 * @return
	 */
	List<ProductVo> getProductList(Integer productPtypeCommod);


}
