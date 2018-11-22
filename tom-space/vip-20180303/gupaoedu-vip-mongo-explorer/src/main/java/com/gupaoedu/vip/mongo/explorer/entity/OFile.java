package com.gupaoedu.vip.mongo.explorer.entity;

import java.io.*;
import java.util.ArrayList;

import com.twmacinta.util.MD5;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "t_object_file")
public class OFile extends File {

	private static final long serialVersionUID = 1L;
	private OFileInfo fileInfo = new OFileInfo();
	private boolean isOfile = false;
    private String name;

    public OFile(File file) {
    	this(file.getAbsolutePath());
    }

	public OFile(String pathname) {
		super(pathname);
		isOfile = isOFile();
	}

    @Override
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId(){
	    try {
            return MD5.asHex(MD5.getHash(this));
        }catch (Exception e){
	        e.printStackTrace();
        }
        return null;
	}



	@Override
	public OFile[] listFiles() {
		String[] files = list();
		OFile[] fs = null;
		if (files != null) {
			int n = files.length;
			fs = new OFile[n];
			for (int i = 0; i < n; i++) {
				fs[i] = new OFile(getPath() + "/" + files[i]);
			}
		}
		return fs;
	}
	
	@Override
	public OFile[] listFiles(FilenameFilter filter) {
		String ss[] = list();
		if (ss == null) return null;
		ArrayList<OFile> v = new ArrayList<OFile>();
		for (int i = 0; i < ss.length; i++) {
			if ((filter == null) || filter.accept(this, ss[i])) {
				v.add(new OFile(getPath() + "/" + ss[i]));
			}
		}
		return (OFile[]) (v.toArray(new File[v.size()]));
	}

	/**
	 * 取文件大小
	 */
	@Override
	public long length() {
		if (fileInfo.length == null) {
			if (fileInfo.realFile() != null) {
				return fileInfo.realFile().length();
			}
		} else {
			return fileInfo.length;
		}
		return super.length();
	}
	
	public boolean read(){
		ObjectInputStream in = null;
		try {
			in = new ObjectInputStream(new FileInputStream(this));
			fileInfo = (OFileInfo) (in.readObject());
			in.close(); 
			return true;
		} catch (Exception e) {
			if(in != null) try { in.close(); } catch (IOException e1) {}
		}
		return false;
	}
	
	private boolean isOFile(){
		return (this.length() < 1024 && read());
	}

	
	public OFileInfo getInfo(){
		return this.fileInfo;
	}


	class OFileInfo implements Externalizable {
		private static final long serialVersionUID = 1L;

		File realFile = null;
		String fileCode = null;
		String canonicalPath = null;
		Long length = null;

		public OFileInfo() { }

		/**
		 * 获取真实文件
		 *
		 * @return
		 */
		File realFile() {
			if (realFile == null) {
				if (canonicalPath == null) {
					return null;
				}
				realFile = new File(canonicalPath);
			}
			return realFile;
		}


		@Override
		public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
			this.canonicalPath = (String) in.readObject();
			this.length = (Long) in.readObject();
			this.fileCode = (String) in.readObject();
		}

		@Override
		public void writeExternal(ObjectOutput out) throws IOException {
			out.writeObject(realFile.getCanonicalPath());
			out.writeObject(realFile.length());
			out.writeObject(fileCode);
		}

		public String getFileCode(){
			return this.fileCode;
		}
	}

}
