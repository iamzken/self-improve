package com.d3sq.shopping.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.core.common.ResultMsg;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.druid.util.StringUtils;
import com.alibaba.fastjson.JSONObject;
import com.d3sq.common.constants.FieldConstant;
import com.d3sq.common.constants.MobileConstant;
import com.d3sq.common.constants.SystemConstant;
import com.d3sq.shopping.dao.KindDao;
import com.d3sq.shopping.service.IKindService;

@Service("kindService")
public class KindService implements IKindService{
	@Autowired private KindDao kindDao;


	@Override
	public ResultMsg<?> getForProduct(String local, String domain, String enc) {
		//验证参数
		if(StringUtils.isEmpty(enc)){
			JSONObject error = new JSONObject();
			if(StringUtils.isEmpty(enc)){
				error.put("enc", "授权码未填写");
			}
			return new ResultMsg<Object>(SystemConstant.RESULT_PARAM_ERROR, "参数不完整",error);
		}

		//检查授权码是否正确
		if(!MobileConstant.genEnc("").equals(enc)){
			JSONObject error = new JSONObject();
			error.put("enc", "授权码错误");
			return new ResultMsg<Object>(SystemConstant.RESULT_PARAM_ERROR, "授权码错误",error);
		}
		try {
			String alias = FieldConstant.KIND_ALIAS_PRODUCT;
			//Long parentId = (long)-1;
			List<Map<String,Object>> kinds = kindDao.selectByAlias(alias);
			if(kinds.isEmpty()){
				return new ResultMsg<Object>(SystemConstant.RESULT_STATUS_SUCCESS, "查询成功");
			}

			Map<String,Object> topics = new HashMap<String,Object>();
			//分析级联数据
			for(Map<String,Object> kind:kinds){
				String id = kind.get("id").toString();
				String parentId = kind.get("parentId").toString();
				//最顶级
				if(!topics.containsKey(id) && id.equals(parentId)){
					topics.put(id, kind);
				}
			}

			List<Map<String,Object>> datas = new ArrayList<Map<String,Object>>();

			for(String key:topics.keySet()){
				Map<String,Object> topic = (Map<String,Object>)topics.get(key);
				Map<String,Object> data = new HashMap<String, Object>();
				data.put("kindId", topic.get("id"));
				data.put("kingName", topic.get("name"));
				data.put("kindPath", topic.get("xpath"));
				List<Map<String,Object>> childrens = new ArrayList<Map<String,Object>>();
				for(Map<String,Object> kind:kinds){
					String id = kind.get("id").toString();
					String parentId = kind.get("parentId").toString();
					if(!key.equals(parentId) || key.equals(id)){
						continue;
					}
					Map<String,Object> children = new HashMap<String, Object>();
					children.put("kingId", kind.get("id"));
					children.put("kingName", kind.get("name"));
					children.put("kindPath", kind.get("xpath"));
					childrens.add(children);
				}
				data.put("childrens", childrens);
				datas.add(data);
			}
			return new ResultMsg<Object>(SystemConstant.RESULT_STATUS_SUCCESS, "查询成功",datas);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return new ResultMsg<Object>(SystemConstant.RESULT_STATUS_ERROR, "出现异常");
		}
	}
	
	
	@Override
	public ResultMsg<?> getForService(String local, String domain, String enc) {
		//验证参数
		if(StringUtils.isEmpty(enc)){
			JSONObject error = new JSONObject();
			if(StringUtils.isEmpty(enc)){
				error.put("enc", "授权码未填写");
			}
			return new ResultMsg<Object>(SystemConstant.RESULT_PARAM_ERROR, "参数不完整",error);
		}

		//检查授权码是否正确
		if(!MobileConstant.genEnc("").equals(enc)){
			JSONObject error = new JSONObject();
			error.put("enc", "授权码错误");
			return new ResultMsg<Object>(SystemConstant.RESULT_PARAM_ERROR, "授权码错误",error);
		}
		try {
			String alias = FieldConstant.KIND_ALIAS_SERVICE;
			//Long parentId = (long)-1;
			List<Map<String,Object>> kinds = kindDao.selectByAlias(alias);
			if(kinds.isEmpty()){
				return new ResultMsg<Object>(SystemConstant.RESULT_STATUS_SUCCESS, "查询成功");
			}

			Map<String,Object> topics = new HashMap<String,Object>();
			//分析级联数据
			for(Map<String,Object> kind:kinds){
				String id = kind.get("id").toString();
				String parentId = kind.get("parentId").toString();
				//最顶级
				if(!topics.containsKey(id) && id.equals(parentId)){
					topics.put(id, kind);
				}
			}

			List<Map<String,Object>> datas = new ArrayList<Map<String,Object>>();

			for(String key:topics.keySet()){
				Map<String,Object> topic = (Map<String,Object>)topics.get(key);
				Map<String,Object> data = new HashMap<String, Object>();
				data.put("kindId", topic.get("id"));
				data.put("kingName", topic.get("name"));
				data.put("kindPath", topic.get("xpath"));
				List<Map<String,Object>> childrens = new ArrayList<Map<String,Object>>();
				for(Map<String,Object> kind:kinds){
					String id = kind.get("id").toString();
					String parentId = kind.get("parentId").toString();
					if(!key.equals(parentId) || key.equals(id)){
						continue;
					}
					Map<String,Object> children = new HashMap<String, Object>();
					children.put("kingId", kind.get("id"));
					children.put("kingName", kind.get("name"));
					children.put("kindPath", kind.get("xpath"));
					childrens.add(children);
				}
				data.put("childrens", childrens);
				datas.add(data);
			}
			return new ResultMsg<Object>(SystemConstant.RESULT_STATUS_SUCCESS, "查询成功",datas);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return new ResultMsg<Object>(SystemConstant.RESULT_STATUS_ERROR, "出现异常");
		}
	}


