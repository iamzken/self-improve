package javax.core.common.utils;

import java.util.ArrayList;
import java.util.List;


/**
 * 集合常用操作
 * @author Tom
 *
 */
public class ListUtils {
	
	private ListUtils(){}
	
	
	/**
	 * 
	 *  分页,按页码和分页大小分页
	 *
	 * @param datas
	 * @param pageNo
	 * @param pageSize
	 * @return    
	 * @throws
	 */
	public  static List pagination(List datas, int pageNo, int pageSize) {
		List result = new ArrayList();
		if (null != datas) {
			int index = (pageNo * pageSize <= datas.size() ? pageNo * pageSize
					: datas.size());

			if (0 != datas.size()) {
				// 在 内存中 进行分页
				for (int j = (pageNo - 1) * pageSize; j < index; j++) {

					result.add(datas.get(j));

				}
			}
		}
		return result;
	}
	
	/**
	 * 连接数据中的元素，最后返回连接后的字符串
	 * @param list
	 * @param joinstr
	 * @return
	 */
	public static String join(List list,String joinstr){
		if(list == null || list.size() == 0){return "";}
		return list.toString().replaceFirst("\\[|", "").replaceFirst("\\]$", "").replaceAll("\\s", "").replaceAll(",",joinstr);
	}
}
