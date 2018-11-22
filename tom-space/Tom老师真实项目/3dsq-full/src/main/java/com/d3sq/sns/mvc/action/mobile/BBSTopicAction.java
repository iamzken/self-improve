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
import com.d3sq.sns.service.IBBSTopicService;

@Controller
@RequestMapping("/mobile/bbs/topic")
@Domain(value="sns",desc="发布和获取话题")
public class BBSTopicAction extends BaseAction{
	
	@Autowired
	private IBBSTopicService bbsTopicService;
	
	@Api(name="发表话题",
	 auth={ @Auth(checkEnc=true) },
	 params={
			 @Rule(name="residentialId",desc="社区id."),
			 @Rule(name="forumId",desc="模块id."),
			 @Rule(name="forumPath",desc="模块path,需要utf-8编码"),
			 @Rule(name="title",desc="标题,注意(标题长度不能超过30)."),
			 @Rule(name="content",desc="话题内容."),
			 @Rule(name="contentImgs",desc="话题内容中的图片地址，多张图片已逗号隔开."),
			 @Rule(name="enc",desc="授权码")
	 },
	 returns={
		@Rule(name="成功",desc="{status:1,data:{},msg:''}")
	})
	@RequestMapping(value="/add.json")
	public ModelAndView addTopic(@RequestParam(value = "enc") String enc,
			@RequestParam(value="residentialId") Long residentialId,
			@RequestParam(value="forumId",required = true) Long forumId,
			@RequestParam(value="forumPath",required = false) String forumPath,
			@RequestParam(value="title",required = false) String title,
			@RequestParam(value="content",required = false) String content,
			@RequestParam(value="contentImgs",required = false) String contentImgs,
			HttpServletRequest request,HttpServletResponse response){
		
		String local = super.getLocal(request);
		JSONObject obj = super.getUserInfo(request);
		Long userId = obj.getJSONObject("user").getLong("id");
		ResultMsg<?> result = bbsTopicService.addTopic(local, userId, residentialId,forumId,forumPath, title, content, contentImgs, enc);
		return super.callBackForJsonp(request, response,JSON.toJSONString(result));
	}
	
	@Api(name="删除话题",
	 auth={ @Auth(checkEnc=true) },
	 params={
			 @Rule(name="topicId",desc="话题id"),
			 @Rule(name="enc",desc="授权码")
	 },
	 returns={
		@Rule(name="成功",desc="{status:1,data:{},msg:''}")
	})
	@RequestMapping(value="/remove.json")
	public ModelAndView deleteTopic(@RequestParam(value = "enc") String enc,
			@RequestParam(value="topicId") Long topicId,
			HttpServletRequest request,HttpServletResponse response
			){
		String local = super.getLocal(request);
		JSONObject obj = super.getUserInfo(request);
		Long userId = obj.getJSONObject("user").getLong("id");
		ResultMsg<?> result = bbsTopicService.removeTopic(local, topicId, userId, enc);
		return super.callBackForJsonp(request, response,JSON.toJSONString(result));
	}
	
	@Api(name="修改话题",
	 auth={ @Auth(checkEnc=true) },
	 params={
			@Rule(name="forumId",desc="模块id."),
			@Rule(name="forumPath",desc="模块path,需要utf-8编码"),
			@Rule(name="topicId",desc="话题id."),
			@Rule(name="title",desc="标题,注意(标题长度不能超过30)."),
			@Rule(name="content",desc="话题内容."),
			@Rule(name="contentImgs",desc="话题内容中的图片地址，多张图片已逗号隔开."),
			@Rule(name="enc",desc="授权码")
	 },
	 returns={
		@Rule(name="成功",desc="{status:1,data:{},msg:''}")
	})
	@RequestMapping(value="/modify.json")
	public ModelAndView modifyTopic(@RequestParam(value = "enc") String enc,
			@RequestParam(value="forumId",required = false) Long forumId,
			@RequestParam(value="forumPath",required = false) String forumPath,
			@RequestParam(value="topicId") Long topicId,
			@RequestParam(value="title",required = false) String title,
			@RequestParam(value="content",required = false) String content,
			@RequestParam(value="contentImgs",required = false) String contentImgs,
			HttpServletRequest request,HttpServletResponse response){
		
		String local = super.getLocal(request);
		JSONObject obj = super.getUserInfo(request);
		Long userId = obj.getJSONObject("user").getLong("id");
		ResultMsg<?> result = bbsTopicService.modifyTopic(local, userId,forumId,forumPath, topicId, title, content, contentImgs, enc);
		return super.callBackForJsonp(request, response,JSON.toJSONString(result));
	}
	
	@Api(name="话题列表",
	 author="胡乾坤",createtime="2016-07-27",
	 auth={ @Auth(checkEnc=false) },
	 params={
			@Rule(name="residentialId",desc="社区id"),
			@Rule(name="top",desc="置顶标识(0:普通话题、1:置顶的话题)"),
			@Rule(name="sw",desc="搜索话题参数，搜索范围(标题、内容)"),
			@Rule(name="order",desc="排序标识(0:话题创建时间正序、1:话题创建时间倒序、2:最后回复时间正序、3:最后回复时间倒序),不传默认为:0"),
			@Rule(name="lastTopicId",desc="每一页最后一个话题的id"),
			@Rule(name="pageSize",desc="每页显示的条数,默认为每页20条")
	 },
	 returns={
		@Rule(name="成功",desc="{status:1,data:{},msg:''}")
	})
	@RequestMapping(value="/getList.json")
	public ModelAndView getTopicList(
			@RequestParam(value="enc") String enc,
			@RequestParam(value="residentialId",required = true) Long residentialId,
			@RequestParam(value="forumId",required = true) Long forumId,
			@RequestParam(value="top",required = false) Integer top,
			@RequestParam(value="sw",required = false) String sw,
			@RequestParam(value="order",defaultValue = "0",required = false) Integer order,
			@RequestParam(value="lastTopicId",defaultValue = "1") Long lastTopicId,
			@RequestParam(value="pageSize",defaultValue = "20",required = false) Integer pageSize,
			HttpServletRequest request,HttpServletResponse response
			){
		String local = super.getLocal(request);
		JSONObject obj = super.getUserInfo(request);
		Long userId = null;
		if(null != obj){
			JSONObject user = obj.getJSONObject("user");
			if(null != user){
				userId = user.getLong("id");
			}
		}
		ResultMsg<?> result = bbsTopicService.getTopicList(local,userId, residentialId,forumId, top, sw, lastTopicId, pageSize, order,enc);
		return super.callBackForJsonp(request, response,JSON.toJSONString(result));
	}
	
	@Api(name="话题详情",
	 auth={ @Auth(checkEnc=false) },
	 params={
			 @Rule(name="topicId",desc="话题id")
	 },
	 returns={
		@Rule(name="成功",desc="{status:1,data:{},msg:''}")
	})
	@RequestMapping(value="/getTopic.json")
	public ModelAndView getTopic(
			@RequestParam(value="enc") String enc,
			@RequestParam(value="topicId") Long topicId,
			HttpServletRequest request,HttpServletResponse response){
		String local = super.getLocal(request);
		JSONObject obj = super.getUserInfo(request);
		Long userId = null;
		if(null != obj){
			JSONObject user = obj.getJSONObject("user");
			if(null != user){
				userId = user.getLong("id");
			}
		}
		ResultMsg<?> result = bbsTopicService.getTopic(local,userId, topicId,enc);
		return super.callBackForJsonp(request, response,JSON.toJSONString(result));
	}
}
