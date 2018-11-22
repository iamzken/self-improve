package javax.core.common.mongo;

/**
 * Created by Tom on 2018/9/5.
 */
public class EntiyOperation<T> {
    public Class<T> entiyClass = null;

    public EntiyOperation(Class<T> entiyClass){
        this.entiyClass = entiyClass;
    }
}
