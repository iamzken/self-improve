package javax.core.common.encrypt;

public class MD5 {

	public static String calcMD5(String str) {
		return MessageEncrypt.getInstance("md5").encode(str);
	}

}
