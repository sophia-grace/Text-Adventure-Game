public class NodeHT<K,V> { //Node referred to in HashTable
	NodeHT<K, V> next;
	K key;
	V value;
		    
	public NodeHT(K key, V value) {
		  this.key = key;
		  this.value = value;
	}
}