package com.gupaoedu.qrcode;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.gupaoedu.qrcode.utils.QRCodeUtils;

public class QRCodeServlet extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		this.doPost(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		StringBuffer content = new StringBuffer();
		
		content.append("BEGIN:VCARD" + "\n");
		content.append("FN:" + req.getParameter("name") + "\n");
		content.append("TEL;WORK:" + req.getParameter("tel") + "\n");
		content.append("ORG:" + req.getParameter("org") + "\n");
		content.append("EMAIL;WORK:" + req.getParameter("email") + "\n");
		content.append("URL:" + req.getParameter("site") + "\n");
		content.append("ADR;WORK:" + req.getParameter("addr") + "\n");
		content.append("NOTE:" + req.getParameter("note") + "\n");
		content.append("END:VCARD" + "\n");
	
		QRCodeUtils.encoderQRCode(content.toString(),resp.getOutputStream(),"jpg",14);
		resp.flushBuffer();
	}
	
	
}
