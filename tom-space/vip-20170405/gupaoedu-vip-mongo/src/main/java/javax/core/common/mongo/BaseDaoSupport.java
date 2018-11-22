package javax.core.common.mongo;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;

import javax.core.common.Page;
import javax.core.common.utils.GenericsUtils;

import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;

  
/** 
 * MongoDB 
 */  
public abstract class BaseDaoSupport<T extends Serializable, PK extends Serializable> {  
	
	private MongoTemplate mongoTemplate;
	
	private EntityOperation<T> op;
	
	public BaseDaoSupport(){
		Class<T> entityClass = GenericsUtils.getSuperClassGenricType(getClass(),0);
		op = new EntityOperation<>(entityClass);
	}
	
	
	protected List<T> find(QueryRule queryRule){
		
		//问题1：首先想办法拿到Templete，而且要通过一个更加友好的方式将其注入进来
		
		//问题2：如何将用户的QueryRule操作转换为templete的操作
		
		//问题3：MongoDB肯定要实现读写分离以及动态数据源如何路由
		
		QueryRuleBulider bulider = new QueryRuleBulider(queryRule);
		Query query = bulider.getQuery();
		return mongoTemplate.find(query, op.entityClass);
	}
	
	
	/**
	 * 根据ID获取对象. 如果对象不存在，返回null.<br>
	 */
	protected T get(PK id) throws Exception {
		QueryRule queryRule = QueryRule.getInstance();
		queryRule.andEqual(this.getPKColumn(), id);
		QueryRuleBulider bulider = new QueryRuleBulider(queryRule);
		Query query = bulider.getQuery();
		return mongoTemplate.findOne(query, op.entityClass);
	}

	/**
	 * 获取全部对象. <br>
	 * 
	 * @return 全部对象
	 */
	protected List<T> getAll() throws Exception {
		return null;
	}
	

	/**
	 * 使用SQL语句更新对象.<br>
	 * 例如：以下代码将更新id="0002"的name值更新为“张三”到数据库
	 * <pre>
	 * 		<code>
	 * Map<String,Object> map = new HashMap();
	 * map.put("name","张三");
	 * map.put("id","0002");
	 * String sql = "UPDATE SET name = :name WHERE id = :id";
	 * // 更新对象
	 * service.update(sql,map)
	 * </code>
	 * </pre>
	 * 
	 * @param sql 更新sql语句
	 * @param args 参数对象
	 * 
	 * @return 更新记录数
	 */
	protected int update(String sql,Map<String,?> paramMap) throws Exception{
		return 0;
	}
	/**
	 * 批量保存对象.<br>
	 * 例如：以下代码将对象保存到数据库
	 * <pre>
	 * 		<code>
	 * List&lt;Role&gt; list = new ArrayList&lt;Role&gt;();
	 * for (int i = 1; i &lt; 8; i++) {
	 * 	Role role = new Role();
	 * 	role.setId(i);
	 * 	role.setRolename(&quot;管理quot; + i);
	 * 	role.setPrivilegesFlag(&quot;1,2,3&quot;);
	 * 	list.add(role);
	 * }
	 * service.saveAll(list);
	 * </code>
	 * </pre>
	 * 
	 * @param list 待保存的对象List
	 * @throws InvocationTargetException 
	 * @throws IllegalArgumentException 
	 * @throws IllegalAccessException 
	 */
	protected int saveAll(List<T> list) throws Exception {
		return 0;
	}
	
	
	

	/**
	 * 删除对象.<br>
	 * 例如：以下删除entity对应的记录
	 * <pre>
	 * 		<code>
	 * service.remove(entity);
	 * </code>
	 * </pre>
	 * 
	 * @param obj 待删除的实体对象
	 */
	protected int delete(Object entity) throws Exception {
		return 0;
	}

	/**
	 * 删除对象.<br>
	 * 例如：以下删除entity对应的记录
	 * <pre>
	 * 		<code>
	 * service.remove(entityList);
	 * </code>
	 * </pre>
	 * 
	 * @param entityList 待删除的实体对象列表
	 * @throws InvocationTargetException 
	 * @throws IllegalArgumentException 
	 * @throws IllegalAccessException 
	 */
	protected int deleteAll(List<T> list) throws Exception {
		return 0;
	}


	
	/**
	 * 根据ID删除对象.如果有记录则删之，没有记录也不报异常<br>
	 * 例如：以下删除主键唯一的记录
	 * <pre>
	 * 		<code>
	 * service.remove(1);
	 * </code>
	 * </pre>
	 * 
	 * @param id 序列化对id
	 * 
	 * @return 删除是否成功
	 */
	protected boolean delete(PK id)  throws Exception {
		return true;
	}

	

