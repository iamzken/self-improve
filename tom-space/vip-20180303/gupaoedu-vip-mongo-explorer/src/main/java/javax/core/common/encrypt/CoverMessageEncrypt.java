package javax.core.common.encrypt;

import java.io.UnsupportedEncodingException;
import java.util.Random;

import javax.core.common.utils.StringUtils;
 

public class CoverMessageEncrypt extends MessageEncrypt {

	public static final String NAME="cover";

	
	private static long time;
	static {
		
		Thread t = new Thread(new Runnable() {			
			public void run() {
				while(true){
					time = System.currentTimeMillis()^0x863FA34;
					try {
						Thread.sleep(24*3600);//wait one day
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		});
		t.setDaemon(true);
		
		t.start();
		
	}

	public String decode(String input){
		int nConLen = input.length();
		byte []byKey = StringUtils.hex2Bytes(input.substring(nConLen - 16, nConLen));
		byte[] byteResult = StringUtils.hex2Bytes(input.substring(0, nConLen - 16));

		for (int n = 0; n < byteResult.length; n++) {
			byteResult[n] -= byKey[byKey.length - 1];
		}
		try {
			return new String(byteResult,"GBK");
		} catch (UnsupportedEncodingException e) {
			return new String(byteResult);
		}
	}

	@Override
	public byte[] decode(byte[] input) {				
		return decode(new String(input)).getBytes();
	}

	@Override
	public byte[] encode(byte[] input) {
		return StringUtils.bytes2Hex(RandEncode(input)).getBytes();
	}

	private byte[] getRandBytes(){
		Random rand = new Random(time);
		byte []m_byteRand = new byte[8];
		int n = 0;
		while (n % 10 == 0) {
			n = rand.nextInt();
		}
		n=n>0?n:-n;	    

		byte[] bytesRand = String.valueOf(n).getBytes();

		for (int i = 7,j=bytesRand.length-1; i >=0&& j>=0 ; i--,j--) {
			m_byteRand[i] = bytesRand[j];
		}
		return m_byteRand;
	}




	/**
	 *   bySrc[n]  =  
	 * @param bySrc
	 * @return
	 */
	private byte[] RandEncode(byte []bySrc) {
		byte[] m_byteRand = getRandBytes();				
		byte[] result = new byte[bySrc.length+m_byteRand.length];
		System.arraycopy(bySrc, 0, result, 0, bySrc.length);
		System.arraycopy(m_byteRand, 0, result, bySrc.length, m_byteRand.length);
		for (int n = 0; n < bySrc.length; n++) {
			result[n] += m_byteRand[m_byteRand.length-1];
		}

		return result;
	}
		 
		 public static void main(String[] args) {
			 MessageEncrypt encrypt = new CoverMessageEncrypt();
			 
			 System.out.println(encrypt.encode("2212"));
			 System.out.println(encrypt.encode("2212"));
		}

}
