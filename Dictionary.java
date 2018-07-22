public class Dictionary<K, V> extends HashTable<K, V> {
    public void add(K key, V value) {
        store(key, value);      
    }
    
    public V get(K key) { 
        return retrieve(key);
    }    
}