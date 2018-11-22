package com.gupaoedu.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.poi.xwpf.usermodel.UnderlinePatterns;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableCell;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;

import com.gupaoedu.bean.InnerTableListBean;
import com.gupaoedu.bean.MainInfoBean;

/**
 * 咕泡学院，只为更好的你
 */
public class PoiUtil {

	/**
	 * 替换Word里面的变量
	 *
	 * @param pactUrl
	 * @param bean
	 * @param storeList
	 * @param wordPath
	 * @return
	 * @throws Exception
	 */
	public static String replaceParams(String pactUrl, MainInfoBean bean, List<InnerTableListBean> storeList,
									   String wordPath) throws Exception {
		InputStream in = null;
		OutputStream os = null;
		try {
			File file = new File(pactUrl);
			in = new FileInputStream(file);
			XWPFDocument docx = new XWPFDocument(in);
			PoiUtil util = new PoiUtil();
			// 商户信息转化为map
			Map<String, Object> params = util.transformBean(bean);
			// 获取商户旗下门店数量
			int storeNum = storeList.size();
			params.put("storeNum", storeNum + "");
			// 获取当前系统时间
			String[] nowDate = DataUtil.getDateYYYYMMDD2().split("-");
			// 年
			params.put("year", nowDate[0]);
			// 月
			params.put("month", nowDate[1]);
			// 日
			params.put("day", nowDate[2]);
			// 替换段落里的变量
			util.replaceInpara(docx, params);

			// 替换附件门店清单里表格的变量
			util.replaceStoreTable(docx, storeList);

			// 存放在临时目录中
			String tempFile = wordPath + File.separator + Calendar.getInstance().getTimeInMillis() + ".docx";
			File _file = new File(wordPath);
			// 如果文件夹不存在，则创建之
			if (!_file.exists()) {
				_file.mkdirs();
			}
			os = new FileOutputStream(tempFile);
			docx.write(os);
			return tempFile;
		} catch (FileNotFoundException e) {
			throw new Exception("对应的协议模板丢失！");
		} catch (Exception e) {
			throw new Exception("将协议中的变量进行替换出错！");
		} finally {
			try {
				if (os != null) {
					os.close();
				}
				if (in != null) {
					in.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 复制表格里的一行
	 *
	 * @param sourceRow
	 * @param targetRow
	 * @throws Exception
	 */
	private void copyRow(XWPFTableRow sourceRow, XWPFTableRow targetRow) throws Exception {
		List<XWPFTableCell> sourceCells = sourceRow.getTableCells();
		int size = sourceCells.size();
		for (int i = 0; i < size; i++) {
			XWPFTableCell sourceCell = sourceCells.get(i);
			XWPFTableCell newCell = targetRow.getCell(i);
			String str = sourceCell.getText();
			setCellText(sourceCell, newCell, str);
		}
	}

	/**
	 * 将某个实体类转换成Map<K,V> 利用反射来做
	 */
	private Map<String, Object> transformBean(Object bean) throws Exception {
		if (bean != null) {
			Map<String, Object> beanMap = new HashMap<String, Object>();
			// 获得类的运行时
			Class<?> c = bean.getClass();
			// 获得类的属性
			Field[] cfs = c.getDeclaredFields();
			for (Field cf : cfs) {
				// 属性的名称
				String cfName = cf.getName();
				// 如果是serialVersionUID则直接跳过
				if ("serialVersionUID".equals(cfName)) {
					continue;
				}
				// 属性对应的get方法
				String methodName = "get" + cfName.substring(0, 1).toUpperCase() + cfName.substring(1);
				Method getMethod = c.getMethod(methodName, new Class[] {});
				// 获得属性对应的值
				Object cfValue = getMethod.invoke(bean, new Object[] {});
				beanMap.put(cfName, cfValue);
			}
			return beanMap;
		} else {
			return null;
		}
	}

	/**
	 * 替换文档里的变量
	 *
	 * @param docx
	 * @param params
	 */
	private void replaceInpara(XWPFDocument docx, Map<String, Object> params) {
		Iterator<XWPFParagraph> iterator = docx.getParagraphsIterator();
		XWPFParagraph para;
		while (iterator.hasNext()) {
			para = iterator.next();
			this.replaceInpara(para, params);
		}
	}

	private void replaceInpara(XWPFParagraph para, Map<String, Object> params) {
		List<XWPFRun> runs = null;
		Set<String> keySet = params.keySet();
		String paraStr = para.getParagraphText();
		if (this.findText(paraStr, keySet)) {
			runs = para.getRuns();
			for (int i = 0; i < runs.size(); i++) {
				XWPFRun run = runs.get(i);
				String runText = run.toString();
				Iterator<String> ite = keySet.iterator();
				while (ite.hasNext()) {
					String str = ite.next();
					if (runText.equals(str)) {
						runText = params.get(str) + "";
						break;
					}
				}
				UnderlinePatterns underline = run.getUnderline();
				int fontSize = run.getFontSize();
				para.removeRun(i);
				XWPFRun _run = para.insertNewRun(i);
				_run.setText(runText);
				_run.setFontSize(fontSize);
				_run.setUnderline(underline);
			}
		}
	}

	private boolean findText(String str, Set<String> keySet) {
		for (String key : keySet) {
			if (str.contains(key)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 设置单元格文字属性
	 *
	 * @param tmpCell
	 * @param cell
	 * @param text
	 * @throws Exception
	 */
	public void setCellText(XWPFTableCell tmpCell, XWPFTableCell cell, String text) throws Exception {
		XWPFParagraph tmpP = tmpCell.getParagraphs().get(0);
		XWPFParagraph cellP = cell.getParagraphs().get(0);
		XWPFRun tmpR = null;
		if (tmpP.getRuns() != null && tmpP.getRuns().size() > 0) {
			tmpR = tmpP.getRuns().get(0);
		}
		XWPFRun cellR = cellP.createRun();
		cellR.setText(text);
	}

	/**
	 * 替换附件门店清单里的表格
	 *
	 * @param docx
	 * @param beanList
	 * @throws Exception
	 */
	private void replaceStoreTable(XWPFDocument docx, List<InnerTableListBean> beanList) throws Exception {
		// 获得文档中的table列表
		List<XWPFTable> tables = docx.getTables();
		// 遍历表格，侦测其中第二行的第一个单元格，内容为storeList，则这个就是我们要替换变量的表格
		XWPFTable table = null;
		for (XWPFTable xt : tables) {
			XWPFTableRow row = xt.getRow(1);
			XWPFTableCell cell = row.getCell(0);
			String text = cell.getText();
			if (text.contains("storeList")) {
				// 取得对于的table
				table = xt;
				break;
			}
		}
		if (table == null) {
			return;
		}
		// 取得table的第二行，作为模板行
		XWPFTableRow modelRow = table.getRow(1);
		// 门店数目
		int size = beanList.size();
		for (int i = 0; i < size; i++) {
			// 在table中新增一行
			XWPFTableRow row = table.createRow();
			// 将模板行复制到新增行中
			this.copyRow(modelRow, row);
			// 获得门店的信息
			InnerTableListBean bean = beanList.get(i);
			// 将门店信息从object转换为map对象
			Map<String, Object> params = this.transformBean(bean);
			// 添加门店序号
			params.put("storeList", i + 1);
			// 获得行中的列
			List<XWPFTableCell> cells = row.getTableCells();
			for (int j = 0; j < cells.size(); j++) {
				// 获得某一列
				XWPFTableCell cell = cells.get(j);
				// 获得列中的内容
				List<XWPFParagraph> paras = cell.getParagraphs();
				for (XWPFParagraph para : paras) {
					// 对内容进行参数替换
					this.replaceInpara(para, params);
				}
			}
		}
		// 删除模板表格
		table.removeRow(1);
	}

}