	/**
	 * 查询满足条件的记录数，使用hql.<br>
	 * 例如：查询User里满足条件?name like "%ca%" 的记录数
	 * 
	 * <pre>
	 * 		<code>
	 * long count = service.getCount(&quot;from User where name like ?&quot;, &quot;%ca%&quot;);
	 * </code>
	 * </pre>
	 * 
	 * @param sql SQL语句
	 * @param values 参数List
	 * @return 满足条件的记录数
	 */
	protected long getCount(QueryRule queryRule) throws Exception {
		return 0;
	}

	/**
	 * 根据某个属性值倒序获得第一个最大值
	 * @param propertyName
	 * @return
	 */
	protected T getMax(String propertyName) throws Exception{
		QueryRule queryRule = QueryRule.getInstance();
		queryRule.addDescOrder(propertyName);
		Page<T> result = this.find(queryRule,1,1);
		if(null == result.getRows() || 0 == result.getRows().size()){
			return null;
		}else{
			return result.getRows().get(0);
		}
	}
	
	
	
	


	/**
	 * 根据SQL语句查询符合条件的唯一对象，没符合条件的记录返回null.<br>
	 * @param sql查询语句
	 * @param args 为Object数组
	 * @return 符合条件的唯一对象，没符合条件的记录返回null.
	 */
	protected Map<String,Object> findUnique(Object... args) throws Exception{
		return null;
	}
	
	/**
	 * 根据SQL语句执行查询，参数为List对象
	 * @param sql查询语句
	 * @param list<Object>对象
	 * @return 符合条件的所有对象
	 */
	protected List<Map<String,Object>> find(List<Object> list) throws Exception{
		return null;
	}
	
	/**
	 * 根据SQL语句查询符合条件的唯一对象，没符合条件的记录返回null.<br>
	 * @param sql查询语句
	 * @param listParam 属性值List
	 * @return 符合条件的唯一对象，没符合条件的记录返回null.
	 */
	protected Map<String,Object> findUnique(List<Object> listParam) throws Exception{
		return null;
	}
	
	/**
	 * 分页查询函数，使用查询规则<br>
	 * 例如以下代码查询条件为匹配的数据
	 * 
	 * <pre>
	 *		<code>
	 * QueryRule queryRule = QueryRule.getInstance();
	 * queryRule.addLike(&quot;username&quot;, user.getUsername());
	 * queryRule.addLike(&quot;monicker&quot;, user.getMonicker());
	 * queryRule.addBetween(&quot;id&quot;, lowerId, upperId);
	 * queryRule.addDescOrder(&quot;id&quot;);
	 * queryRule.addAscOrder(&quot;username&quot;);
	 * page = userService.find(User.class, queryRule, pageNo, pageSize);
	 * </code> 
	 * </pre>
	 * 
	 * @param queryRule 查询规则
	 * @param pageNo 页号,从1开始
	 * @param pageSize  每页的记录条数
	 * @return 查询出的结果Page
	 */
	protected Page<T> find(QueryRule queryRule,final int pageNo, final int pageSize) throws Exception{
		return null;
	}

	
	/**
	 * 分页查询特殊SQL语句
	 * @param sql 语句
	 * @param param  查询条件
	 * @param pageNo	页码
	 * @param pageSize	每页内容
	 * @return
	 */
	protected Page<Map<String,Object>> findPage( Map<String,?> param, final int pageNo, final int pageSize) throws Exception {
		return null;
	}
	
	
	/**
	 * 分页查询特殊SQL语句
	 * @param sql 语句
	 * @param param  查询条件
	 * @param pageNo	页码
	 * @param pageSize	每页内容
	 * @return
	 */
	protected Page<Map<String,Object>> findPage(Object [] param, final int pageNo, final int pageSize) throws Exception {
		return null;
	}
	
	/**
	 * 根据<属性名和属属性值Map查询符合条件的唯一对象，没符合条件的记录返回null.<br>
	 * 例如，如下语句查找sex=1,age=18的所有记录：
	 * 
	 * <pre>
	 *     <code>
	 * Map properties = new HashMap();
	 * properties.put(&quot;sex&quot;, &quot;1&quot;);
	 * properties.put(&quot;age&quot;, 18);
	 * User user = service.findUnique(User.class, properties);
	 * </code>
	 * </pre>
	 * 
	 * @param properties 属性值Map，key为属性名，value为属性值
	 * @return 符合条件的唯一对象，没符合条件的记录返回null.
	 */
	protected T findUnique(Map<String, Object> properties) throws Exception {
		return null;
	}
	
	
	
	
	protected abstract String getPKColumn();
	
	protected void setTemplate(MongoTemplate template){
		this.mongoTemplate = template;
	}
	
	
}
