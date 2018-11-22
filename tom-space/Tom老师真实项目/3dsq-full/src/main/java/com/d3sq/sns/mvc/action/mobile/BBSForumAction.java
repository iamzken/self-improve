package com.d3sq.sns.mvc.action.mobile;

import javax.core.common.ResultMsg;
import javax.core.common.doc.annotation.Api;
import javax.core.common.doc.annotation.Auth;
import javax.core.common.doc.annotation.Domain;
import javax.core.common.doc.annotation.Rule;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.d3sq.core.mvc.action.BaseAction;
import com.d3sq.sns.service.IBBSForumService;

@Controller
@RequestMapping("/mobile/bbs/forum")
@Domain(value="sns",desc="社区版块")
public class BBSForumAction extends BaseAction {
	
	@Autowired
	private IBBSForumService bbsForumService;
	
	@Api(name="添加板块",
	 auth={ @Auth(checkEnc=true) },
	 params={
			 @Rule(name="residentialId",desc="社区id."),
			 @Rule(name="parentId",desc="父级id.默认为根级模块"),
			 @Rule(name="title",desc="模块标题"),
			 @Rule(name="typeFlag",desc="模块类型(1:小区公共模块，2:物业公共模块)"),
			 @Rule(name="state",desc="是否是正常模块(1:正常,2:不可用。默认为:1)"),
			 @Rule(name="enc",desc="授权码")
	 },
	 returns={
		@Rule(name="成功",desc="{status:1,data:{},msg:''}")
	})
	@RequestMapping(value="/add.json")
	public ModelAndView addTopic(@RequestParam(value = "enc") String enc,
			@RequestParam(value="residentialId") Long residentialId,
			@RequestParam(value="parentId",defaultValue="0") Long parentId,
			@RequestParam(value="title",required = false) String title,
			@RequestParam(value="typeFlag",required = true) Integer typeFlag,
			@RequestParam(value="state",defaultValue="1") Integer state,
			HttpServletRequest request,HttpServletResponse response){
		
		String local = super.getLocal(request);
		JSONObject obj = super.getUserInfo(request);
		Long userId = obj.getJSONObject("user").getLong("id");
		ResultMsg<?> result = bbsForumService.addForum(local, userId, residentialId, parentId, title, typeFlag, state, enc);
		return super.callBackForJsonp(request, response,JSON.toJSONString(result));
	}

	@Api(name="获取板块",
	 auth={ @Auth(checkEnc=true) },
	 params={
			 @Rule(name="residentialId",desc="社区id."),
			 @Rule(name="lastId",desc="每一页最后一条数据的id"),
			 @Rule(name="order",desc="排序参数(0:创建日期正序,1:创建日期倒序.默认为：0)"),
			 @Rule(name="topicSize",desc="模块下话题的数量,默认为:5"),
			 @Rule(name="pageSize",desc="每页显示模块的数量,默认为：5"),
			 @Rule(name="enc",desc="授权码")
	 },
	 returns={
		@Rule(name="成功",desc="{status:1,data:{},msg:''}")
	})
	@RequestMapping(value="/get.json")
	public ModelAndView getList(
			@RequestParam(value="enc",defaultValue="") String enc,
			@RequestParam(value="residentialId") Long residentialId,
			@RequestParam(value="lastId",defaultValue="0") Long lastId,
			@RequestParam(value="order",required = false) Integer order,
			@RequestParam(value="topicSize",defaultValue="5") Integer topicSize,
			@RequestParam(value="pageSize",defaultValue="5") Integer pageSize,
			HttpServletRequest request,HttpServletResponse response){
		
		String local = super.getLocal(request);
		ResultMsg<?> result = bbsForumService.getForum(local, residentialId, order, lastId, topicSize, pageSize,enc);
		return super.callBackForJsonp(request, response,JSON.toJSONString(result));
	}
}
