package javax.core.common.mongo;

import java.util.ArrayList;
import java.util.List;

import javax.core.common.utils.StringUtils;

import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.domain.Sort.Direction;  
import org.springframework.data.domain.Sort.Order;  

public class QueryRuleBulider {
	
	private Query query;
	
	private Criteria criteria;
	
	private List<Order> orders;
	
	public  QueryRuleBulider(QueryRule queryRule) {
		//根据RuleList来循环，动态生成各种Criteria
		this.criteria = new Criteria();
		
		for (QueryRule.Rule rule : queryRule.getRuleList()) {
			switch (rule.getType()) {
			case QueryRule.BETWEEN:
				processBetween(rule);
				break;
			case QueryRule.EQ:
				processEqual(rule);
				break;
			case QueryRule.LIKE:
				processLike(rule);
				break;
			case QueryRule.NOTEQ:
				processNotEqual(rule);
				break;
			case QueryRule.GT:
				processGreaterThen(rule);
				break;
			case QueryRule.GE:
				processGreaterEqual(rule);
				break;
			case QueryRule.LT:
				processLessThen(rule);
				break;
			case QueryRule.LE:
				processLessEqual(rule);
				break;
			case QueryRule.IN:
				processIN(rule);
				break;
			case QueryRule.NOTIN:
				processNotIN(rule);
				break;
			case QueryRule.ISNULL:
				processIsNull(rule);
				break;
			case QueryRule.ISNOTNULL:
				processIsNotNull(rule);
				break;
			case QueryRule.ISEMPTY:
				processIsEmpty(rule);
				break;
			case QueryRule.ISNOTEMPTY:
				processIsNotEmpty(rule);
				break;
			case QueryRule.ASC_ORDER:
				processOrder(rule);
				break;
			case QueryRule.DESC_ORDER:
				processOrder(rule);
				break;
			default:
				throw new IllegalArgumentException("type " + rule.getType() + " not supported.");
			}
		}
		
		this.query = new Query(this.criteria);
		//设置排序的优先级
//		List<Order> orders = new ArrayList<Order>();
//		orders.add(new Order(Direction.DESC, "price"));
		
		this.query.with(new Sort(this.orders));
	}
	
	
	
	/**
	 * 处理like
	 * @param rule
	 */
	private  void processLike(QueryRule.Rule rule) {
		
	}

	/**
	 * 处理between
	 * @param rule
	 */
	private  void processBetween(QueryRule.Rule rule) {
		
	}
	
	/**
	 * 处理 =
	 * @param rule
	 */
	private  void processEqual(QueryRule.Rule rule) {
		this.criteria.and(rule.getPropertyName()).is(rule.getValues()[0]);
	}

	/**
	 * 处理 <>
	 * @param rule
	 */
	private  void processNotEqual(QueryRule.Rule rule) {
		
	}

	/**
	 * 处理 >
	 * @param rule
	 */
	private  void processGreaterThen(QueryRule.Rule rule) {
		
	}

	/**
	 * 处理>=
	 * @param rule
	 */
	private  void processGreaterEqual(QueryRule.Rule rule) {
		
	}

	/**
	 * 处理<
	 * @param rule
	 */
	private  void processLessThen(QueryRule.Rule rule) {
		
	}

	/**
	 * 处理<=
	 * @param rule
	 */
	private  void processLessEqual(QueryRule.Rule rule) {
		
	}

	/**
	 * 处理  is null
	 * @param rule
	 */
	private  void processIsNull(QueryRule.Rule rule) {
		
	}

	/**
	 * 处理 is not null
	 * @param rule
	 */
	private  void processIsNotNull(QueryRule.Rule rule) {
		
	}

	/**
	 * 处理  <>''
	 * @param rule
	 */
	private  void processIsNotEmpty(QueryRule.Rule rule) {
		
	}

	/**
	 * 处理 =''
	 * @param rule
	 */
	private  void processIsEmpty(QueryRule.Rule rule) {
		
	}

	
	/**
	 * 处理in和not in
	 * @param rule
	 * @param name
	 */
	private void inAndNotIn(QueryRule.Rule rule,String name){
		
	}
	
	/**
	 * 处理 not in
	 * @param rule
	 */
	private void processNotIN(QueryRule.Rule rule){
		
	}
	
	/**
	 * 处理 in
	 * @param rule
	 */
	private  void processIN(QueryRule.Rule rule) {
		
	}
	
	
	
	/**
	 * 处理 order by
	 * @param rule 查询规则
	 */
	private void processOrder(QueryRule.Rule rule) {
		
		//根据某个字段升序、降序
		if(null == this.orders){
			this.orders = new ArrayList<Order>();
		}
		switch (rule.getType()) {
			case QueryRule.ASC_ORDER:
				if (!StringUtils.isEmpty(rule.getPropertyName())) {
					this.orders.add(new Order(Direction.ASC, rule.getPropertyName()));
				}
				break;
			case QueryRule.DESC_ORDER:
				if (!StringUtils.isEmpty(rule.getPropertyName())) {
					this.orders.add(new Order(Direction.DESC, rule.getPropertyName()));
				}
				break;
			default:
				break;
		}
		
	}
	
	
	
	public Query getQuery(){
		
		return this.query;
	}
	
}
