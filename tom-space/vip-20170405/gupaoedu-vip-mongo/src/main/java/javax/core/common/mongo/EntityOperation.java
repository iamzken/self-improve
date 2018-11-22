package javax.core.common.mongo;

/**
 * 实体对象的反射操作
 * @author Tom
 *
 * @param <T>
 */

public class EntityOperation<T> {
	public Class<T> entityClass = null; // 泛型实体Class对象
	
	public EntityOperation(Class<T> entityClass){
		this.entityClass = entityClass;
	}
}

