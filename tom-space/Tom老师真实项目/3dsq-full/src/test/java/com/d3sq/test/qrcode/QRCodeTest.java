package com.d3sq.test.qrcode;

import javax.core.common.utils.QRCodeUtils;

public class QRCodeTest {
	public static void main(String[] args) {
		
		String content = "WIFI:S:TP-LINK_TANYONGDE;T:WPA;P:http://www.tom.com;";
		QRCodeUtils.encoderQRCode(content, "/Users/tom/Desktop/WIFI.png", "png",4);
		
	}
}
