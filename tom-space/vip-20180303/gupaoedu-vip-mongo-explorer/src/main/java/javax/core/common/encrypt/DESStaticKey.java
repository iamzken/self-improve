package javax.core.common.encrypt;

import java.net.URLDecoder;
import java.net.URLEncoder;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

public class DESStaticKey {

	/**
	 * DES 加密，解决中文乱码问题
	 * @param str
	 * @param key
	 * @param charset
	 * @return
	 */
	public static String encrypt(String str, String key,String charset) {
		try {
			if(str == null || str.length() < 1) return "";
			DESKeySpec keySpec = new DESKeySpec(key.getBytes());
			SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
			SecretKey secretKey = keyFactory.generateSecret(keySpec);
			Cipher c1 = Cipher.getInstance("DES");
			c1.init(Cipher.ENCRYPT_MODE, secretKey);
			byte[] cipherByte = c1.doFinal(str.getBytes());
			String result = bytes2Hex(cipherByte);
			if(!(null == charset || charset.length() < 1)){
				result = URLEncoder.encode(result, charset);
			}
			return result;
		} catch (Exception e) {
			return "";
		}
	}

	/**
	 * DES 加密，解决中文乱码问题
	 * @param str
	 * @param key
	 * @param charset
	 * @return
	 */
	public static String decrypt(String str, String key,String charset) {
		try {
			if(str == null || str.length() < 1) return "";
			DESKeySpec keySpec = new DESKeySpec(key.getBytes());
			SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
			SecretKey secretKey = keyFactory.generateSecret(keySpec);
			Cipher c1 = Cipher.getInstance("DES");
			c1.init(Cipher.DECRYPT_MODE, secretKey);
			String result = new String(c1.doFinal(hex2byte(str)));
			if(!(null == charset || charset.length() < 1)){
				result = URLDecoder.decode(result, charset);
			}
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}
	
	
	public static String encrypt(String str, String key) {
		return encrypt(str,key,null);
	}

	public static String decrypt(String str, String key) {
		return decrypt(str, key,null);
	}

	private static String bytes2Hex(byte[] bts) {
		String des = "";
		String tmp = null;
		for (int i = 0; i < bts.length; i++) {
			tmp = (Integer.toHexString(bts[i] & 0xFF));
			if (tmp.length() == 1) {
				des += "0";
			}
			des += tmp;
		}
		return des;
	}
	
	private static byte[] hex2byte(String hexStr){
		try{
	        byte[] bts = new byte[hexStr.length() / 2];
	        for (int i = 0,j=0; j < bts.length; j++ ) {
	           bts[j] = (byte) Integer.parseInt(hexStr.substring(i, i+2), 16);
	           i+=2;
	        }
	        return bts;
		} catch(Exception e){
			return "".getBytes();
		}
    }

	public static void main(String[] args) throws Exception {
		String str="6cb2606b4382afd1659a4b4031f65a2c6ef727f10c03de27f4ed22e84d8ef49a8ca9af6dd4f68fc2a9c1f322be1043efd50b4070bb33a84c1c4433fdfea98cb913f7f76703e7c5ded1c494c2cc288c4bd055d335bf2a0a779135eb694b5da73e6499b1cd74d2b3e957eb793aabc23b1ca7949c3eb28abfce76ce7c7e4120d8ecbd74b61603637e817a7fce656a32f81ecea8a1ac96c701c6";
		System.out.println(DESStaticKey.decrypt(str, "402880E6"));
//		System.out.println(decrypt("cde01d1bc4311736", "sDx5show"));
		//System.out.println(hex2byte("a13qswdswqe").length);
	}

}
