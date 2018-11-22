package com.d3sq.explorer.mvc.action.mobile;

import java.io.File;
import java.util.Iterator;

import javax.core.common.ResultMsg;
import javax.core.common.doc.annotation.Api;
import javax.core.common.doc.annotation.Auth;
import javax.core.common.doc.annotation.Domain;
import javax.core.common.doc.annotation.Rule;
import javax.core.common.utils.ToolKit;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.druid.util.StringUtils;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.d3sq.common.config.CustomConfig;
import com.d3sq.common.constants.ExplorerConstant;
import com.d3sq.common.constants.SystemConstant;
import com.d3sq.common.listeners.UploadListener;
import com.d3sq.common.resolvers.UploadResolver;
import com.d3sq.core.mvc.action.BaseAction;
import com.d3sq.explorer.service.IResourceService;

/**
 * 资源管理接口
 *
 */
@Controller
@RequestMapping("/mobile")
@Domain(value="resource",desc="资源服务")
public class ResourceAction extends BaseAction{
	
	Logger LOG = Logger.getLogger(this.getClass());
	
	@Autowired private UploadResolver multipartResolver;
	
	@Autowired private IResourceService resourceService;
	
	
	private ModelAndView processUpload(HttpServletRequest request,HttpServletResponse response,String local,String mode){
		String loginName = super.getUserInfo(request).getJSONObject("user").getString("loginName");

		if(StringUtils.isEmpty(mode)){
			mode = ExplorerConstant.UPLOAD_MODE_EMPTY;
		}else if(!mode.matches(CustomConfig.getString("explorer.upload.mode"))){
			mode = ExplorerConstant.UPLOAD_MODE_EMPTY;
		}
		
		String filePath = "/uploads/" + mode + "/"+loginName;
		String abpath = CustomConfig.getString("explorer.upload");
		
		String path = (abpath + filePath);//定义上传路径  
		File dir = new File(path);
		if(!dir.exists()) {dir.mkdirs();}
		
		JSONArray pathArrayObj = new JSONArray();
		//创建一个通用的多部分解析器  
        try {
            if(multipartResolver.isMultipart(request)){  //判断 request 是否有文件上传,即多部分请求  
                MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest)request;  //转换成多部分request    
                Iterator<String> iter = multiRequest.getFileNames();  //取得request中的所有文件名  
                while(iter.hasNext()){  
                    MultipartFile file = multiRequest.getFile(iter.next());  //取得上传文件  
                    if(file != null){  
                    	String progressId = ToolKit.getString(request, "X-Progress-ID");
                        String myFileName = file.getOriginalFilename();  //取得当前上传文件的文件名称  
                        String ext = myFileName.substring(myFileName.lastIndexOf("."));
                        String fileName = progressId + "_" + System.currentTimeMillis() + ext;
                        JSONObject fileObj = new JSONObject();
                        fileObj.put("progressId", progressId);	//文件ID
                        if(!myFileName.toUpperCase().matches(".*(PNG|JPG|JPEG|BMP)$")){
                        	return super.callBackForJsonp(request, response, JSONObject.toJSONString(new ResultMsg<Object>(SystemConstant.RESULT_STATUS_ERROR, "仅支持(PNG|JPG|JPEG|BMP)格式",fileObj)));
                        }
                        if(file.getSize() > (1024 * 1204 * 10)){
                        	return super.callBackForJsonp(request, response, JSONObject.toJSONString(new ResultMsg<Object>(SystemConstant.RESULT_STATUS_ERROR, "图片大小已超过10M",fileObj)));
                        }
                        if(myFileName.trim() !=""){  //如果名称不为“”,说明该文件存在，否则说明该文件不存在  
                            File localFile = new File(path + "/" + fileName);  
                            file.transferTo(localFile);
                        }
                        fileObj.put("size", file.getSize());	//文件大小
                        fileObj.put("fileName", myFileName);	//文件真实名
                        fileObj.put("filePath", CustomConfig.getString("system.resourceHost") + filePath + "/" + fileName);	//文件路径
                        pathArrayObj.add(fileObj);
                    }  
                }  
            }
            return super.callBackForJsonp(request, response, JSONObject.toJSONString(new ResultMsg<Object>(SystemConstant.RESULT_STATUS_SUCCESS, "上传文件成功.",pathArrayObj)));
		} catch (Exception e) {
			LOG.error("上传文件出错！", e);
			return super.callBackForJsonp(request, response, JSONObject.toJSONString(new ResultMsg<Object>(SystemConstant.RESULT_STATUS_ERROR, "上传文件出错.")));
		}
	}
	
	
	@Api(name="上传文件到指定模块",
	 desc="如果未指定上传模块，则不允许上传",
	 auth={ @Auth(checkEnc=true) },
	 params={
		@Rule(name="mode",desc="模块名称,包括（photo、bbs、cert、public、anonymous、mall）")
	 },
	 returns={
		@Rule(name="成功",type="JSON",desc="{status:1,data:{},msg:''}")
	})
	@RequestMapping(value="/resource/{mode}/upload.json",method=RequestMethod.POST)
	public ModelAndView uploadForMode(HttpServletRequest request,HttpServletResponse response,
			@PathVariable("mode") String mode){
		String local = super.getLocal(request);
		return processUpload(request,response,local,mode);
	}
	
	@Api(name="上传文件到公共模块",
	 desc="上传资料到公共模块",
	 auth={ @Auth(checkEnc=true) },
	 returns={
		@Rule(name="成功",type="JSON",desc="{status:1,data:{},msg:''}")
	})
	@RequestMapping(value="/resource/upload.json",method=RequestMethod.POST)
	public ModelAndView upload(HttpServletRequest request,HttpServletResponse response){
		String local = super.getLocal(request);
		return processUpload(request,response,local,"public");
	}
	
	
	@Api(name="上传用户头像",
	 desc="上传用户头像",
	 auth={ @Auth(checkEnc=true) },
	 returns={
		@Rule(name="成功",type="JSON",desc="{status:1,data:{},msg:''}")
	})
	@RequestMapping(value="/resource/photo/upload.json",method=RequestMethod.POST)
	public ModelAndView uploadForPhoto(HttpServletRequest request,HttpServletResponse response){
		String local = super.getLocal(request);
		return processUpload(request,response,local,"photos");
	}
	
	
	
	/**
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@Api(name="获取上传进度",
	 desc="获取某个文件的上传进度",
	 auth={ @Auth(checkEnc=true) },
	 params={
		@Rule(name="X-Progress-ID",desc="上传文件时生成的唯一标识，一般是使用时间戳")
	},
	 returns={
		@Rule(name="成功",type="JSON",desc="{status:1,data:{},msg:''}")
	})
	@RequestMapping(value="/upload/progress.json")
	public ModelAndView progress(HttpServletRequest request, HttpServletResponse response,
			@RequestParam(value="X-Progress-ID") String progressId){
//		String progressId = ToolKit.getString(request, "");
		
		UploadListener listenter = (UploadListener) request.getSession().getAttribute(ExplorerConstant.PROGRESS_KEY_SUFF + progressId);
		if (listenter == null) {
			return super.callBackForJsonp(request, response, "{}");
		}
		return super.callBackForJsonp(request, response, JSONObject.toJSONString(listenter.progress));
	}
	
	
}
