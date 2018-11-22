package javax.core.common.encrypt;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

import javax.core.common.utils.StringUtils;


public class DESMessageEncrypt extends MessageEncrypt {
	@Override
	public byte[] encode(byte[] input) {
		try {
			Cipher c1 = Cipher.getInstance("DES");
			c1.init(Cipher.ENCRYPT_MODE, getSecretKey());
			byte[] codes =  c1.doFinal(input);
			return  StringUtils.bytes2Hex(codes).getBytes();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			e.printStackTrace();
		} catch (IllegalBlockSizeException e) {
			e.printStackTrace();
		} catch (BadPaddingException e) {
			e.printStackTrace();
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@Override
	public byte[] decode(byte[] input) {
		try {			
			Cipher c1 = Cipher.getInstance("DES");
			c1.init(Cipher.DECRYPT_MODE,getSecretKey());
			return c1.doFinal(StringUtils.hex2Bytes(new String(input)));
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			e.printStackTrace();
		} catch (IllegalBlockSizeException e) {
			e.printStackTrace();
		} catch (BadPaddingException e) {
			e.printStackTrace();
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	protected SecretKey secretKey;
	
	protected SecretKey getSecretKey() {
		if(secretKey==null){
			SecretKeyFactory keygen=null;
			try {
				keygen = SecretKeyFactory.getInstance("DES");
			} catch (NoSuchAlgorithmException e) {
				e.printStackTrace();
			}
			
			DESKeySpec keySpec=null;
			try {
				//keySpec = new DESKeySpec(LuceneLabContext.getString("s_a").getBytes());
				keySpec = new DESKeySpec("sDx5show".getBytes());				
			} catch (InvalidKeyException e) {
				e.printStackTrace();
			}			 
			try {
				secretKey= keygen.generateSecret(keySpec);
			} catch (InvalidKeySpecException e) {
				e.printStackTrace();
			}
		}
		return secretKey;
	}
}
