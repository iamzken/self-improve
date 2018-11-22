package com.d3sq.shopping.service.impl;

import javax.core.common.ResultMsg;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.druid.util.StringUtils;
import com.alibaba.fastjson.JSONObject;
import com.d3sq.common.constants.IndexConstant;
import com.d3sq.common.constants.MobileConstant;
import com.d3sq.common.constants.SystemConstant;
import com.d3sq.common.utils.PinyinUtil;
import com.d3sq.core.plugin.queue.annotation.QueueTarget;
import com.d3sq.core.plugin.queue.model.QueueItem;
import com.d3sq.model.entity.Shop;
import com.d3sq.search.plugin.ShopIndexPlugin;
import com.d3sq.shopping.dao.ShopDao;
import com.d3sq.shopping.service.IShopService;
import com.d3sq.shopping.vo.AddShopVo;

@Service("shopService")
public class ShopService implements IShopService {
	@Autowired
	private ShopDao shopDao;
	@Autowired 
	private ShopIndexPlugin shopIndexPlugin;

	@Override
	public ResultMsg<?> addShop(String local,String domain, AddShopVo shopVo, String enc) {
		String shopName = shopVo.getShopName();
		String address = shopVo.getAddress();
		String lon = shopVo.getLon();
		String lat = shopVo.getLat();
		
		//验证参数
		if(null == lon ||   null == lat || StringUtils.isEmpty(shopName) ||   StringUtils.isEmpty(address) || StringUtils.isEmpty(enc)){
			JSONObject error = new JSONObject();
			if(null == lon || null == lat){
				error.put("lon", "定位失败");
			}
			if(StringUtils.isEmpty(shopName)){
				error.put("shopName", "店铺名称未填写");
			}
			if(StringUtils.isEmpty(address)){
				error.put("address", "店铺地址未填写");
			}
			if(StringUtils.isEmpty(enc)){
				error.put("enc", "授权码未填写");
			}
			return new ResultMsg<Object>(SystemConstant.RESULT_PARAM_ERROR, "参数不完整",error);
		}
		

		//检查授权码是否正确
		if(!MobileConstant.genEnc(shopVo.getCreatorId()+""+lon+lat).equals(enc)){
			JSONObject error = new JSONObject();
			error.put("enc", "授权码错误");
			return new ResultMsg<Object>(SystemConstant.RESULT_PARAM_ERROR, "授权码错误",error);
		}
		try {
			Shop shop = new Shop();
			//DataUtils.copySimpleObject(shopVo, shop);
			shop.setName(shopName);
			shop.setAddress(address);
			shop.setLon(Float.parseFloat(lon));
			shop.setLat(Float.parseFloat(lat));
			shop.setCreatorId(shopVo.getCreatorId());
			shop.setCreateTime(System.currentTimeMillis());
			shop.setPinyin(PinyinUtil.converterToSpell(shopName).split(",")[0]);
			Long id = shopDao.insert(shop);
			if(id > 0){
				shop.setId(id);
//				QueueItem item = new QueueItem(IndexConstant.SERVICE_SEARCH_INDEX, QueueTarget.OPT_ADD, id, shop);
//				shopIndexPlugin.push(item);
				QueueItem item = new QueueItem(shopIndexPlugin,IndexConstant.SHOP_SEARCH_INDEX, QueueTarget.OPT_ADD, id, shop);
				shopIndexPlugin.push(item);
				return new ResultMsg<Object>(SystemConstant.RESULT_STATUS_SUCCESS, "添加成功",shop);
			}
			return new ResultMsg<Object>(SystemConstant.RESULT_STATUS_ERROR, "添加失败");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return new ResultMsg<Object>(SystemConstant.RESULT_STATUS_ERROR, "程序异常");
		}
	}
}
