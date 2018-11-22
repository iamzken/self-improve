package com.d3sq.core.service;

/**
 * 生成系统序列号
 *
 */
public interface ISequenceService {
	
	/**
	 * 获取业务流水号<br>
	 * 例如以下代码，获取系统中“menuCode”业务对象的业务流水号，加上前缀“MC”和分隔符“-”，且长度为8<br>
	 * “init=true” 当业务代码不存在时，插入业务代码。默认为不插入。
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
	public String nextVal(String businessCode,String prefixValue,String splitFlagValue,Integer numLength,boolean init);
	
	/**
	 * 获取业务流水号<br>
	 * 例如以下代码，获取系统“menuCode”业务对象的业务流水号，加上前缀“MC”且长度为8<br>
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
	public String nextVal(String businessCode,String prefixValue,Integer numLength,boolean init);
	
	/**
	 * 获取业务流水号<br>
	 * 例如以下代码，获取系统中“menuCode”业务对象的业务流水号，且长度为8<br>
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
	public String nextVal(String businessCode,Integer numLength,boolean init);
	
	/**
	 * 获取业务流水号<br>
	 * 例如以下代码，获取系统中“menuCode”业务对象的业务流水号<br>
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
	public String nextVal(String businessCode,boolean init);
	
	/**
	 * 获取业务流水号<br>
	 * 例如以下代码，获取系统中“menuCode”业务对象的业务流水号
	 * 
	 * <pre>
	 * 	String bizNo = sequenceService.nextVal(&quot;menuCode&quot;);
	 * 	bizNo = &quot;00000001&quot;;
	 * </pre>
	 * @param businessCode	业务代码
	 * @return
	 */
	public String nextVal(String businessCode);
}
