package com.d3sq.core.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.d3sq.core.dao.SequenceDao;
import com.d3sq.core.service.ISequenceService;

/**
 * 业务序列号生成器</br>
 * 使用之前请现在SysSequence表中添加对应的业务规则
 * @version v1.0
 */
@Service
public class SequenceService implements ISequenceService {
	
	@Autowired SequenceDao sequenceDao;

	/**
	 * 获取业务流水号<br>
	 * 例如以下代码，获取系统中“menuCode”业务对象的业务流水号，加上前缀“MC”和分隔符“-”，且长度为8
	 * “init=true” 当业务代码不存在时，插入业务代码。默认为不插入。
	 * 
	 * <pre>
	 * 	String bizNo = sequenceService.nextVal(&quot;menuCode&quot;,&quot;MC&quot;,&quot;-&quot;,&quot;8&quot;);
	 * 	bizNo = &quot;MC-00000001&quot;;
	 * </pre>
	 * @param businessCode	业务代码
	 * @param prefixValue	前缀
	 * @param splitFlagValue	分隔符
	 * @param numLength	长度
	 * @param init	是否初始化
	 * @return	业务流水号
	 */
	@Override
	public String nextVal(String businessName, String prefixValue, String splitFlagValue,Integer numLength,boolean init) {
		return sequenceDao.selectNextVal(businessName,prefixValue,splitFlagValue,numLength,false,init);
	}

	/**
	 * 获取业务流水号<br>
	 * 例如以下代码，获取系统“menuCode”业务对象的业务流水号，加上前缀“MC”且长度为8
	 * “init=true” 当业务代码不存在时，插入业务代码。默认为不插入。
	 * 
	 * <pre>
	 * 	String bizNo = sequenceService.nextVal(&quot;menuCode&quot;,&quot;MC&quot;,&quot;8&quot;);
	 * 	bizNo = &quot;MC00000001&quot;;
	 * </pre>
	 * @param businessCode	业务代码
	 * @param prefixValue	前缀
	 * @param numLength		长度
	 * @param init	是否初始化
	 * @return
	 */
	@Override
	public String nextVal(String businessName, String prefixValue, Integer numLength,boolean init) {
		return sequenceDao.selectNextVal(businessName,prefixValue,numLength,init);
	}

	/**
	 * 获取业务流水号<br>
	 * 例如以下代码，获取系统中“menuCode”业务对象的业务流水号，且长度为8
	 * “init=true” 当业务代码不存在时，插入业务代码。默认为不插入。
	 * 
	 * <pre>
	 * 	String bizNo = sequenceService.nextVal(&quot;menuCode&quot;,&quot;8&quot;);
	 * 	bizNo = &quot;00000001&quot;;
	 * </pre>
	 * @param businessCode	业务代码
	 * @param numLength		长度
	 * @param init	是否初始化
	 * @return
	 */
	@Override
	public String nextVal(String businessName, Integer numLength,boolean init) {
		return sequenceDao.selectNextVal(businessName,numLength,init);
	}

	/**
	 * 获取业务流水号<br>
	 * 例如以下代码，获取系统中“menuCode”业务对象的业务流水号
	 * “init=true” 当业务代码不存在时，插入业务代码。默认为不插入。
	 * 
	 * <pre>
	 * 	String bizNo = sequenceService.nextVal(&quot;menuCode&quot;);
	 * 	bizNo = &quot;00000001&quot;;
	 * </pre>
	 * @param businessCode	业务代码
	 * @param init	是否初始化
	 * @return
	 */
	@Override
	public String nextVal(String businessName,boolean init) {
		return sequenceDao.selectNextVal(businessName,init);
	}
	
	/**
	 * 获取业务流水号<br>
	 * 例如以下代码，获取系统中“menuCode”业务对象的业务流水号
	 * “init=true” 当业务代码不存在时，插入业务代码。默认为不插入。
	 * 
	 * <pre>
	 * 	String bizNo = sequenceService.nextVal(&quot;menuCode&quot;);
	 * 	bizNo = &quot;00000001&quot;;
	 * </pre>
	 * @param businessCode	业务代码
	 * @return
	 */
	@Override
	public String nextVal(String businessName) {
		return sequenceDao.selectNextVal(businessName);
	}
	
}
