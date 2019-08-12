
import java.util.Collections;
        import java.util.HashMap;
        import java.util.HashSet;
        import java.util.Map;
        import java.util.Set;

//用map来保存用户和socket输出流的对应关系,
//K将会是String类型的用户名，不允许重复
//V是从socket返回的outputStream对象，也不允许重复
public class ChatMap<K,V> {
    public Map<K,V> map = Collections.synchronizedMap(new HashMap<K,V>());

    //根据outputStream对象删除制定项
    public synchronized void removeByValue(Object value) {
        for (Object key : map.keySet()) {
            if(map.get(key) == value) {
                map.remove(key);
                break;
            }
        }
    }

    //获取outputStream对象组成的Set
    public synchronized Set<V> valueSet() {
        Set<V> result = new HashSet<V>();
        //遍历map，将map的value存入Set
        for(K key :  map.keySet()) {
            result.add(map.get(key));
        }
        return result;
    }

    //根据ouputStream对象查找用户名
    public synchronized K getKeyByValue(V val) {
        for(K key : map.keySet()) {
            if (map.get(key) == val || map.get(key).equals(val)) {
                return key;
            }
        }
        return null;
    }

    //实现put,key和value都不允许重复
    public synchronized V put(K key, V value) {
        for (V val : valueSet() ) {
            if (val.equals(value) && val.hashCode() == value.hashCode()) {
                throw new RuntimeException("此输入流已经被使用");
            }
        }
        return map.put(key, value);
    }
}
