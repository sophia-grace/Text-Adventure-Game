
public class LinkedListHT<K,V> { //LinkedList referred to in HashTable
	NodeHT<K, V> list;
		    
	public void add(K key, V value) {
		add(new NodeHT<K, V>(key, value));
	}

	public void add(NodeHT<K, V> node) {
		if(list == null) {
		     list = node;            
		 }
		 else {
			 NodeHT<K, V> current = list;
			 while(current.next != null) {
				 current = current.next;
			 }
		            current.next = node;            
		        }       
		    }  
		    
	public boolean contains(K key) {
		NodeHT<K, V> current = list;
		while (current != null) {
			if (current.key.equals(key)) {
				return true;               
		    }
		    current = current.next;
		}
		return false;
	}
		   
	public boolean delete(K key) {
		NodeHT<K, V> current = list;
		NodeHT<K, V> previous = null;
		while (current != null) {
			if (current.key.equals(key)) {
				if (previous == null) {
					list = current.next;                    
		        }
				else {
					previous.next = current.next;                    
		        }
		    return true;                
			}
		    previous = current;
		    current = current.next;
		}
		return false;
	}    
		   
	public V retrieve(K key) {
		NodeHT<K, V> current = list;
		while(current != null) {
			if(current.key.equals(key)) {                
				return current.value;               
		     }
			current = current.next;
		}         
		return null;       
	} 
		         
	public NodeHT<K,V> retrieveNode(K key) {
		NodeHT<K, V> current = list;
		while(current != null) {
			if(current.key.equals(key)) {                
				return current;               
		     }
		     current = current.next;
		}         
		return null;
	} 
		          
	public String toString() {
		NodeHT<K, V> current = list;
		String sb = "";
		while(current != null) {
			if (! sb.equals("")) {
				sb += current.key;
		    } else {
		    	sb += current.key;                
		    }
		    	current = current.next;            
		}        
		return sb;
	}   
}