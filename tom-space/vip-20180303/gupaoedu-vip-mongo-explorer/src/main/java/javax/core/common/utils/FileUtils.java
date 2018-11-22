package javax.core.common.utils;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.RandomAccessFile;
import java.net.URL;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;

/**
 * 提供各种对文件系统进行操作的工具. <br>
 * 在应用程序中，经常需要对文件系统进行操作，例如复制、移动、删除文件，查找文件的路径，对文件进行写操作等， 类FileUtils提供了与此相关的各种工具。
 * 
 * @author Tanyongde
 */

@SuppressWarnings("unchecked")
public class FileUtils {
	private static final File POOL_FILE = getUniqueFile(FileUtils.class,
			".deletefiles");

	private static ArrayList<File> deleteFilesPool;
	static {
		try {
			initPool();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 构造方法，禁止实例化
	 */
	private FileUtils() {}
	
	/**
	 * 读出以前未删除的文件列表
	 * 
	 * @throws Exception
	 * @throws IOException
	 */
	@SuppressWarnings("unchecked")
	private static void initPool() {
		if (POOL_FILE.exists() && POOL_FILE.canRead()) {
			ObjectInputStream in = null;
			try {
				in = new ObjectInputStream(new FileInputStream(POOL_FILE));
				deleteFilesPool = (ArrayList) in.readObject();

			} catch (Exception e) {
				deleteFilesPool = new ArrayList<File>();
			} finally {
				if (in != null) {
					try {
						in.close();
					} catch (Exception e) {// do nothing

					}
				}
			}
		} else {
			deleteFilesPool = new ArrayList<File>();
		}

	}


	/**
	 * 得到短文件名. <br>
	 * <br>
	 * <b>示例: </b> <br>
	 * FileUtils.getShortFileName(&quot;/home/app/config.xml&quot;) 返回
	 * &quot;config.xml&quot;
	 * FileUtils.getShortFileName(&quot;C:\\test\\config.xml&quot;) 返回
	 * &quot;config.xml&quot;</br>
	 * 
	 * @param fileName
	 *            文件名
	 * @return 短文件名
	 */
	public static String getShortFileName(String fileName) {
		String shortFileName = "";
		int pos = fileName.lastIndexOf('\\');
		if (pos == -1) {
			pos = fileName.lastIndexOf('/');
		}
		if (pos > -1) {
			shortFileName = fileName.substring(pos + 1);
		} else {
			shortFileName = fileName;
		}
		return shortFileName;
	}

	/**
	 * 得到不带扩展名的短文件名. <br>
	 * <br>
	 * <b>示例: </b> <br>
	 * FileUtils.getShortFileNameWithoutExt(&quot;/home/app/config.xml&quot;) 返回
	 * &quot;config&quot;<br>
	 * FileUtils.getShortFileNameWithoutExt(&quot;C:\\test\\config.xml&quot;) 返回
	 * &quot;config&quot;</br>
	 * 
	 * @param fileName
	 *            文件名
	 * @return 短文件名
	 */
	public static String getShortFileNameWithoutExt(String fileName) {
		String shortFileName = getShortFileName(fileName);
		shortFileName = getFileNameWithoutExt(shortFileName);
		return shortFileName;
	}

	/**
	 * 得到文件内容
	 * 
	 * @param fileName
	 *            文件名称
	 * @return 文件内容
	 * @throws Exception
	 */
	public static String read(String fileName) throws Exception {
		return read(new File(fileName));
	}

	/**
	 * 得到文件内容
	 * 
	 * @param file
	 *            文件
	 * @return 文件内容
	 * @throws Exception
	 */
	public static String read(File file) throws Exception {
		String fileContent = "";
		FileInputStream in = null;
		try {
			in = new FileInputStream(file);
			fileContent = read(in);
		} finally {
			if (in != null) {
				in.close();
			}
		}
		return fileContent;
	}

	/**
	 * 得到输入流的内容
	 * 
	 * @param is
	 *            输入流
	 * @return 字符串
	 * @throws Exception
	 */
	public static String read(InputStream is) throws Exception {
		byte[] result = readBytes(is);
		return new String(result);
	}

	/**
	 * 以byte数组方式得到输入流的内容
	 * 
	 * @param fileName
	 *            文件名称
	 * @return byte数组
	 * @throws Exception
	 */
	public static byte[] readBytes(String fileName) throws Exception {
		return readBytes(new FileInputStream(fileName));
	}

	/**
	 * 以byte数组方式得到输入流的内容
	 * 
	 * @param file
	 *            文件
	 * @return byte数组
	 * @throws Exception
	 */
	public static byte[] readBytes(File file) throws Exception {
		return readBytes(new FileInputStream(file));
	}

	/**
	 * 以byte数组方式得到输入流的内容
	 * 
	 * @param is
	 *            输入流
	 * @return byte数组
	 * @throws Exception
	 */
	public static byte[] readBytes(InputStream is) throws Exception {
		if (is == null || is.available() < 1) {
			return new byte[0];
		}
		byte[] buff = new byte[8192];
		byte[] result = new byte[is.available()];
		int nch;
		BufferedInputStream in = null;
		try {
			in = new BufferedInputStream(is);
			int pos = 0;
			while ((nch = in.read(buff, 0, buff.length)) != -1) {
				System.arraycopy(buff, 0, result, pos, nch);
				pos += nch;
			}
		} finally {
			if (in != null) {
				in.close();
			}
		}
		return result;
	}

	/**
	 * 写文件
	 * 
	 * @param content
	 *            文件内容
	 * @param file
	 *            文件对象
	 * @throws IOException
	 */
	public static void write(String content, File file) throws IOException {
		write(content.getBytes(), file);
	}

	/**
	 * 写文件
	 * 
	 * @param content
	 *            文件内容
	 * @param file
	 *            文件名
	 * @throws IOException
	 */
	public static void write(String content, String file) throws IOException {
		write(content, new File(file));
	}

	/**
	 * 写文件
	 * 
	 * @param bytes
	 *            文件内容
	 * @param file
	 *            文件名
	 * @throws IOException
	 */
	public static void write(byte[] bytes, String file) throws IOException {
		write(bytes, new File(file));
	}

	/**
	 * 写文件
	 * 
	 * @param bytes
	 *            文件内容
	 * @param file
	 *            文件
	 * @throws IOException
	 */
	public static void write(byte[] bytes, File file) throws IOException {
		FileOutputStream out = null;
		try {
			out = new FileOutputStream(file);
			out.write(bytes);
			out.flush();
		} finally {
			if (out != null) {
				out.close();
			}
		}
	}

	/**
	 * 返回不带扩展名的文件名
	 * 
	 * @param fileName
	 *            原始文件名
	 * @return 不带扩展名的文件名
	 */
	public static String getFileNameWithoutExt(String fileName) {
		String shortFileName = fileName;
		if (fileName.indexOf('.') > -1) {
			shortFileName = fileName.substring(0, fileName.lastIndexOf('.'));
		}
		return shortFileName;
	}

	/**
	 * 返回文件扩展名,带“.”
	 * 
	 * @param fileName
	 *            原始文件名
	 * @return 文件扩展名
	 */
	public static String getFileNameExt(String fileName) {
		String fileExt = "";
		if (fileName.indexOf('.') > -1) {
			fileExt = fileName.substring(fileName.lastIndexOf('.'));
		}
		return fileExt;
	}

	/**
	 * 得到唯一文件
	 * 
	 * @param fileName
	 *            原始文件名
	 * @return File
	 */
	public synchronized static File getUniqueFile(File repository,
			String fileName) {
		String shortFileName = getShortFileName(fileName);
		String tempFileName = getFileNameWithoutExt(shortFileName);
		File file = new File(repository, shortFileName);
		String fileExt = getFileNameExt(shortFileName);
		while (file.exists()) {
			file = new File(repository, tempFileName + "-"
					+ Math.abs(Math.random() * 1000000) + fileExt);
		}
		return file;
	}


	/**
	 * 删除文件方法，如果删除不掉，将该文件加入删除池，下次进行调用时将尝试删除池中的文件
	 * 
	 * @param file
	 *            file
	 */
	public static void deleteFile(File file) {
		file.delete();// 尝试删除文件
		if (file.exists()) {
			deleteFilesPool.add(file);
		}
		checkDeletePool();
	}

	/**
	 * 检查池，删除池中文件，如果删除成功则同时从池中移除。
	 */
	private static void checkDeletePool() {
		File file;
		for (int i = deleteFilesPool.size() - 1; i >= 0; i--) {
			file = (File) deleteFilesPool.get(i);
			file.delete();
			if (file.exists() == false) {
				deleteFilesPool.remove(i);
			}
		}
		ObjectOutputStream out = null;
		try {
			out = new ObjectOutputStream(new FileOutputStream(POOL_FILE));
			out.writeObject(deleteFilesPool);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (out != null) {
				try {
					out.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * 得到唯一文件。一个类处在某个位置的class或jar包中，根据此位置得到此类对应的文件。<br>
	 * 不同位置的类得到的文件是不一样的。
	 * 
	 * @param cl
	 *            类
	 * @param extension
	 *            带点的文件扩展名
	 * @return File
	 */
	public static File getUniqueFile(Class cl, String extension) {
		int key = 0;
		URL url = cl.getResource(getClassNameWithoutPackage(cl) + ".class");
		if (url != null) {
			key = url.getPath().hashCode();
		}
		File propFile = new File(System.getProperty("java.io.tmpdir"),
				getClassNameWithoutPackage(cl) + key + extension);
		return propFile;
	}

	private static String getClassNameWithoutPackage(Class cl) {
		String className = cl.getName();
		int pos = className.lastIndexOf('.') + 1;
		if (pos == -1)
			pos = 0;
		String name = className.substring(pos);
		return name;
	}

	/**
	 * 删除文件夹（不管是否文件夹为空）<br>
	 * 注意：非原子操作，删除文件夹失败时，并不能保证没有文件被删除。 * <br>
	 * <b>示例: </b> <br>
	 * FileUtils.deleteFolder(&quot;/home/tmp&quot;) 删除成功返回true.<br>
	 * FileUtils.deleteFolder(&quot;C:\\test&quot;) 删除成功返回true.</br>
	 * 
	 * @param delFolder
	 *            待删除的文件夹
	 * @return 如果删除成功则返回true，否则返回false
	 */
	public static boolean deleteFolder(File delFolder) {
		// 目录是否已删除
		boolean hasDeleted = true;
		// 得到该文件夹下的所有文件夹和文件数组
		File[] allFiles = delFolder.listFiles();

		for (int i = 0; i < allFiles.length; i++) {
			// 为true时操作
			if (hasDeleted) {
				if (allFiles[i].isDirectory()) {
					// 如果为文件夹,则递归调用删除文件夹的方法
					hasDeleted = deleteFolder(allFiles[i]);
				} else if (allFiles[i].isFile()) {
					try {// 删除文件
						if (!allFiles[i].delete()) {
							// 删除失败,返回false
							hasDeleted = false;
						}
					} catch (Exception e) {
						// 异常,返回false
						hasDeleted = false;
					}
				}
			} else {
				// 为false,跳出循环
				break;
			}
		}
		if (hasDeleted) {
			// 该文件夹已为空文件夹,删除它
			delFolder.delete();
		}
		return hasDeleted;
	}

	/**
	 * 得到Java类所在的实际位置。一个类处在某个位置的class或jar包中，根据此位置得到此类对应的文件。<br>
	 * 不同位置的类得到的文件是不一样的。
	 * 
	 * @param cl
	 *            类
	 * @return 类在系统中的实际文件名
	 */
	public static String getRealPathName(Class cl) {

		URL url = cl.getResource(getClassNameWithoutPackage(cl) + ".class");
		if (url != null) {
			return url.getPath();
		}
		return null;
	}
	
	/**
	 * 获得 机器上 所有磁盘
	 * @return
	 */
	public static List<String> getDiskPath() {
		List<String> disks = new ArrayList<String>();
		File[] fs = File.listRoots();
		for (int i = 0; i < fs.length; i++) {
			disks.add(fs[i].getPath());
		}
		return disks;
	}

	/**
	 * 递归读取文件下面的所有文件
	 * @param filePath
	 * @return
	 */
	public static List<File> listFiles(String filePath) {
		return listFiles(filePath, null);
	}

	/**
	 * 递归读取文件下面的满足过滤器要求的文件
	 * @param filePath
	 * @return
	 */
	public static List<File> listFiles(String filePath, FileFilter filter) {
		List<File> fileList = new ArrayList<File>();
		try {
			File file = new File(filePath);
			File[] files = null;
			if (null != filter) {
				files = file.listFiles(filter);
			} else {
				files = file.listFiles();
			}
			for (int i = 0; i < files.length; i++) {
				File tempFile = files[i];
				if (tempFile.isDirectory()) {
					String subDirName = tempFile.getPath();
					List<File> list = null;
					list = listFiles(subDirName, filter);
					for (int j = 0; j < list.size(); j++) {
						fileList.add(list.get(j));
					}
				} else {
					if (!tempFile.isFile()) {
						continue;
					}
					fileList.add(tempFile);
				}
				if (i == (files.length - 1)) {
					return fileList;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return fileList;
	}

	/**
	 * 用zip格式压缩文件
	 * @param zipFileName 压缩后的文件名 包含路径 如："c:\\test.zip"
	 * @param inputFile 要压缩的文件 可以是文件或文件夹 如："c:\\test" 或 "c:\\test.doc"
	 * @throws Exception

	 *ant下的zip工具默认压缩编码为UTF-8编码，而winRAR软件压缩是用的windows默认的GBK或者GB2312编码

	 *用ant压缩后放到windows上面会出现中文文件名乱码，用winRAR压缩的文件，用ant解压也会出现乱码，

	 *所以，在用ant处理winRAR压缩的文件时，要设置压缩编码
	 */
	public static boolean zip(String sourceFilePath, String targetFileName) {
		boolean flag = false;
		try {
			File f = new File(sourceFilePath);
			ZipOutputStream out = new ZipOutputStream(new FileOutputStream(
					targetFileName));
			//设置压缩编码
			//out.setEncoding("GBK");//设置为GBK后在windows下就不会乱码了，如果要放到Linux或者Unix下就不要设置了
			flag = zip(out, f, "");// 递归压缩方法
			out.close();
			return flag;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	private static boolean zip(ZipOutputStream out, File f, String base) {
		try {
			if (f.isDirectory()) { // 如果是文件夹，则获取下面的所有文件
				File[] fl = f.listFiles();
				out.putNextEntry(new ZipEntry(base + "/"));// 此处要将文件写到文件夹中只用在文件名前加"/"再加文件夹名
				base = base.length() == 0 ? "" : base + "/";
				for (int i = 0; i < fl.length; i++) {
					zip(out, fl[i], base + fl[i].getName());
				}
			} else { // 如果是文件，则压缩
				out.putNextEntry(new ZipEntry(base)); // 生成下一个压缩节点
				FileInputStream in = new FileInputStream(f); // 读取文件内容
				int len;
				byte[] buf = new byte[1024];
				while ((len = in.read(buf, 0, 1024)) != -1) {
					out.write(buf, 0, len); // 写入到压缩包
				}
				in.close();
			}
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * 
	 * 解压缩zip文件，支持中文
	 * 
	 * @param zipFilePath
	 *            要解压的zip文件对象
	 * @param outputFileName
	 *            要解压到某个指定的目录下
	 * @throws IOException
	 * 
	 */
	public static boolean unZip(String zipFilePath, String outputFileName) {
		return unZip(zipFilePath, outputFileName, false);
	}

	/**
	 * 
	 * 解压缩zip文件，支持中文
	 * 
	 * @param zipFilePath
	 *            要解压的zip文件对象
	 * @param outputFileName
	 *            要解压到某个指定的目录下
	 * @param deleteFile
	 *            解压完毕后,是否同时删除压缩文件
	 * @throws IOException
	 * 
	 */
	public static boolean unZip(String zipFilePath, String outputFileName,
			boolean deleteFile) {
		try {
			File file = new File(zipFilePath);
			if (!file.exists()) {
				return false;
			}
			ZipFile zipFile = new ZipFile(file);
			Enumeration e = zipFile.entries();
			while (e.hasMoreElements()) {
				ZipEntry zipEntry = (ZipEntry) e.nextElement();
				if (zipEntry.isDirectory()) {
					String name = zipEntry.getName();
					name = name.substring(0, name.length() - 1);
					File f = new File(outputFileName + name);
					f.mkdirs();
				} else {
					File f = new File(outputFileName + zipEntry.getName());
					f.getParentFile().mkdirs();
					f.createNewFile();
					InputStream is = zipFile.getInputStream(zipEntry);
					FileOutputStream fos = new FileOutputStream(f);
					int length = 0;
					byte[] b = new byte[1024];
					while ((length = is.read(b, 0, 1024)) != -1) {
						fos.write(b, 0, length);
					}
					is.close();
					fos.close();
				}
			}

			if (zipFile != null) {
				zipFile.close();
			}

			if (deleteFile) {
				file.deleteOnExit();
			}
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 *  
	 *  复制文件夹
	 *  
	 * @param sourceDirPath
	 * @param targetDirPath
	 * @throws IOException
	 */
	public static boolean copyDirectiory(String sourceDirPath,
			String targetDirPath, String sourceDestCoding,
			String targetDestCoding) {
		boolean flag = false;
		try {
			// 创建目标文件夹
			(new File(targetDirPath)).mkdirs();
			// 获取源文件夹当前下的文件或目录
			File[] file = (new File(sourceDirPath)).listFiles();
			for (int i = 0; i < file.length; i++) {
				if (file[i].isFile()) {
					// 复制文件
					String type = file[i].getName().substring(
							file[i].getName().lastIndexOf(".") + 1);

					if (type.equalsIgnoreCase("txt"))
						copyFile(file[i].getAbsolutePath(), targetDirPath
								+ file[i].getName(), sourceDestCoding,
								targetDestCoding);
					else
						copyFile(file[i].getAbsolutePath(), targetDirPath
								+ file[i].getName());
				}
				if (file[i].isDirectory()) {
					// 复制目录
					String sourceDir = sourceDirPath + File.separator
							+ file[i].getName();
					String targetDir = targetDirPath + File.separator
							+ file[i].getName();
					copyDirectiory(sourceDir, targetDir);
				}
			}
			flag = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}

	/**
	 * 剪切文件夹
	 * @param sourceFilePath
	 * @param targetFilePath
	 * @return
	 */
	public static boolean cutDirectiory(String sourceFilePath,
			String targetFilePath) {
		boolean flag = false;
		if (copyDirectiory(sourceFilePath, targetFilePath)) {
			flag = deleteDirectory(sourceFilePath);
		}
		return flag;
	}

	/**
	 * 剪切文件
	 * @param sourceFilePath
	 * @param targetFilePath
	 * @return
	 */
	public static boolean cutFile(String sourceFilePath, String targetFilePath) {
		boolean flag = false;
		if (copyFile(sourceFilePath, targetFilePath)) {
			flag = deleteFile(sourceFilePath);
		}
		return flag;
	}

	/**
	 * 复制文件
	 */
	public static boolean copyFile(String sourceFilePath, String targetFilePath) {
		boolean flag = false;
		BufferedInputStream inBuff = null;
		BufferedOutputStream outBuff = null;
		try {
			// 新建文件输入流并对它进行缓冲
			inBuff = new BufferedInputStream(
					new FileInputStream(sourceFilePath));

			// 新建文件输出流并对它进行缓冲
			outBuff = new BufferedOutputStream(new FileOutputStream(
					targetFilePath));

			// 缓冲数组
			byte[] b = new byte[1024 * 5];
			int len;
			while ((len = inBuff.read(b)) != -1) {
				outBuff.write(b, 0, len);
			}
			// 刷新此缓冲的输出流
			outBuff.flush();
			flag = true;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				// 关闭流
				if (inBuff != null)
					inBuff.close();
				if (outBuff != null)
					outBuff.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return flag;
	}

	/**
	 * 复制文件夹
	 * @param sourceDir
	 * @param targetDir
	 * @throws IOException
	 */
	public static boolean copyDirectiory(String sourceFilePath,
			String targetFilePath) {
		boolean flag = false;
		try {
			// 新建目标目录
			(new File(sourceFilePath)).mkdirs();
			// 获取源文件夹当前下的文件或目录
			File[] file = (new File(sourceFilePath)).listFiles();
			for (int i = 0; i < file.length; i++) {
				if (file[i].isFile()) {
					// 源文件
					File sourceFile = file[i];
					// 目标文件
					File targetFile = new File(new File(targetFilePath)
							.getAbsolutePath()
							+ File.separator + file[i].getName());
					copyFile(sourceFile.getAbsolutePath(), targetFile
							.getAbsolutePath());
				}
				if (file[i].isDirectory()) {
					// 准备复制的源文件夹
					String dir1 = sourceFilePath + "/" + file[i].getName();
					// 准备复制的目标文件夹
					String dir2 = targetFilePath + "/" + file[i].getName();
					copyDirectiory(dir1, dir2);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}

	/**
	 * 复制文件,并设置编码
	 * @param srcFileName
	 * @param destFileName
	 * @param srcCoding
	 * @param destCoding
	 * @throws IOException
	 */
	public static boolean copyFile(String srcFilePath, String destFilePath,
			String srcCoding, String destCoding) {
		boolean flag = false;
		BufferedReader br = null;
		BufferedWriter bw = null;
		try {
			br = new BufferedReader(new InputStreamReader(new FileInputStream(
					srcFilePath), srcCoding));
			bw = new BufferedWriter(new OutputStreamWriter(
					new FileOutputStream(destFilePath), destCoding));
			char[] cbuf = new char[1024 * 5];
			int len = cbuf.length;
			int off = 0;
			int ret = 0;
			while ((ret = br.read(cbuf, off, len)) > 0) {
				off += ret;
				len -= ret;
			}
			bw.write(cbuf, 0, off);
			bw.flush();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (br != null)
					br.close();
				if (bw != null)
					bw.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return flag;
	}

	/**  
	 * 根据路径删除指定的目录或文件，无论存在与否  
	 *@param sPath  要删除的目录或文件  
	 *@return 删除成功返回 true，否则返回 false
	 */
	public boolean deleteFolder(String sPath) {
		boolean flag = false;
		File file = new File(sPath);
		// 判断目录或文件是否存在   
		if (!file.exists()) { // 不存在返回 false   
			return flag;
		} else {
			// 判断是否为文件   
			if (file.isFile()) { // 为文件时调用删除文件方法   
				return deleteFile(sPath);
			} else { // 为目录时调用删除目录方法   
				return deleteDirectory(sPath);
			}
		}
	}

	/**  
	 * 删除单个文件  
	 * @param   sPath    被删除文件的文件名  
	 * @return 单个文件删除成功返回true，否则返回false  
	 */
	public static boolean deleteFile(String sourceFilePath) {
		boolean flag = false;
		File file = new File(sourceFilePath);
		// 路径为文件且不为空则进行删除   
		if (file.isFile() && file.exists()) {
			file.delete();
			flag = true;
		}
		return flag;
	}

	/**  
	 * 删除目录（文件夹）以及目录下的文件  
	 * @param   sPath 被删除目录的文件路径  
	 * @return  目录删除成功返回true，否则返回false  
	 */
	public static boolean deleteDirectory(String sourceFilePath) {
		//如果sPath不以文件分隔符结尾，自动添加文件分隔符   
		if (!sourceFilePath.endsWith(File.separator)) {
			sourceFilePath = sourceFilePath + File.separator;
		}
		File dirFile = new File(sourceFilePath);
		//如果dir对应的文件不存在，或者不是一个目录，则退出   
		if (!dirFile.exists() || !dirFile.isDirectory()) {
			return false;
		}
		boolean flag = true;
		//删除文件夹下的所有文件(包括子目录)   
		File[] files = dirFile.listFiles();
		for (int i = 0; i < files.length; i++) {
			//删除子文件   
			if (files[i].isFile()) {
				flag = deleteFile(files[i].getAbsolutePath());
				if (!flag)
					break;
			} //删除子目录   
			else {
				flag = deleteDirectory(files[i].getAbsolutePath());
				if (!flag)
					break;
			}
		}
		if (!flag)
			return false;
		//删除当前目录   
		if (dirFile.delete()) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 读取文件中内容
	 * 
	 * @param path
	 * @return
	 * @throws IOException
	 */
	public static String readFileToString(String path, String destCoding)
			throws IOException {
		String resultStr = null;
		FileInputStream fis = null;
		try {
			fis = new FileInputStream(path);
			byte[] inBuf = new byte[2000];
			int len = inBuf.length;
			int off = 0;
			int ret = 0;
			while ((ret = fis.read(inBuf, off, len)) > 0) {
				off += ret;
				len -= ret;
			}
			resultStr = new String(new String(inBuf, 0, off, destCoding)
					.getBytes());
		} finally {
			if (fis != null)
				fis.close();
		}
		return resultStr;
	}

	/**
	 * 文件转成字节数组
	 * 
	 * @param path
	 * @return
	 * @throws IOException
	 */
	public static byte[] readFileToBytes(String path) throws IOException {
		byte[] b = null;
		InputStream is = null;
		File f = new File(path);
		try {
			is = new FileInputStream(f);
			b = new byte[(int) f.length()];
			is.read(b);
		} finally {
			if (is != null)
				is.close();
		}
		return b;
	}

	/**
	 * 将byte写入文件中
	 * 
	 * @param fileByte
	 * @param filePath
	 * @throws IOException
	 */
	public static boolean saveByteToFile(byte[] fileByte, String filePath) {
		boolean flag = false;
		OutputStream os = null;
		try {
			os = new FileOutputStream(new File(filePath));
			os.write(fileByte);
			os.flush();
		} catch (Exception e) {
			flag = false;
			e.printStackTrace();
		} finally {
			if (os != null)
				try {
					os.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
		}
		return flag;
	}

	/**
	 * 将String写入到文件中
	 * 
	 * @param fileByte
	 * @param filePath
	 * @throws IOException
	 */
	public static boolean saveStringToFile(String fileString, String filePath) {
		return saveStringToFile(fileString, filePath, "GBK");
	}

	/**
	 * 将String写入到文件中
	 * 
	 * @param fileByte
	 * @param filePath
	 * @throws IOException
	 */
	public static boolean saveStringToFile(String fileString, String filePath,
			String encoding) {
		boolean flag = false;
		BufferedWriter bw = null;
		try {
			bw = new BufferedWriter(new OutputStreamWriter(
					new FileOutputStream(filePath), encoding));
			bw.append(fileString);
			bw.flush();
			flag = true;
		} catch (Exception e) {
			flag = false;
			e.printStackTrace();
		} finally {
			if (bw != null) {
				try {
					bw.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return flag;
	}

	
	/**
	 * 文件续传
	 * @param srcFile
	 * @param destFile
	 * @return
	 */
	public static boolean runUnFinished(String srcFilePath, String destFilePath) {
		boolean flag = false;
		File destFile = new File(destFilePath);
       if (destFile.exists()) {
       	FileChannel fin = null;
       	FileChannel fout = null;
           try {
               
               RandomAccessFile randomSrcFile = new RandomAccessFile(srcFilePath,"rw");
               long destFileSize = destFile.length();
               
               RandomAccessFile randomDestFile = new RandomAccessFile(destFile, "rw");
               randomSrcFile.seek(destFileSize - 1024 * 2);
               randomDestFile.seek(destFileSize - 1024 * 2);
               fin = randomSrcFile.getChannel();
               fout = randomDestFile.getChannel();

               while (true) {
                   int length = 1024 * 1024 * 2;

                   if (fin.position() == fin.size()) {
                       fin.close();
                       fout.close();
                       break;
                   }

                   if ((fin.size() - fin.position()) < 1024 * 1024 * 2 * 10) {
                       length = (int) (fin.size() - fin.position());
                   } else {
                       length = 1024 * 1024 * 2 * 10;
                   }
                   fin.transferTo(fin.position(), length, fout);
                   fin.position(fin.position() + length);
               }
               flag = true;
           } catch (Exception e) {
           	flag = false;
               e.printStackTrace();
           }finally{
           	try {
	            	if(null != fin)fin.close();
	            	if(null != fout) fout.close();
           	} catch (IOException e) {
           		e.printStackTrace();
           	}
           }
       }
       return flag;
   }
	
	public static String getFileExtName(File file){
		String fileName = file.getName();
		String extName = fileName.substring(fileName.lastIndexOf('.') + 1);
		return extName;
	}
	
}