package com.gupaoedu;

import java.util.ArrayList;
import java.util.List;

import com.gupaoedu.bean.InnerTableListBean;
import com.gupaoedu.bean.MainInfoBean;
import com.gupaoedu.util.Office2PDFUtil;
import com.gupaoedu.util.PoiUtil;

/**
 * 咕泡学院，只为更好的你
 */
public class PoiTest {
	public static void main(String[] args) throws Exception {

		String pactUrl = "D:\\template\\template_blank.docx";
		MainInfoBean bean = new MainInfoBean();

		bean.setRetailName("巨有钱金融公司");
		bean.setRetailRegcode("JHFDJKSAJKJD673462");
		bean.setRetailAddr("湖南省长沙市");
		bean.setLegalName("王旺旺");
		bean.setPrimUserEmail("123456@qq.com");
		bean.setPrimUserPhone("13866669999");

		List<InnerTableListBean> storeList = new ArrayList<InnerTableListBean>();
		InnerTableListBean store1 = new InnerTableListBean();
		store1.setPrimUserPhone("13755557777");
		store1.setStoreAddr("步步高二楼");
		store1.setStoreName("大哥大通讯");
		store1.setStoreRegCode("RJKFDSA515234864554");
		storeList.add(store1);

		InnerTableListBean store2 = new InnerTableListBean();
		store2.setPrimUserPhone("13522221111");
		store2.setStoreAddr("五一广场");
		store2.setStoreName("摩拜手机城");
		store2.setStoreRegCode("FJYJKLFD4454545432");
		storeList.add(store2);

		String wordUrl = PoiUtil.replaceParams(pactUrl, bean, storeList, "D:\\template\\");
		String fileName = wordUrl.substring(wordUrl.lastIndexOf("/")+1, wordUrl.lastIndexOf(".docx"));
		String _pdfUrl =  fileName + ".pdf";

		Office2PDFUtil.transfrom(wordUrl, _pdfUrl);
	}
}
