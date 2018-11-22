package com.d3sq.model.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 文件对象实体定义
 *
 */
@Entity
@Table(name="t_ufile")
public class UFile implements Serializable{
	//自动生成ID
	private String id;
	//父级ID
	private String pid;
	//文件路径
	private String xpath;
	//路径代码
	private String xcode;
	//文件名
	private String fname;
	//别名
	private String alias;
	//文件唯一标识
	private String objectId;
	//文件存储的真实路径
//	@Column(name="original_path") private String originalPath;
	//创建时间
	private Long createTime;
	//上传者
	private String uname;
	//文件类型,是否为文件夹
	private String ftype;
	//文件扩展名
	private String ext;
	//图标类型
	private String ico;
	//最后修改时间
	private Long lastModified;
	//文件大小
	private Long fsize;
	//文件所在的组
	private String fgroup;
	//文件所在的子组
	private String fsgroup;
	//标记是否已删除
	private String deleted;
	
	public UFile(){}
	public UFile(String path){
		path =	path.replaceAll("/$", "");
		if(path.length() == 0){ path = "/"; }
		int s = path.lastIndexOf("/");
		this.xpath = path.substring(0, s + 1);
		this.fname = path.substring(s + 1);
		if(fname.indexOf(".") == -1){
			this.ftype = "DIR";
		}else{
			this.ext = fname.substring(fname.lastIndexOf(".") + 1);
			this.ftype = "FILE";;
		}
	}
	
	@Id
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getPid() {
		return pid;
	}
	public void setPid(String pid) {
		this.pid = pid;
	}
	public String getXpath() {
		return xpath;
	}
	public void setXpath(String xpath) {
		this.xpath = xpath;
	}
	public String getObjectId() {
		return objectId;
	}
	public void setObjectId(String objectId) {
		this.objectId = objectId;
	}
	public String getFname() {
		return fname;
	}
	public void setFname(String fname) {
		this.fname = fname;
	}
//	public String getOriginalPath() {
//		return originalPath;
//	}
//	public void setOriginalPath(String originalPath) {
//		this.originalPath = originalPath;
//	}
	public Long getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Long createTime) {
		this.createTime = createTime;
	}
//	public long getMid() {
//		return mid;
//	}
//	public void setMid(long mid) {
//		this.mid = mid;
//	}
	public String getUname() {
		return uname;
	}
	public void setUname(String uname) {
		this.uname = uname;
	}
	public String getFtype() {
		return ftype;
	}
	public void setFtype(String ftype) {
		this.ftype = ftype;
	}
	public String getExt() {
		return ext;
	}
	public void setExt(String ext) {
		this.ext = ext;
	}
	public String getIco() {
		return ico;
	}
	public void setIco(String ico) {
		this.ico = ico;
	}
	public Long getLastModified() {
		return lastModified;
	}
	public void setLastModified(Long lastModified) {
		this.lastModified = lastModified;
	}
	public Long getFsize() {
		return fsize;
	}
	public void setFsize(Long fsize) {
		this.fsize = fsize;
	}
	public String getFgroup() {
		return fgroup;
	}
	public void setFgroup(String fgroup) {
		this.fgroup = fgroup;
	}
	public String getFsgroup() {
		return fsgroup;
	}
	public void setFsgroup(String fsgroup) {
		this.fsgroup = fsgroup;
	}
	public String getXcode() {
		return xcode;
	}
	public void setXcode(String xcode) {
		this.xcode = xcode;
	}
	public String getDeleted() {
		return deleted;
	}
	public void setDeleted(String deleted) {
		this.deleted = deleted;
	}
	public String getAlias() {
		return alias;
	}
	public void setAlias(String alias) {
		this.alias = alias;
	}
	
}
