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
import com.d3sq.sns.service.IBBSReplyService;

@Controller
@RequestMapping("/mobile/bbs/reply")
@Domain(value="sns",desc="回复话题")
public class BBSReplyAction extends BaseAction {
	
	@Autowired
	private IBBSReplyService bbsReplyService;
	
	@Api(name="发表回复",
	 auth={ @Auth(checkEnc=true) },
	 params={
			 @Rule(name="topicId",desc="话题id."),
			 @Rule(name="replyId",desc="回复id,如果回复话题参数为0,默认为回复话题."),
			 @Rule(name="content",desc="话题内容."),
			 @Rule(name="contentImgs",desc="话题内容中的图片地址，多张图片已逗号隔开."),
			 @Rule(name="enc",desc="授权码")
	 },
	 returns={
		@Rule(name="成功",desc="{status:1,data:{},msg:''}")
	})
	@RequestMapping(value="/add.json")
	public ModelAndView addReply(@RequestParam(value = "enc") String enc,
			@RequestParam(value="topicId") Long topicId,
			@RequestParam(value="replyId",defaultValue="0") Long replyId,
			@RequestParam(value="content",required = false) String content,
			@RequestParam(value="contentImgs",required = false) String contentImgs,
			HttpServletRequest request,HttpServletResponse response){
		
		String local = super.getLocal(request);
		JSONObject obj = super.getUserInfo(request);
		Long userId = obj.getJSONObject("user").getLong("id");
		ResultMsg<?> result = bbsReplyService.addReply(local, userId, topicId, replyId, content, contentImgs, enc);
		return super.callBackForJsonp(request, response,JSON.toJSONString(result));
	}
	
	@Api(name="编辑回复内容",
	 auth={ @Auth(checkEnc=true) },
	 params={
			 @Rule(name="replyId",desc="回复的id."),
			 @Rule(name="content",desc="回复的内容."),
			 @Rule(name="contentImgs",desc="回复内容中的图片地址，多张图片已逗号隔开."),
			 @Rule(name="enc",desc="授权码")
	 },
	 returns={
		@Rule(name="成功",desc="{status:1,data:{},msg:''}")
	})
	@RequestMapping(value="/update.json")
	public ModelAndView update(@RequestParam(value = "enc") String enc,
			@RequestParam(value="replyId",defaultValue="0") Long replyId,
			@RequestParam(value="content",required = false) String content,
			@RequestParam(value="contentImgs",required = false) String contentImgs,
			HttpServletRequest request,HttpServletResponse response){
		
		String local = super.getLocal(request);
		JSONObject obj = super.getUserInfo(request);
		Long userId = obj.getJSONObject("user").getLong("id");
		ResultMsg<?> result = bbsReplyService.modifyReply(local, replyId, userId, content, contentImgs, enc);
		return super.callBackForJsonp(request, response,JSON.toJSONString(result));
	}
	
	@Api(name="删除回复",
	 auth={ @Auth(checkEnc=true) },
	 params={
			 @Rule(name="replyId",desc="回复的id."),
			 @Rule(name="enc",desc="授权码")
	 },
	 returns={
		@Rule(name="成功",desc="{status:1,data:{},msg:''}")
	})
	@RequestMapping(value="/remove.json")
	public ModelAndView delete(@RequestParam(value = "enc") String enc,
			@RequestParam(value="replyId",defaultValue="0") Long replyId,
			HttpServletRequest request,HttpServletResponse response){
		
		String local = super.getLocal(request);
		JSONObject obj = super.getUserInfo(request);
		Long userId = obj.getJSONObject("user").getLong("id");
		ResultMsg<?> result = bbsReplyService.removeReply(local, replyId, userId, enc);
		return super.callBackForJsonp(request, response,JSON.toJSONString(result));
	}
	
	@Api(name="获取一级回复",
	 auth={ @Auth(checkEnc=true) },
	 params={
			 @Rule(name="enc",desc="授权码"),
			 @Rule(name="topicId",desc="话题id."),
			 @Rule(name="lastId",desc="每页最后一条回复的id."),
			 @Rule(name="replySize",desc="二级回复的数量(不传默认为:5)"),
			 @Rule(name="pageSize",desc="每页加载一级回复的条数(不传默认为:15)"),
			 @Rule(name="orderFlag",desc="排序标识(0:根据回复时间正序,1:根据回复时间倒序。不传默认为:0)")
	 },
	 returns={
		@Rule(name="成功",desc="{status:1,data:{},msg:''}")
	})
	@RequestMapping(value="/getRoot.json")
	public ModelAndView getRoot(
			@RequestParam(value = "enc") String enc,
			@RequestParam(value="topicId",required = true) Long topicId,
			@RequestParam(value="lastId",defaultValue="0") Long lastId,
			@RequestParam(value="replySize",defaultValue="5") Integer replySize,
			@RequestParam(value="pageSize",defaultValue="15") Integer pageSize,
			@RequestParam(value="orderFlag",defaultValue="0") Integer orderFlag,
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
		
		ResultMsg<?> result = bbsReplyService.getRootReplyListForPage(local, userId,topicId, lastId, replySize, pageSize, orderFlag,enc);
		return super.callBackForJsonp(request, response,JSON.toJSONString(result));
	}
	
	@Api(name="获取子级回复",
	 auth={ @Auth(checkEnc=true) },
	 params={
			 @Rule(name="enc",desc="授权码"),
			 @Rule(name="replyId",desc="一级回复的id."),
			 @Rule(name="lastId",desc="每页最后一条回复的id."),
			 @Rule(name="pageSize",desc="每页加载一级回复的条数(不传默认为:15)"),
			 @Rule(name="orderFlag",desc="排序标识(0:根据回复时间正序,1:根据回复时间倒序。不传默认为:0)")
	 },
	 returns={
		@Rule(name="成功",desc="{status:1,data:{},msg:''}")
	})
	@RequestMapping(value="/getSecond.json")
	public ModelAndView getRootSecond(
			@RequestParam(value = "enc") String enc,
			@RequestParam(value="replyId",required = true) Long replyId,
			@RequestParam(value="lastId",defaultValue="0") Long lastId,
			@RequestParam(value="pageSize",defaultValue="15") Integer pageSize,
			@RequestParam(value="orderFlag",defaultValue="0") Integer orderFlag,
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
		ResultMsg<?> result = bbsReplyService.getSecondReplyListForPage(local, userId,replyId, lastId, pageSize, orderFlag,enc);
		return super.callBackForJsonp(request, response,JSON.toJSONString(result));
	}
	
}