	@Override
	public ResultMsg<?> getByParentId(String local, String domain,
			Long parentId, String enc) {
		//验证参数
		if(null == parentId || StringUtils.isEmpty(enc)){
			JSONObject error = new JSONObject();
			if(null == parentId){
				error.put("parentId", "父级分类id不能为空");
			}
			if(StringUtils.isEmpty(enc)){
				error.put("enc", "授权码未填写");
			}
			return new ResultMsg<Object>(SystemConstant.RESULT_PARAM_ERROR, "参数不完整",error);
		}

		//检查授权码是否正确
		if(!MobileConstant.genEnc(parentId+"").equals(enc)){
			JSONObject error = new JSONObject();
			error.put("enc", "授权码错误");
			return new ResultMsg<Object>(SystemConstant.RESULT_PARAM_ERROR, "授权码错误",error);
		}
		try {
			List<Map<String,Object>> kinds = kindDao.selectByParentId(parentId);
			return new ResultMsg<Object>(SystemConstant.RESULT_STATUS_SUCCESS, "查询成功",kinds);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return new ResultMsg<Object>(SystemConstant.RESULT_STATUS_ERROR, "出现异常");
		}
	}
	
	
	@Override
	public ResultMsg<?> getForShop(String local, String domain, String enc) {
		//验证参数
		if(StringUtils.isEmpty(enc)){
			JSONObject error = new JSONObject();
			if(StringUtils.isEmpty(enc)){
				error.put("enc", "授权码未填写");
			}
			return new ResultMsg<Object>(SystemConstant.RESULT_PARAM_ERROR, "参数不完整",error);
		}

		//检查授权码是否正确
		if(!MobileConstant.genEnc("").equals(enc)){
			JSONObject error = new JSONObject();
			error.put("enc", "授权码错误");
			return new ResultMsg<Object>(SystemConstant.RESULT_PARAM_ERROR, "授权码错误",error);
		}
		try {
			String alias = FieldConstant.KIND_ALIAS_SHOP;
			//Long parentId = (long)-1;
			List<Map<String,Object>> kinds = kindDao.selectByAlias(alias);
			if(kinds.isEmpty()){
				return new ResultMsg<Object>(SystemConstant.RESULT_STATUS_SUCCESS, "查询成功");
			}

			Map<String,Object> topics = new HashMap<String,Object>();
			//分析级联数据
			for(Map<String,Object> kind:kinds){
				String id = kind.get("id").toString();
				String parentId = kind.get("parentId").toString();
				//最顶级
				if(!topics.containsKey(id) && id.equals(parentId)){
					topics.put(id, kind);
				}
			}

			List<Map<String,Object>> datas = new ArrayList<Map<String,Object>>();

			for(String key:topics.keySet()){
				Map<String,Object> topic = (Map<String,Object>)topics.get(key);
				Map<String,Object> data = new HashMap<String, Object>();
				data.put("kindId", topic.get("id"));
				data.put("kingName", topic.get("name"));
				data.put("kindPath", topic.get("xpath"));
				List<Map<String,Object>> childrens = new ArrayList<Map<String,Object>>();
				for(Map<String,Object> kind:kinds){
					String id = kind.get("id").toString();
					String parentId = kind.get("parentId").toString();
					if(!key.equals(parentId) || key.equals(id)){
						continue;
					}
					Map<String,Object> children = new HashMap<String, Object>();
					children.put("kingId", kind.get("id"));
					children.put("kingName", kind.get("name"));
					children.put("kindPath", kind.get("xpath"));
					childrens.add(children);
				}
				data.put("childrens", childrens);
				datas.add(data);
			}
			return new ResultMsg<Object>(SystemConstant.RESULT_STATUS_SUCCESS, "查询成功",datas);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return new ResultMsg<Object>(SystemConstant.RESULT_STATUS_ERROR, "出现异常");
		}
	}

}
